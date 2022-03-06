package com.example.taraziot;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigServerActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_server);


        webView = findViewById(R.id.webView);

        WebSettings wbset = webView.getSettings();
        wbset.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String url = "http://google.com/";

//        System.out.println(getdeviceid());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);





    }
}