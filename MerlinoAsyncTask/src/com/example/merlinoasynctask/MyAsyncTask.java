package com.example.merlinoasynctask;

import android.R.bool;
import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

	public interface IOnNotifyValue {
		public void onValue(int aValue);
	}

	int mCounter;
	public IOnNotifyValue mListener;

	@Override
	protected Void doInBackground(Void... params) {
		while (!this.isCancelled()) {
			publishProgress(mCounter);
			Log.d("MyAsyncTask", "id: " + this + " counter: " + mCounter);
			mCounter++;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		
		if(mListener != null) {
			mListener.onValue(values[0]);
		}
	}
}
