package com.lqc.mylocalguide.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lqc.mylocalguide.activities.WebViewActivity;

public class InternetConnectionActiveBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		Intent mIntent;

		if (activeNetInfo != null
				&& (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI || activeNetInfo
						.getType() == ConnectivityManager.TYPE_MOBILE)) {
			mIntent = new Intent(context, WebViewActivity.class);
			context.startActivity(mIntent);
		}
	}
}
