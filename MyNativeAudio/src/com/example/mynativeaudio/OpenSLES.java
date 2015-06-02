package com.example.mynativeaudio;

import android.util.Log;

public class OpenSLES {
	
	static int numChannelsUri = 0;
	static final int CLIP_PLAYBACK = 4;
	private boolean created;

	public static OpenSLES mInstance;
	
	private OpenSLES() {
		//System.loadLibrary("OpenSLES");
		init();
	}
	
	public static OpenSLES getIntance(){
		if(mInstance == null)
			mInstance = new OpenSLES();
		
		return mInstance;
	}
	
	private void init() {
		
		
	}
	
	public static native void createEngine();
	public static native int getNumChannelsUriAudioPlayer();
	public static native void createBufferQueueAudioPlayer();
	public static native boolean createAudioRecorder();
    public static native void startRecording();
    public static native boolean selectClip(int which, int count);

	static {
		Log.d("jajaja","Loading Libaries");
		System.loadLibrary("OpenSLES");
		Log.d("jajaja","Init");
		//createEngine();
		//createBufferQueueAudioPlayer();
	}
	
	public void getChannels() {
		if (numChannelsUri == 0) {
			numChannelsUri = getNumChannelsUriAudioPlayer();
		}
	}
	
	public void record() {
		if (!created) {
			created = createAudioRecorder();
		}
		if (created) {
			startRecording();
		}
	}
	
	public void playBack() {
		selectClip(CLIP_PLAYBACK, 3);
	}
}
