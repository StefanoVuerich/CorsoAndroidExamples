package com.corso.compitofinalestefanovuerich;

import com.corso.compitofinalestefanovuerich.CounterAsyncTask.IOnCounterNotifyValue;
import com.corso.compitofinalestefanovuerich.ResetDialog.IActionsDialogo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoGui extends Fragment implements IOnCounterNotifyValue {

	private INoGui mListener;
	private CounterAsyncTask mTask;
	private Integer currentStartingCountNumber = 0;
	private static final String CURRENT_NUMBER = "CURRENT_NUMBER";
	private MainActivity mainActivity;

	public static NoGui getInstance(int counterValue) {
		NoGui fragment = new NoGui();
		Bundle mBundle = new Bundle();
		mBundle.putInt(MainActivity.COUNTER_VALUE, counterValue);
		fragment.setArguments(mBundle);
		return fragment;
	}

	public interface INoGui {
		public void onData(String aValue);
		public void onFinished(boolean hasFinished);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//mainActivity = activity;
		if (activity instanceof INoGui) {
			mainActivity = (MainActivity) activity;
			mListener = (INoGui) activity;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		Bundle mBundle = getArguments();

		//???
		if (mBundle != null) {
			currentStartingCountNumber = mBundle
					.getInt(MainActivity.COUNTER_VALUE);
		}

		if (savedInstanceState != null) {
			int x = savedInstanceState.getInt(CURRENT_NUMBER);
			currentStartingCountNumber = x;

		}

		Log.v(MainActivity.TAG, "NoGui onCreate with " + CURRENT_NUMBER);

		mTask = new CounterAsyncTask();
		mTask.mListener = this;
		mTask.execute(currentStartingCountNumber);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(MainActivity.TAG, "NoGui onDestroy");
		mTask.cancel(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return null;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.v(MainActivity.TAG, "NoGui onDetach");
		mListener = null;
	}

	@Override
	public void onValue(int aValue) {
		if (mListener != null) {
			mListener.onData("" + aValue);
		}
	}

	@Override
	public void onFinished(boolean hasFinished) {
		Log.v(MainActivity.TAG, "NoGui onFinished");
		if (mListener != null) {
			mListener.onFinished(hasFinished);
			Log.v(MainActivity.TAG, "no gui finished");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(MainActivity.TAG, "NoGui onPause");
		mTask.pause(true);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		int number = ((MainActivity) mainActivity).getCounter();
		Log.v(MainActivity.TAG, "NoGui savedinstancestate:" + number);
		outState.putInt(CURRENT_NUMBER, number);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(MainActivity.TAG, "NoGui onResume");
		mTask.pause(false);
	}

	public void stopTask() {
		mTask.cancel(false);
	}

}
