package com.example.customviewcircles.fragments;

import java.security.acl.LastOwnerException;

import com.example.customviewcircles.R;
import com.example.customviewcircles.customviews.VerticalSeekBar;
import com.example.customviewcircles.nativeaudio.OpenSLES;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ControlFirstFragment extends Fragment implements
		OnSeekBarChangeListener {

	VerticalSeekBar volumeSeekbar;
	SeekBar pitchSeekBar;
	public final static String _TAG = "ControlFirstFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(
				R.layout.controls_first_fragment_layout, container, false);

		volumeSeekbar = (VerticalSeekBar) rootView
				.findViewById(R.id.volumeSeekBar);
		volumeSeekbar.setOnSeekBarChangeListener(this);

		pitchSeekBar = (SeekBar) rootView.findViewById(R.id.pitchSeekBar);
		pitchSeekBar.setOnSeekBarChangeListener(this);
		pitchSeekBar.setMax(100);

		return rootView;
	}

	int currentProgress;

	public static ControlFirstFragment getInstance() {
		return new ControlFirstFragment();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		currentProgress = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		OpenSLES.setNewRate(currentProgress);
	}
}
