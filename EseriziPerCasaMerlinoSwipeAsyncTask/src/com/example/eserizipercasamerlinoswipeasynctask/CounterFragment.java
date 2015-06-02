package com.example.eserizipercasamerlinoswipeasynctask;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CounterFragment extends Fragment {

	public static int counter = 0;
	// private final static String TAG = "MYTAG";
	private boolean isStopped = true;
	// private MyThread mThread;
	private Button start, stop;
	private TextView counterTxt, finishCount;
	private boolean canStart = true;

	// Per secondo Modo
	MyRunnable mMyRunnable;
	Thread vThread;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.counter_layout, container,
				false);

		start = (Button) rootView.findViewById(R.id.start_counter_btn);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (canStart) {
					startCount();
					canStart = false;
				}
			}
		});
		stop = (Button) rootView.findViewById(R.id.stop_counter_btn);
		stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				stopCount();
				canStart = true;
			}
		});
		counterTxt = (TextView) rootView.findViewById(R.id.counterTxt);
		finishCount = (TextView) rootView.findViewById(R.id.finishCountTxt);

		if (savedInstanceState != null) {
			counter = savedInstanceState.getInt("counter");
			isStopped = savedInstanceState.getBoolean("isstopped");
			finishCount.setText(savedInstanceState.getString("finishedcount"));
			counterTxt.setText("" + counter);
			isStopped = true;
		}

		return rootView;
	}

	@Override
	protected void finalize() throws Throwable {
		Log.d("MainActivity", "Finalize " + CounterFragment.this.toString());
		super.finalize();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Bundle mBundle = new Bundle();
		mBundle.putInt("counter", counter);
		mBundle.putBoolean("isstopped", isStopped);
		mBundle.putString("finishedcount", finishCount.getText().toString());
		outState.putAll(mBundle);
		isStopped = false;
	}

	private void startCount() {
		// finishCount.setText("");
		// mThread = new MyThread();
		// mThread.start();
		// Secondo Modo
		mMyRunnable = new MyRunnable(this);
		vThread = new Thread(mMyRunnable);
		vThread.start();

	}

	private void stopCount() {
		finishCount.setText("Count was " + counter);
		counterTxt.setText("" + 0);
		counter = 0;
		/*
		 * isStopped = true; counterTxt.setText("" + 0);
		 */
		mMyRunnable.stopMyRunnable();
	}

	private void updateLabel(final int current) {

		/*
		 * if (getActivity() == null) { finishCount.setText("Count was " +
		 * counter); }
		 * 
		 * if (getActivity() != null) { getActivity().runOnUiThread(new
		 * Runnable() {
		 * 
		 * @Override public void run() { counterTxt.setText("" + current);
		 * counter = current; } }); }
		 */
		if (getActivity() != null) {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					counterTxt.setText("" + current);
					counter = current;
					Log.d("MainActivity", "Thread is running / Valore: "
							+ current);
				}
			});
		}
	}

	/*
	 * private class MyThread extends Thread {
	 * 
	 * int counter = 0;
	 * 
	 * @Override public void run() {
	 * 
	 * if (isStopped) { isStopped = false;
	 * 
	 * while (!isStopped) { counter++; updateLabel(counter); Log.v(TAG,
	 * Integer.toString(counter)); try { Thread.sleep(1000); } catch
	 * (InterruptedException e) { e.printStackTrace(); } } } } }
	 */

	// secondo modo per creare un thread: classe che implementa runnable
	public static class MyRunnable implements Runnable {

		int mCounter = CounterFragment.counter;
		WeakReference<CounterFragment> mRef;
		private boolean mStopped = false;

		public MyRunnable(CounterFragment aActivity) {
			mRef = new WeakReference<CounterFragment>(aActivity);
		}

		public void stopMyRunnable() {
			mStopped = true;
		}

		@Override
		public void run() {

			if (!mStopped) {
				while (!mStopped) {
					if (mRef != null) {
						CounterFragment vActivity = mRef.get();
						if (vActivity != null) {
							vActivity.updateLabel(mCounter);
						}
					}
					mCounter++;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
