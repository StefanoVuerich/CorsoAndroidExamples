package com.example.customviewcircles.fragments;

import com.example.customviewcircles.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ControlSecondFragment extends Fragment {

	public final static String _TAG = "ControlSecondFragment";
	private Spinner effectsSpinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(
				R.layout.controls_second_fragment_layout, container, false);
		
		effectsSpinner = (Spinner)rootView.findViewById(R.id.effects_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.effetcs_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		effectsSpinner.setAdapter(adapter);

		return rootView;
	}

	public static ControlSecondFragment getInstance() {
		return new ControlSecondFragment();
	}
}
