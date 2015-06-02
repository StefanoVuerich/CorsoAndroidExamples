package com.corso.esercizioprovacronometro;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class ClockRunnable implements Runnable {

	int count;
	boolean running;
	WeakReference<MainActivity> weakReference;
	Activity mainActivity;
	Clock clock;

	public ClockRunnable(MainActivity mActivity, Clock clock) {
		this.weakReference = new WeakReference<MainActivity>(mActivity);
		this.mainActivity = mActivity;
		this.clock = clock;

		CustomApplication.getInstance().setClockRunnable(this);
	}

	@Override
	public void run() {

		running = true;

		while (running) {

			//Log.v("jajaja", "thread is running count is: " + count);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			++count;

			if (mainActivity != null) {

				mainActivity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						((TextView) mainActivity
								.findViewById(R.id.counterLabel)).setText(""
								+ (count));

						if (clock != null) {
							Log.v("jajaja", "updating clock");
							clock.update(0);
						}
					}
				});
			}
		}
	}

	public int getTime() {

		int tmpCout = count;
		count = 0;

		return tmpCout;
	}

	public void stop() {
		if (this.running == true) {
			this.running = false;
			CustomApplication.getInstance().setClockRunnable(null);
		}
	}

	public void detachActivity() {
		this.mainActivity = null;
		this.clock = null;
	}

	public void attachActivity(MainActivity activity, Clock clock) {
		this.mainActivity = activity;
		this.clock = clock;
	}
}
