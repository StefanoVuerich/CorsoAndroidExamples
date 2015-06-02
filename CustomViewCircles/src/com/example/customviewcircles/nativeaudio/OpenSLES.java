package com.example.customviewcircles.nativeaudio;

import android.content.res.AssetManager;
import android.util.Log;

public class OpenSLES {
	
	static int numChannelsUri = 0;
	static final int CLIP_PLAYBACK = 4;
	private boolean created;
	private AssetManager assetManager;
	static boolean isPlayingAsset = false;

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
	public static native void shutdownEngine();
	public static native void setNewRate(int rate);
	//public static native int getNumChannelsUriAudioPlayer();
	//public static native void createBufferQueueAudioPlayer();
	//public static native boolean createAudioRecorder();
    //public static native void startRecording();
    //public static native boolean selectClip(int which, int count);
    public static native boolean createAssetAudioPlayer(AssetManager assetManager, String filename);
    //public static native void setPlayingAssetAudioPlayer();
    //public static native void stopAssetAudioPlayer();
    public static native void setVolume(int millibel);
    //public static native int getSampleDuration();
    //public static native void checkCapabilities();
    //public static native int startCapabilities();

	static {
		Log.d("jajaja","Loading Libaries");
		System.loadLibrary("native-audio-jni");
		Log.d("jajaja","Init");
		createEngine();
		//createBufferQueueAudioPlayer();
		//startCapabilities();
	}
	
	public void getChannels() {
		if (numChannelsUri == 0) {
			//numChannelsUri = getNumChannelsUriAudioPlayer();
		}
	}
	
	public void record() {
		if (!created) {
			//created = createAudioRecorder();
		}
		if (created) {
			//startRecording();
		}
	}
	
	public void playBack() {
		//selectClip(CLIP_PLAYBACK, 3);
	}
	
	public void playTrack(AssetManager assetManager, String name) {
            created = createAssetAudioPlayer(assetManager, name);
	}
	
	public void setNewVolume(int rate) {
		Log.v("jajaja", "chiamato setnewvolume");
		setVolume(rate);
	}
	
	public void stopTrack(){
		//stopAssetAudioPlayer();
	}
}
