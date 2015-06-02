package com.example.mylistviewlayout;

import com.example.mylistviewlayout.MasterGain.IMasterGain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements IMasterGain {
	
	private int volumePercentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sampler_layout);
		if (savedInstanceState == null) {
			//getFragmentManager().beginTransaction()
					//.add(R.id.container, new Channel()).commit();
		}
		
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void masterGainChanged(int percentage) {
		
		this.volumePercentage = percentage;
		
		
		EffectsDeck deckFragment = new EffectsDeck();
		
		
		deckFragment.masterGainVolumeListener(percentage);
		
		
		Log.v(EffectsDeck.LOG_TEXT, "percentage: " + percentage);
	}
}
