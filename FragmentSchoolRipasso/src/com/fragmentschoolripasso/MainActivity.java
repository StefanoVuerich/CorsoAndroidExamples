package com.fragmentschoolripasso;

import com.fragmentschoolripasso.FirstFragment.ITestFragment;
import com.fragmentschoolripasso.FirstFragment.ITestFragment2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements ITestFragment, ITestFragment2{
	
	
	public String a, b;
	TextView mActivityTextView,  mActivityTextView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mActivityTextView = (TextView)findViewById(R.id.mATextView);
		mActivityTextView2 = (TextView)findViewById(R.id.mATextView2);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.fragmentContainer, FirstFragment.getInstance(100)).commit();
		} else {
			getFragmentManager().beginTransaction()
			.add(R.id.fragmentContainer, FirstFragment.getInstance(100)).commit();
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("first", mActivityTextView.getText().toString());
		outState.putString("second", mActivityTextView2.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		String a = savedInstanceState.getString("first");
		mActivityTextView.setText(a);
		String b = savedInstanceState.getString("second");
		mActivityTextView2.setText(b);
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
	public void aggiornaEtichetta(String valore) {
		mActivityTextView.setText(valore);		
	}

	@Override
	public void aggiornaEtichetta2(String valore) {
		mActivityTextView2.setText(valore);
	}
}
