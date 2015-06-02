package com.example.dialogboxnumberexercise;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements
		OperationsDialog.OnItemSelected, TabListener {

	public static final String CURRENTR = "currentR";
	private TextView numero;
	private Button piu, meno, dialog;
	private int currentNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar bar = getActionBar();
		bar.setTitle("Il titolo");
		bar.setSubtitle("Il sottotitolo");
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab1 = bar.newTab();
		tab1.setText("tab1");
		tab1.setTabListener(this);
		
		ActionBar.Tab tab2 = bar.newTab();
		tab2.setText("tab2");
		tab2.setTabListener(this);
		
		ActionBar.Tab tab3 = bar.newTab();
		tab3.setText("tab3");
		tab3.setTabListener(this);
		
		bar.addTab(tab1);
		bar.addTab(tab2);
		bar.addTab(tab3);
		
		
		if (savedInstanceState != null) {
			currentNumber = savedInstanceState.getInt(CURRENTR);
		}

		numero = (TextView) findViewById(R.id.number);
		numero.setText("" + currentNumber);

		meno = (Button) findViewById(R.id.meno);
		meno.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// currentNumber--;
				numero.setText("" + --currentNumber);
			}
		});

		piu = (Button) findViewById(R.id.piu);
		piu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// currentNumber++;
				numero.setText("" + ++currentNumber);
			}
		});

		dialog = (Button) findViewById(R.id.dialog);
		dialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openMyDialog();
			}
		});
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

	private void openMyDialog() {
		OperationsDialog dialog = new OperationsDialog();
		dialog.show(getFragmentManager(), "DIALOGO");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CURRENTR, currentNumber);
	}

	@Override
	public void updateResult(int operation) {
		switch (operation) {
		case 0:
			numero.setText("" + operation);
			currentNumber = operation;
			break;
		case 1:
			numero.setText("" + (currentNumber * 2));
			currentNumber = currentNumber * 2;
			break;
		case 2:
			numero.setText("" + (currentNumber * 3));
			currentNumber = currentNumber * 3;
			break;
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.v("jella", "Tab: " + tab);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.v("jella", "tab Unselected");
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.v("jella", "tab Reselected");
	}
}
