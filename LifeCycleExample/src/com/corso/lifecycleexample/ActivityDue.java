package com.corso.lifecycleexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityDue extends Activity {
	
	private final static String LOG_TAG = "LifeCycleActivity2";
	private int mValue;
	private int mStartValue;
	TextView risultato;
	Button x2, passa;
	public static final String RISULTATO = "Risultato";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_due_layout);
		
		Intent intent = getIntent(); // mi riferisco all'intent che ha lanciato l'activity
		Bundle bundle = intent.getExtras();
		
		if(bundle != null) {
			mValue = bundle.getInt(MainActivity.VALORE);
			mStartValue = mValue;
		}
		
		risultato = (TextView)findViewById(R.id.risultato);
		// occhio agli int
		risultato.setText("" + mValue);
		
		x2 = (Button)findViewById(R.id.x2);
		x2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myNum = 0;
				try {
				    myNum = Integer.parseInt(risultato.getText().toString()) * 2;
				} catch(NumberFormatException nfe) {
				   System.out.println("Could not parse " + nfe);
				} 
				
				risultato.setText("" + myNum);
			}
		});
		
		passa = (Button)findViewById(R.id.passa);
		passa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				passala();
			}
		});
		
		Log.d(LOG_TAG, "OnCreate");
	}
	
	protected void passala() {
		Intent intent = new Intent(this, MainActivity.class);
		Bundle bundle = new Bundle();
		int ru = bundle.getInt(MainActivity.VALORE);
		bundle.putInt(MainActivity.VALORE, mStartValue);
		int ra = Integer.parseInt(risultato.getText().toString());
		bundle.putInt(ActivityDue.RISULTATO, ra);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(LOG_TAG, "OnSavedInstanceState");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(LOG_TAG, "OnStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(LOG_TAG, "OnRestart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(LOG_TAG, "OnPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(LOG_TAG, "OnStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "OnDestroy");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "OnResume");
	}

	
}
