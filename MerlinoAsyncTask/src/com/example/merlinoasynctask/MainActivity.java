package com.example.merlinoasynctask;

import com.example.merlinoasynctask.MyAsyncTask.IOnNotifyValue;
import com.example.merlinoasynctask.NoGui.INoGui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Debug;
import android.widget.TextView;

public class MainActivity extends Activity implements IOnNotifyValue, INoGui{

	TextView myLabel;
	MyAsyncTask mTask;
	private static final String NO_GUI_TAG = "NOGUI";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Debug.startMethodTracing("TracciaturaMetodo");
		
		setContentView(R.layout.activity_main);
		
		myLabel = (TextView) findViewById(R.id.myLabel);
		updateLabel("0");
		
		NoGui fr = (NoGui)getFragmentManager().findFragmentByTag(NO_GUI_TAG);
		if(fr == null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(NoGui.get(), NO_GUI_TAG);
			ft.commit();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		Debug.stopMethodTracing();
	}

	private void updateLabel(String aValue) {
		if(myLabel != null) {
			myLabel.setText(aValue);
		}
	}

	@Override
	public void onValue(int aValue) {
		myLabel.setText("" + aValue);
	}



	@Override
	protected void onDestroy() {		
		super.onDestroy();
		//mTask.cancel(true);
	}

	@Override
	public void onData(String aValue) {
		updateLabel(aValue);
	}
}
