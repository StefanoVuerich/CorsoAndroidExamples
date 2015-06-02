package com.example.customviewcircles.fragments;

import com.example.customviewcircles.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ControlThirdFragment extends Fragment {

	public final static String _TAG = "ControlThirdFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(
				R.layout.controls_third_fragment_layout, container, false);

		return rootView;
	}

	public static ControlThirdFragment getInstance() {
		return new ControlThirdFragment();
	}
}
