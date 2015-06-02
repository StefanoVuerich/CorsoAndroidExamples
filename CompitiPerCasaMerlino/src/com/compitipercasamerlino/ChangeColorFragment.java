package com.compitipercasamerlino;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChangeColorFragment extends Fragment{
	
	OnColoriButtonPressed mCallback;
	
	public interface OnColoriButtonPressed {
        public void changeBackgroundColor(int colore);
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            mCallback = (OnColoriButtonPressed) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.change_color_fragment_layout,
				container, false);
		
		Button changeColor = (Button)rootView.findViewById(R.id.myChangeColorBtn);
		changeColor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "cliccato", Toast.LENGTH_LONG).show();
				
				int random = (int) Math.floor(Math.random() * 4);
				
				mCallback.changeBackgroundColor(random);
			}
		});
		
		return rootView;
	}
}
