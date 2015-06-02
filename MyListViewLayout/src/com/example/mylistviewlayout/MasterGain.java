package com.example.mylistviewlayout;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MasterGain extends Fragment{

	public interface IMasterGain {
		public void masterGainChanged(int percentage);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		
		if (activity instanceof IMasterGain)
			activityListener = (IMasterGain) activity;
	}
	
	private IMasterGain activityListener;
	private SeekBar masterGainSeekBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.master_gain_layout, container,
				false);
		
		masterGainSeekBar = (SeekBar)rootView.findViewById(R.id.masterGainSeek);
		masterGainSeekBar.setMax(100);
		masterGainSeekBar.setProgress(100);
		masterGainSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				sendPercentage(progress);
			}
		});
		
		return rootView;
	}
	
	private void sendPercentage(int progress) {
		if (activityListener != null) {
			double percentage = progress * 100.0 / masterGainSeekBar.getMax(); 
			activityListener.masterGainChanged((int)Math.round(percentage));
		}
	}
}
