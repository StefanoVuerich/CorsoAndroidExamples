package com.example.customviewcircles.nativeaudio;

public class CanPlayHandler {
	
	private static CanPlayHandler mInstance;
	private static boolean _canPlay;
	
	private CanPlayHandler() {}
	
	public static CanPlayHandler getInstance() {
		
		if(mInstance == null) {
			mInstance = new CanPlayHandler();
			_canPlay = true;
		}
		
		return mInstance;
	}

	public boolean canPlay() {
		return _canPlay;
	}

	public void setCanPlay(boolean canPlay) {
		CanPlayHandler._canPlay = canPlay;
	}

	
}
