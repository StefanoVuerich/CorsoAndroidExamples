package com.lqc.mylocalguide.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.lqc.mylocalguide.R;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment.IOnApplicationExit;
import com.lqc.mylocalguide.receivers.InternetConnectionActiveBroadcastReceiver;
import com.lqc.mylocalguide.utilities.FullScreenHelper;

public class NoInternetActivity extends Activity implements IOnApplicationExit {

	BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.v("jajaja", "start no internet activity");

		setContentView(R.layout.no_internet_activity_layout);
	}

	@Override
	protected void onResume() {
		super.onResume();

		FullScreenHelper.get().enableFullScreenMode(this);

		receiver = new InternetConnectionActiveBroadcastReceiver();
		IntentFilter intent = new IntentFilter(
				"android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(receiver, intent);
	}

	@Override
	protected void onPause() {
		super.onPause();

		unregisterReceiver(receiver);
	}

	@Override
	public void onBackPressed() {
		ConfirmApplicationExitFragment fr = ConfirmApplicationExitFragment
				.get();
		fr.show(getFragmentManager(), ConfirmApplicationExitFragment._TAG);
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
