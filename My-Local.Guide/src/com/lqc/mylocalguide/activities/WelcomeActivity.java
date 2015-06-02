package com.lqc.mylocalguide.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lqc.mylocalguide.R;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment;
import com.lqc.mylocalguide.fragments.ConfirmApplicationExitFragment.IOnApplicationExit;
import com.lqc.mylocalguide.utilities.FullScreenHelper;

public class WelcomeActivity extends Activity implements IOnApplicationExit {

	private static int SPLASH_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity_layout);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						WebViewActivity.class);
				startActivity(intent);
			}
		}, SPLASH_TIME_OUT);
	}

	@Override
	protected void onResume() {
		super.onResume();

		FullScreenHelper.get().enableFullScreenMode(this);
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
