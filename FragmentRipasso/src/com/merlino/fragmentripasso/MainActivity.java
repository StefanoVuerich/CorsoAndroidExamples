package com.merlino.fragmentripasso;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.fragmentContainer, new ListFragment(), "pippo")
					.add(R.id.fragmentContainer2, new ListFragment(), "pluto")
					.commit();
		}
	}
}
