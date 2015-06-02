package com.example.customviewcircles.nativeaudio;

public class SlotManager {
	
	private final static int slotsNumber = 9;
	private static SlotManager mInstance;
	public Sample[] getSlots() {
		return slots;
	}

	public void setSlots(Sample[] slots) {
		this.slots = slots;
	}

	private Sample[] slots;
	
	private SlotManager() {
		slots = new Sample[slotsNumber];
		init();
	}
	
	private void init() {
		if(slots != null) {
			slots[0] = new Sample("first.wav");
			slots[1] = new Sample("second.wav");
			slots[2] = new Sample("third.wav");
			slots[3] = new Sample("fourth.wav");
			slots[4] = new Sample("fifth.wav");
			slots[6] = new Sample("fifth.wav");
			slots[7] = new Sample("fifth.wav");
			slots[8] = new Sample("fifth.wav");
		}
	}

	public static SlotManager getInstance() {
		if(mInstance == null)
			mInstance = new SlotManager();
			
		return mInstance;
	}
}
