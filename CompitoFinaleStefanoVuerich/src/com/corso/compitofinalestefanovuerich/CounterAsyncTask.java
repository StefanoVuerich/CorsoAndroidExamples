package com.corso.compitofinalestefanovuerich;

import android.os.AsyncTask;
import android.util.Log;

public class CounterAsyncTask extends AsyncTask<Integer, Integer, Void>{

	private boolean isPaused = false;
	int mCounter;
	public IOnCounterNotifyValue mListener;
	
	public interface IOnCounterNotifyValue {
		public void onValue(int aValue);
		public void onFinished(boolean hasFinished);
	}

	@Override
	protected Void doInBackground(Integer... params) {
		mCounter = params[0];
		while (!this.isCancelled() && !isPaused) {
			Log.v(MainActivity.TAG, "task start");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			mCounter--;
			publishProgress(mCounter);
			//Log.v(MainActivity.TAG, "id: " + this + " counter: " + mCounter);
			
			if(mCounter == 0) {
				
				this.cancel(true);
				Log.v(MainActivity.TAG, "asynctask finished");
				
				publishProgress(mCounter);		
			}			
		}
		Log.v(MainActivity.TAG, "task stooooop");
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		
		if(mListener != null) {
			if(values[0] == 0) {
				mListener.onFinished(true);
			}
			mListener.onValue(values[0]);
		}
	}
	
	public void pause(boolean pause) {
		this.isPaused = pause;
		Log.v(MainActivity.TAG, "asynctask paused");
	}
}
