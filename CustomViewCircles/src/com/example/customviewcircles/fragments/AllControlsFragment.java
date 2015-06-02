package com.example.customviewcircles.fragments;

import com.example.customviewcircles.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AllControlsFragment extends Fragment implements OnClickListener {

	public final static String _TAG = "ControlsFragmentAAA";
	Button first, second, third;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.controls_fragment_layout,
				container, false);
		
		first = (Button) rootView.findViewById(R.id.button1);
		first.setOnClickListener(this);

		second = (Button) rootView.findViewById(R.id.button2);
		second.setOnClickListener(this);

		third = (Button) rootView.findViewById(R.id.button3);
		third.setOnClickListener(this);

		getFragmentManager().beginTransaction().add(R.id.controlsRightContainer, new ControlFirstFragment(), ControlFirstFragment._TAG).commit();
		
		Log.v("jajajaFR","on create controls");
		
		return rootView;
	}
	
	public static AllControlsFragment getInstance() {
		return new AllControlsFragment();
	}
	
	@Override
	public void onClick(View v) {

		Fragment fragment = null;
		String TAG = "";

		switch (v.getId()) {
		case R.id.button1:
			fragment = ControlFirstFragment.getInstance();
			TAG = ControlFirstFragment._TAG;
			break;
		case R.id.button2:
			fragment = ControlSecondFragment.getInstance();
			TAG = ControlSecondFragment._TAG;
			break;
		case R.id.button3:
			fragment = ControlThirdFragment.getInstance();
			TAG = ControlThirdFragment._TAG;
			break;
		}
		getFragmentManager().beginTransaction().replace(R.id.controlsRightContainer, fragment, TAG).commit();
	}
}
