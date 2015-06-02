package com.example.customviewcircles;

import java.io.PrintWriter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.customviewcircles.MyDialog.IPrimoDialogo;
import com.example.customviewcircles.fragments.SamplerButtonsFragment;
import com.example.customviewcircles.fragments.AllControlsFragment;
import com.example.customviewcircles.nativeaudio.OpenSLES;

public class MainActivity extends Activity {

	OpenSLES player;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		FragmentManager fragmentManager = getFragmentManager();
		Fragment sampleButtonFragment, controlsFragment;

		if (savedInstanceState != null) {

			Log.v("jajajaFM", "SIS not nulla");

		} else {
			Log.v("jajajaFM", "SIS nulla");
			
			sampleButtonFragment = SamplerButtonsFragment.getInstance();
			controlsFragment = AllControlsFragment.getInstance();

			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(R.id.samplerButtonsContainer, sampleButtonFragment,
					SamplerButtonsFragment._TAG);
			ft.add(R.id.controlsContainer, controlsFragment,
					AllControlsFragment._TAG);
			ft.commit();
			
			player = OpenSLES.getIntance();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.v("jajajaFM", "on saved instance state");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("jajajaFM", "on start view");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.v("jajajaFM", "on restart view");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("jajajaFM", "on resume view");
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		Log.v("jajajaFM", "on post resume view");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("jajajaFM", "on stop view");
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		OpenSLES.shutdownEngine();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v("jajajaFM", "on pause view");
	}
}
