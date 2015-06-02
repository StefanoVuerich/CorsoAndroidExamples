package com.example.merlinoasynctask;

import com.example.merlinoasynctask.MyAsyncTask.IOnNotifyValue;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoGui extends Fragment implements IOnNotifyValue{

	private INoGui mListener;
	private MyAsyncTask mTask;
	
	public static NoGui get() {
		return new NoGui();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof INoGui) {
			mListener = (INoGui) activity;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		
		mTask = new MyAsyncTask();
		mTask.mListener = this;
		mTask.execute();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
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
		mListener = null;
	}

	public interface INoGui {
		public void onData(String aValue);
	}

	@Override
	public void onValue(int aValue) {
		if(mListener != null) {
			mListener.onData("" + aValue);
		}
	}
}
