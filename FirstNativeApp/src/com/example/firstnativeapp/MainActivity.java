package com.example.firstnativeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText input;
	private RadioGroup type;
	private TextView resultTxt;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.enterNumber);
		type = (RadioGroup) findViewById(R.id.type);
		resultTxt = (TextView) findViewById(R.id.result);
		button = (Button) findViewById(R.id.calculate);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = input.getText().toString();
				if (TextUtils.isEmpty(s))
					return;
				
				final ProgressDialog dialog = ProgressDialog.show(MainActivity.this,"","Calculationg", true);
				final long n = Long.parseLong(s);
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {
						long result = 0;
						long t = System.currentTimeMillis();
						switch (MainActivity.this.type.getCheckedRadioButtonId()) {
						case R.id.fibJR:
							result = FibLib.fibJR(n);
							break;
						case R.id.fibJI:
							result = FibLib.fibJI(n);
							 break;
						case R.id.fibNR:
							result = FibLib.fibNR(n);
							break;
						case R.id.fibNI:
							result = FibLib.fibNI(n);
							break;
						}
						t = System.currentTimeMillis() - t;
						return String.format("Fib(%d)=%d in %d ms", n,
								result, t);
					}

					@Override
					protected void onPostExecute(String result) {
						dialog.dismiss();
						MainActivity.this.resultTxt.setText(result);
					}
				}.execute();
			}
		});
	}
}
