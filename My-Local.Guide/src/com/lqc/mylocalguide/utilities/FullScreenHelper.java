package com.lqc.mylocalguide.utilities;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class FullScreenHelper {

	public static FullScreenHelper get() {
		return new FullScreenHelper();
	}

	public void enableFullScreenMode(Activity activity) {
		if (Build.VERSION.SDK_INT < 19) 
			activity.getActionBar().hide();	
		
		if(Build.VERSION.SDK_INT >= 19) {
			activity.getWindow()
			.getDecorView()
			.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}
	}
}
