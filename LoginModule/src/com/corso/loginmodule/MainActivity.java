package com.corso.loginmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String VALUES = "VALUES";
	private static final String COUNTER_VALUE = "COUNTER_VALUE";
	int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState != null) {
			counter = savedInstanceState.getInt(COUNTER_VALUE);
			
			((TextView) findViewById(R.id.counter)).setText(""
					+ counter);
		}

		((Button) findViewById(R.id.sendButton))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						((EditText) findViewById(R.id.nameInput))
								.setText("Pippo");
						((EditText) findViewById(R.id.surnameInput))
								.setText("De Paperoni");
					}
				});

		((Button) findViewById(R.id.buttonMinus))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						--counter;

						((TextView) findViewById(R.id.counter)).setText(""
								+ counter);
					}
				});

		((Button) findViewById(R.id.buttonPlus))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						++counter;

						((TextView) findViewById(R.id.counter)).setText(""
								+ counter);
					}
				});
		
		((Button)findViewById(R.id.goToSecondActivity)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText edit =  (EditText) findViewById(R.id.nameInput);
				String s = edit.getText().toString();
				
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(VALUES, s);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(COUNTER_VALUE, counter);
	}
	
	
}
