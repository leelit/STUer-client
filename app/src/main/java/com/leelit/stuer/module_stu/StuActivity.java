package com.leelit.stuer.module_stu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.leelit.stuer.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StuActivity extends AppCompatActivity {

    @InjectView(R.id.webView)
    WebView mWebView;
    @InjectView(R.id.btn_back)
    Button mBtnBack;
    @InjectView(R.id.btn_ahead)
    Button mBtnAhead;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu);
        ButterKnife.inject(this);

        webViewProgressSetting();
        initToolBar();

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false); // 隐藏webview缩放按钮
        settings.setBuiltInZoomControls(true); // 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true); // 支持双击缩放(wap网页不支持)
        settings.setLoadWithOverviewMode(true); // 自适应屏幕

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        mWebView.loadUrl(getIntent().getStringExtra("website"));

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });

        mBtnAhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goForward();
            }
        });

        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getIntent().getStringExtra("title"));
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void webViewProgressSetting() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (mProgressBar.getVisibility() == View.INVISIBLE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
