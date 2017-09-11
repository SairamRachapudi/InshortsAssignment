package com.sairam.inshortsapp.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.baseframework.BaseActivity;
import com.sairam.inshortsapp.events.NewsInfoEvent;
import com.sairam.inshortsapp.viewmodel.WebViewViewModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Project Details Activity
 */

public class WebViewActivity extends BaseActivity {
    WebView webView;
    ProgressBar progressBar;

    @Override
    public int getActivityLayoutId() {
        return R.layout.activity_web_view;
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new WebViewViewModel(getNavigator());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        webView = (WebView) findViewById(R.id.web_view);
    }

    @Subscribe(sticky = true)
    public void getProjectUrl(NewsInfoEvent event){
        if(webView == null){
            webView = (WebView) findViewById(R.id.web_view);
        }
        webView.loadUrl(event.getProjectUrl());
        webView.setWebViewClient(new ProjectWebViewClient());
        EventBus.getDefault().removeStickyEvent(event);
    }

    private class ProjectWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
