package com.corso.esercizioprovacronometro;

import java.security.acl.LastOwnerException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String LAST_LAP = "LAST_LAP";
	private ClockRunnable runnable;
	private Clock clock;
	private TextView time, lap;
	private Button startButton, lapButton, stopButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final RelativeLayout clockLayout = (RelativeLayout) findViewById(R.id.clockContainer);
		clockLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// make sure it is not called anymore
						clockLayout.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);

						clock = new Clock(MainActivity.this, clockLayout
								.getWidth(), clockLayout.getHeight());

						clockLayout.addView(clock);
					}
				});

		startButton = (Button) findViewById(R.id.startButton);
		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				runnable = new ClockRunnable(MainActivity.this, clock);
				new Thread(runnable).start();
				
				setIsCountingMode();
			}
		});
		
		lap = (TextView) findViewById(R.id.ultimoGiroLabel);

		lapButton = (Button) findViewById(R.id.lastLapButton);
		lapButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (runnable != null) {
					lap.setText("Ultimo giro: " + runnable.getTime());

				}
			}
		});

		stopButton = (Button) findViewById(R.id.stopButton);
		stopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (runnable != null) {
					runnable.stop();
				}
				
				setIsNotCountingMode();
			}
		});

		if (CustomApplication.getInstance().getRunnable() != null) {
			runnable = CustomApplication.getInstance().getRunnable();
			runnable.attachActivity(this, this.clock);
		}
		
		if(savedInstanceState != null) {
			lap.setText(savedInstanceState.getString(LAST_LAP));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v("jajaja", "pause");

		if (runnable != null)
			runnable.detachActivity();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (CustomApplication.getInstance().getRunnable() != null) {
			runnable.attachActivity(this, this.clock);
			setIsCountingMode();
		} else {
			setIsNotCountingMode();
		}
	}
	
	public Clock getClock() {
		return this.clock;
	}

	private void setIsCountingMode() {
		
		//clock.update(-1);
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		lapButton.setEnabled(true);
	}

	private void setIsNotCountingMode() {
		
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		lapButton.setEnabled(false);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(LAST_LAP, lap.getText().toString());
	}
	
	
}
