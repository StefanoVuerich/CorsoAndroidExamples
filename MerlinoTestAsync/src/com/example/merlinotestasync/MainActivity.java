package com.example.merlinotestasync;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	MyThread mThread;
	TextView textView;
	Button btnStart, btnStop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView1);

		btnStart = (Button) findViewById(R.id.btn1);
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				avviaCalcolo();
			}
		});

		btnStop = (Button) findViewById(R.id.btn2);
		btnStop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				interrompiCalcolo();
			}
		});
	}
	
	//Per secondo Modo
	MyRunnable mMyRunnable;
	Thread vThread;

	private void avviaCalcolo() {
		
		// Primo modo
		//mThread = new MyThread(this);
		//mThread.start();
		// Secondo Modo
		mMyRunnable = new MyRunnable(this);
		vThread = new Thread(mMyRunnable);
		vThread.start();
	}

	private void updateLabel(final int aValue) {
		// se vado a chiamare la funzione che aggiorna direttamente
		// l'interfaccia
		// grafica l'app andrà in crash
		// --> textView.setText("" + aValue);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				textView.setText("" + aValue);
				Log.d("MainActivity", "Thread is running / Valore: " + aValue);
			}
		});
	}

	private void interrompiCalcolo() {
		//Per Primo modo
		/*if(mThread != null) {
			mThread.stop();
		}*/
		//Per secondo modo
		//vThread.stop();
		mMyRunnable.stopMyRunnable();
	}

	// finalize è la funzione che viene chiamata quando la classe 
	//viene dichiarata idonea del garbage collectro ad essere distrutta
	@Override
	protected void finalize() throws Throwable {
		Log.d("MainActivity", "Finalize " + MainActivity.this.toString());
		super.finalize();
	}

	public static class MyThread extends Thread {

		int mCounter;
		WeakReference<MainActivity> mRef;

		public MyThread(MainActivity aActivity) {
			mRef = new WeakReference<MainActivity>(aActivity);
		}

		@Override
		public void run() {

			while (isAlive()) {
				if (mRef != null) {
					MainActivity vActivity = mRef.get();
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

	// secondo modo per creare un thread: classe che implementa runnable
	public static class MyRunnable implements Runnable {

		int mCounter;
		WeakReference<MainActivity> mRef;
		private boolean mStopped;

		public MyRunnable(MainActivity aActivity) {
			mRef = new WeakReference<MainActivity>(aActivity);
		}

		public void stopMyRunnable() {
			mStopped = true;
		}
		
		@Override
		public void run() {

			while (!mStopped) {
				if (mRef != null) {
					MainActivity vActivity = mRef.get();
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
