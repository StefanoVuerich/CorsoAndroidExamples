package com.corso.compitofinalestefanovuerich;

import com.corso.compitofinalestefanovuerich.CounterAsyncTask.IOnCounterNotifyValue;
import com.corso.compitofinalestefanovuerich.NoGui.INoGui;
import com.corso.compitofinalestefanovuerich.ResetDialog.IActionsDialogo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements IOnCounterNotifyValue,
		INoGui, IActionsDialogo {

	public static final String TAG = "MYAPPTAG";
	public static final String COUNTER_VALUE = "COUNTER_VALUE";
	private static final String NO_GUI_TAG = "NO_GUI_TAG";
	private TextView counterText;
	private Button plus, minus, reset, start;
	private int counter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "MainActivity onCreate");
		setContentView(R.layout.activity_main);

		counterText = (TextView) findViewById(R.id.contatoreTextView);
		plus = (Button) findViewById(R.id.buttonPlus);
		minus = (Button) findViewById(R.id.buttonMinus);
		reset = (Button) findViewById(R.id.buttonReset);
		start = (Button) findViewById(R.id.buttonStart);

		if (savedInstanceState == null) {

		} else if (savedInstanceState != null) {
			counter = savedInstanceState.getInt(COUNTER_VALUE);
		}

		counterText.setText("" + counter);

		plus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				counterText.setText("" + ++counter);
			}
		});

		minus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (counter > 0) {
					counterText.setText("" + --counter);
				}
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				showResetDialog();
			}
		});

		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.add(NoGui.getInstance(counter), NO_GUI_TAG);
				ft.commit();
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(COUNTER_VALUE, counter);
		Log.v(TAG, "MainActivity savedinstance");
	}

	@Override
	public void onData(String aValue) {
		updateCounter(aValue);
	}

	public void updateCounter(String aValue) {
		counter = Integer.parseInt(aValue);
		counterText.setText("" + counter);
	}

	@Override
	public void onFinished(boolean hasFinished) {
		if(hasFinished) {
			counter = 0;
			counterText.setText("" + counter);
			Toast.makeText(getApplicationContext(), "Tempo scaduto",
					Toast.LENGTH_SHORT).show();
			Log.v(MainActivity.TAG, "main activity finished");
		}
	}

	@Override
	public void onValue(int aValue) {
	}

	private void showResetDialog() {
		ResetDialog vPD = ResetDialog.getInstance();
		vPD.show(getFragmentManager(), "ResetDialog");

	}

	@Override
	public void onYes() {

		NoGui noGuiFragment = (NoGui) getFragmentManager().findFragmentByTag(
				NO_GUI_TAG);
		if (noGuiFragment != null) {
			noGuiFragment.stopTask();
		}

		counter = 0;
		counterText.setText("" + 0);
	}

	@Override
	public void onNo() {
		// Do Nothing
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "MainActivity resume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "MainActivity onPause");
	}
	
	public int getCounter() {
		return this.counter;
	}
}
