package com.corso.esercizioprovacronometro;

import android.app.Application;

public class CustomApplication extends Application {

	private ClockRunnable clockRunnable;
	private static CustomApplication mInstace;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstace = this;
		clockRunnable = null;
	}
	
	public static CustomApplication getInstance() {
		return mInstace;
	}
	
	public void setClockRunnable(ClockRunnable clockRunnable) {
		this.clockRunnable = clockRunnable;
	}
	
	public ClockRunnable getRunnable() {
		return clockRunnable;
	}

}
