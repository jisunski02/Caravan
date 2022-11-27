package edu.ucu.cite.caravan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChatUsWebviewActivity extends AppCompatActivity {

    WebView chatUsWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_us_webview);

        String Username=SharedPrefManager.getInstance(this).getUsername();

        chatUsWebview = findViewById(R.id.chatUsWebview);

        chatUsWebview.getSettings().setLoadWithOverviewMode(true);
        chatUsWebview.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = chatUsWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        chatUsWebview.loadUrl("https://caravan-rental-cars.online/customerbypasslogin.php?username="+Username);
        chatUsWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}