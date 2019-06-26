package com.orange.ndlib;

import android.webkit.WebView;

import com.orange.lib.activity.webview.WebviewActivity;

public class CocosWebActivity extends WebviewActivity {
    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mWebView.loadUrl("file:///android_asset/k3/index.html");
        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initWebview(mWebView, "1 2 3||20190608-1243||13:25:45||2:20");
            }
        }, 5000);
    }

    public void initWebview(WebView webView, String args) {
        String t = "'" + args + "'";
        String URL = "javascript:callFromJava(" + t + ")";
        webView.loadUrl(URL);
    }
}
