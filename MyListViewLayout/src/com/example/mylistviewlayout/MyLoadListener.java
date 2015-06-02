package com.example.mylistviewlayout;

import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;

public class MyLoadListener implements OnLoadCompleteListener{

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		Log.v(EffectsDeck.LOG_TEXT, "on load complete chiamato");
		
	}

}
