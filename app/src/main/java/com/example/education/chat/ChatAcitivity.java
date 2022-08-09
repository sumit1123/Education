package com.example.education.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.TrustedWebUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityChatAcitivityBinding;
import com.example.education.databinding.ActivityTrackResultBinding;
import com.example.education.utils.Constant;

public class ChatAcitivity extends BaseActivity {

    ActivityChatAcitivityBinding  activityChatAcitivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatAcitivityBinding = ActivityChatAcitivityBinding.inflate(getLayoutInflater());
        setContentView(activityChatAcitivityBinding.getRoot());
        activityChatAcitivityBinding.webview.setWebViewClient(new EducationWebViewClient());
        activityChatAcitivityBinding.webview.loadUrl(Constant.IMAGE_URL + "WApp/");
        WebSettings webSettings = activityChatAcitivityBinding.webview.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDefaultTextEncodingName("utf-8");
        activityChatAcitivityBinding.webview.clearCache(true);
        activityChatAcitivityBinding.webview.clearFormData();
        activityChatAcitivityBinding.webview.clearHistory();
        activityChatAcitivityBinding.webview.clearSslPreferences();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebStorage.getInstance().deleteAllData();
        activityChatAcitivityBinding.webview.setWebViewClient(new EducationWebViewClient());
      ///  callChrome();

    }

    private void callChrome() {
        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
        customIntent.setToolbarColor(ContextCompat.getColor(ChatAcitivity.this, R.color.blue));
        TrustedWebUtils.launchAsTrustedWebActivity(
                ChatAcitivity.this,
                customIntent.build(),
                Uri.parse(Constant.IMAGE_URL + "WApp/"));
       // openCustomTab(ChatAcitivity.this, customIntent.build(), Uri.parse(Constant.IMAGE_URL + "WApp/"));
    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    class EducationWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            activityChatAcitivityBinding.progress.setVisibility(View.GONE);
            activityChatAcitivityBinding.webview.setVisibility(View.VISIBLE);

        }
    }

}