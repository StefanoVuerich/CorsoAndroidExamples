package com.example.customviewcircles.threading;

import java.lang.ref.WeakReference;

import android.app.Fragment;
import android.util.Log;

import com.example.customviewcircles.fragments.SamplerButtonsFragment;
import com.example.customviewcircles.nativeaudio.CanPlayHandler;

public class PressedButtonRunnable implements Runnable {
	
	private int mCounter;
	private WeakReference<SamplerButtonsFragment> mRef;
	private boolean mStopped;

	public PressedButtonRunnable(SamplerButtonsFragment fragment) {
		mRef = new WeakReference<SamplerButtonsFragment>(fragment);
	}
	
	public void stopRunnable() {
		mStopped = true;
	}
	@Override
	public void run() {
		
		PressTimer.getInstance().setStartTime(System.currentTimeMillis());
		while (!mStopped) {
			
			if (mRef != null) {
				SamplerButtonsFragment fragment = mRef.get();
				Log.v("jajaja", "counter: " + mCounter + " - " + System.currentTimeMillis());
				if (fragment != null  && mCounter >= 15) {
					CanPlayHandler.getInstance().setCanPlay(false);
					showLoader(fragment);
					break;
				}
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mCounter++;
			
		}
		long timeElapsed = PressTimer.getInstance().getTime(System.currentTimeMillis());
		Log.v("jajaja","TimeElapsed: " + timeElapsed);
	}

	private void showLoader(SamplerButtonsFragment fragment) {
		fragment.showSampleLoader();
	}

}
