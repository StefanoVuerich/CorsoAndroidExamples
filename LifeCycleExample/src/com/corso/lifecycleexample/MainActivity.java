package com.corso.lifecycleexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private final static String LOG_TAG = "LifeCycle";
	private final static String CONTATORE = "Contatore";
	public static final String VALORE = "Valore";
	TextView mEtichetta, risultatoEt;
	Button mBottone;
	Button lancia;
	int mCounter = 0;
	int risultato = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		mEtichetta = (TextView)findViewById(R.id.etichetta);
		mBottone = (Button)findViewById(R.id.bottone);
		
		if(savedInstanceState != null) {
			mCounter = savedInstanceState.getInt(CONTATORE);
			if (mCounter > 0) {
				mEtichetta.setText("Bottone cliccato " + mCounter + " volte");
			}
		}
		
		Intent intent = getIntent(); // mi riferisco all'intent che ha lanciato l'activity
		Bundle bundle = intent.getExtras();
		
		if(bundle != null) {
			mCounter = bundle.getInt(MainActivity.VALORE);
			risultato = bundle.getInt(ActivityDue.RISULTATO);
			risultatoEt = (TextView)findViewById(R.id.risultato);
			risultatoEt.setText("Risultato: " + risultato);
			mEtichetta.setText("Bottone cliccato " + mCounter + " volte");
		}
		
		
		mBottone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCounter++;
				mEtichetta.setText("Bottone cliccato " + mCounter + " volte");
			}
		});
		
		lancia = (Button)findViewById(R.id.lanciaBtn);
		lancia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				lanciaActivity();
			}
		});
		
		Log.d(LOG_TAG, "OnCreate");
	}

	protected void lanciaActivity() {
		Intent intent = new Intent(this, ActivityDue.class);
		Bundle bundle = new Bundle();
		bundle.putInt(VALORE, mCounter);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(mCounter > 0) {
			outState.putInt(CONTATORE, mCounter);
		}
		Log.d(LOG_TAG, "OnSavedInstanceState");
	}
	
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		Log.d(LOG_TAG, "OnRestoreInstanceState");
//	}

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
		// giusto
		super.onResume();
		Log.d(LOG_TAG, "OnResume");
	}
}
