package com.compitipercasamerlino;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements ChangeColorFragment.OnColoriButtonPressed
														, FirstFragment.OnButtonInfoFragment
														, SecondFragment.OnButtonInfoFragment2{

	Button btn1, btn2;
	Context context = MainActivity.this;
	TextView infoFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.fragmentContainer,
							FirstFragment.getInstance("Sono il fragment HOME"), "F1_Tag")
					.commit();
		}

		btn1 = (Button) findViewById(R.id.fragment1Btn);
		btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Fragment fragment = FirstFragment
						.getInstance("Sono il fragment 01");
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.fragmentContainer, fragment, "F1_Tag")
						.commit();
			}
		});

		btn2 = (Button) findViewById(R.id.fragment2Btn);
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Fragment fragment = SecondFragment
						.getInstance("Sono il fragment 02");
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.fragmentContainer, fragment, "F2_Tag")
						.commit();
			}
		});

		ChangeColorFragment changeColorFragment = new ChangeColorFragment();
		FragmentManager manager = getFragmentManager();
		manager.beginTransaction()
				.add(R.id.changeColorContainer, changeColorFragment,
						"Change_Color_Fragment").commit();
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
	public void changeBackgroundColor(int colore) {
		
		FirstFragment first = (FirstFragment)getFragmentManager().findFragmentByTag("F1_Tag");
		
		if (first != null && first.isVisible()) {
			first.changeBGColor(colore);
		}
		else {
			SecondFragment second = (SecondFragment)getFragmentManager().findFragmentByTag("F2_Tag");
			second.changeBGColor(colore);
		}
	}

	@Override
	public void notificateMainActivity(String notification) {
		
		infoFragment = (TextView)findViewById(R.id.infoFragmentMainActivityTextView);
		infoFragment.setText(notification);
		
	}

	@Override
	public void notificateMainActivity2(String notification) {
		infoFragment = (TextView)findViewById(R.id.infoFragmentMainActivityTextView);
		infoFragment.setText(notification);
		
	}
}
