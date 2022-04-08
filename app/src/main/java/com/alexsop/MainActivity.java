package com.alexsop;

import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private final String siteUrl = "https://alexsop.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(this.siteUrl);

        webView.setWebViewClient(new WebViewClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // callback.invoke(String origin, boolean allow, boolean remember);
                callback.invoke(origin, true, false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Check for connection
                if(!DetectConnection.hasInternetConnection(view.getContext()))
                {
                    showNoConnectionDialog();
                }

                super.onReceivedError(view, request, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mySwipeRefreshLayout.setRefreshing(false);

                super.onPageFinished(view, url);
            }
        });

        // Handle swipe refresh
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
    }

    @Override
    public void  onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    private void showNoConnectionDialog() {
        Toast.makeText(this,"No internet connection", Toast.LENGTH_SHORT).show();

        new MaterialAlertDialogBuilder(webView.getContext())
            .setTitle("No Internet Connection")
            .setMessage("You need to have Mobile Data or WiFi to access this. Please connect to internet and try again")
            .setPositiveButton("Retry", (dialogInterface, i) -> {
                webView.reload();
            })
            .show();
    }
}