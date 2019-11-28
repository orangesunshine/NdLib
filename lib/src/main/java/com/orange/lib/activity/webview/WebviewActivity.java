package com.orange.lib.activity.webview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.orange.lib.R;
import com.orange.lib.mvp.contact.IContact;
import com.orange.lib.mvp.view.activity.base.BaseActivity;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebviewActivity extends BaseActivity {
    protected WebView mWebView;

    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_webview;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        addWebView();
        configWebView();
        configWebViewClient();
        configWebChromeClient();
    }

    /**
     * 3.4 注意事项：如何避免WebView内存泄露？
     * 3.4.1 不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
     */
    private void addWebView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        FrameLayout content = mHolder.getView(R.id.id_content_orange);
        content.addView(mWebView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mWebView) {
            mWebView.onResume();
            mWebView.resumeTimers();
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mWebView) {
//            mWebView.onPause();
//            mWebView.pauseTimers();
//            mWebView.getSettings().setJavaScriptEnabled(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 配置webview
     */
    public void configWebView() {
        if (null == mWebView) return;
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("UTF-8");//设置编码格式
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        if (Build.VERSION.SDK_INT >= 11 && mWebView.getHeight() < 4096 && mWebView.getWidth() < 4096) {
//            mWebView.setLayerType(2, (Paint) null);
//        } else {
//            mWebView.setLayerType(0, (Paint) null);
//        }

        //配置缓存
//        if (NetStatusUtil.isConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

    }

    /*
    onCloseWindow//关闭WebView
　　onCreateWindow() //触发创建一个新的窗口
     */
    public void configWebChromeClient() {
        if (null == mWebView) return;
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
//                    progress.setText(progress);
                } else {
                }
            }

            /**
             * 作用：获取Web页中的标题
             * 每个网页的页面都有一个标题，比如www.baidu.com这个页面的标题即“百度一下，你就知道”，那么如何知道当前webview正在加载的页面的title并进行设置呢？
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleview.setText(title);
            }

            /**
             * 作用：支持javascript的警告框
             * 一般情况下在 Android 中为 Toast，在文本里面加入\n就可以换行
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("JsAlert")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }

            /**
             * 常见方法4： onJsConfirm（）
             * 作用：支持javascript的确认框
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("JsConfirm")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                // 返回布尔值：判断点击时确认还是取消
                // true表示点击了确认；false表示点击了取消；
                return true;
            }

            /**
             * 作用：支持javascript输入框
             * 点击确认返回输入框中的值，点击取消返回 null。
             * @param view
             * @param url
             * @param message
             * @param defaultValue
             * @param result
             * @return
             */
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                final EditText et = new EditText(mActivity);
                et.setText(defaultValue);
                new AlertDialog.Builder(mActivity)
                        .setTitle(message)
                        .setView(et)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(et.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();

                return true;
            }

        });
    }

    /**
     * 处理各种通知 & 请求事件
     */
    public void configWebViewClient() {
        if (null != mWebView)
            mWebView.setWebViewClient(new WebViewClient() {
                /**
                 * @param view
                 * @param url
                 * @deprecated
                 */
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    //设定加载开始的操作,作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    //设定加载结束的操作,作用：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                    //设定加载资源的操作,作用：在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
                }


                /*
                步骤1：写一个html文件（error_handle.html），用于出错时展示给用户看的提示页面
                步骤2：将该html文件放置到代码根目录的assets文件夹下
                步骤3：复写WebViewClient的onRecievedError方法
                该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
                 */
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    //作用：加载页面的服务器出现错误时（如404）调用。
                    switch (errorCode) {
                        case 404:
                            view.loadUrl("file:///android_assets/error_handle.html");
                            break;
                    }
                }

                //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();    //表示等待证书响应
                    // handler.cancel();      //表示挂起连接，为默认方式
                    // handler.handleMessage(null);    //可做其他处理
                }
            });
    }

    /**
     * 清空
     */
    public void clear() {
        if (null == mWebView) return;
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        mWebView.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        mWebView.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        mWebView.clearFormData();
    }


    /**
     * onDestory生命周期调用
     */
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }
}
