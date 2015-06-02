package com.lqc.mylocalguide.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lqc.mylocalguide.R;
import com.lqc.mylocalguide.connectionutilities.ConnectionManager;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment.IOnApplicationExit;
import com.lqc.mylocalguide.receivers.InternetConnectionLostBroadcastReceiver;
import com.lqc.mylocalguide.utilities.FullScreenHelper;

public class WebViewActivity extends Activity implements IOnApplicationExit {

	private WebView webView;
	private ProgressBar progressBar;
	private CookieManager cookieManager;
	private InternetConnectionLostBroadcastReceiver receiver;
	private IntentFilter filter;
	private CookieSyncManager cookieSyncManager;

	private final static String URL = "http://app.my-local.guide";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity_layout);

		if (ConnectionManager.getInstance(this)
				.checkForInternetConnection(this)) {
			setWebView();
			loadUrl(URL);

			receiver = new InternetConnectionLostBroadcastReceiver();
			filter = new IntentFilter();
			filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
			registerReceiver(receiver, filter);
		}

		else {
			showNoConnectionActivity();
		}
	}

	@Override
	public void onBackPressed() {
		ConfirmApplicationExitFragment fr = ConfirmApplicationExitFragment
				.get();
		fr.show(getFragmentManager(), ConfirmApplicationExitFragment._TAG);
	}

	private void loadUrl(String url) {
		webView.loadUrl(url);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (receiver != null)
			unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (receiver != null)
			registerReceiver(receiver, filter);

		FullScreenHelper.get().enableFullScreenMode(this);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView() {
		webView = (WebView) findViewById(R.id.webView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		// enable cookies
		CookieManager.setAcceptFileSchemeCookies(true);
		cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieSyncManager = CookieSyncManager.createInstance(this);
		cookieSyncManager.startSync();
		
		// set webview
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setInitialScale(100);
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				setValue(newProgress);
			}
		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				WebViewActivity.this.progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				WebViewActivity.this.progressBar.setVisibility(View.GONE);
			}
		});
	}

	private void showNoConnectionActivity() {

		Intent intent = new Intent(this, NoInternetActivity.class);
		startActivity(intent);
	}

	private void setValue(int progress) {
		this.progressBar.setProgress(progress);
	}

	@Override
	public void onExitApplication() {
		((ConfirmApplicationExitFragment) getFragmentManager()
				.findFragmentByTag(ConfirmApplicationExitFragment._TAG))
				.dismiss();
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
	}

	@Override
	public void onCancelExit() {
		((ConfirmApplicationExitFragment) getFragmentManager()
				.findFragmentByTag(ConfirmApplicationExitFragment._TAG))
				.dismiss();
	}
}
