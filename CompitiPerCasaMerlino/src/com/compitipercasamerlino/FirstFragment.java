package com.compitipercasamerlino;

import com.compitipercasamerlino.ChangeColorFragment.OnColoriButtonPressed;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstFragment extends Fragment implements ChangeBGHandler {

	OnButtonInfoFragment mCallback;
	View myRootView;
	String annuncio;
	public static String MY_START_VALUE = "start";

	private FirstFragment() {
	}

	public static FirstFragment getInstance(String annuncio) {
		FirstFragment vFragment = new FirstFragment();
		Bundle vBundle = new Bundle();
		vBundle.putString(MY_START_VALUE, annuncio);
		vFragment.setArguments(vBundle);
		return vFragment;
	}
	
	public interface OnButtonInfoFragment {
        public void notificateMainActivity(String notificatinon);
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            mCallback = (OnButtonInfoFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_uno_layout,
				container, false);
		
		myRootView = rootView;

		final TextView annuncioTxt = (TextView) rootView
				.findViewById(R.id.annuncioUno);

		Bundle bundle = getArguments();
		if (bundle != null) {
			annuncio = bundle.getString(MY_START_VALUE);
		}

		if (savedInstanceState != null) {
			annuncio = savedInstanceState.getString(MY_START_VALUE);
		}

		Button chiSono = (Button) rootView.findViewById(R.id.chiSono2);
		chiSono.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				annuncioTxt.setText(annuncio);
				mCallback.notificateMainActivity(annuncio);
			}
		});

		return rootView;
	}

	@Override
	public void changeBGColor(int colore) {

		int myColor = 0;
		switch (colore) {
		case 0:
			myColor = Color.WHITE;
			break;
		case 1:
			myColor = Color.BLUE;
			break;
		case 2:
			myColor = Color.YELLOW;
			break;
		case 3:
			myColor = Color.RED;
			break;
		case 4:
			myColor = Color.GREEN;
			break;
		}
		
		RelativeLayout layout = (RelativeLayout)myRootView.findViewById(R.id.r_layout_fragment_uno);
		layout.setBackgroundColor(myColor);

	}
}
