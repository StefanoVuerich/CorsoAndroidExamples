package com.corso.loginmodule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		Bundle b = getIntent().getExtras();
		
		((TextView)findViewById(R.id.resultText)).setText(b.getString(MainActivity.VALUES));
	}
}
