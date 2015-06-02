package com.example.customviewcircles.threading;

public class PressTimer {
	
	private static PressTimer mInstance;
	
	private PressTimer() {}
	
	public static PressTimer getInstance() {
		if(mInstance == null)
			mInstance = new PressTimer();
		
		return mInstance;	
	}
	
	private long startTime = System.currentTimeMillis();
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getTime(long finishTime) {
		
		return finishTime - startTime;
	}

}
