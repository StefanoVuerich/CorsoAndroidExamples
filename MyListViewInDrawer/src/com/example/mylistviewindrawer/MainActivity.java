package com.example.mylistviewindrawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private DrawerLayout drawerL;
	private ListView listView;
	private MyAdapter myAdapter;
	private String[] myActions;
	private ActionBarDrawerToggle drawerListener;
	boolean longpressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		drawerL = (DrawerLayout) findViewById(R.id.drawer_layout);
		listView = (ListView) findViewById(R.id.drawerList);
		myAdapter = new MyAdapter(this);
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerL,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(MainActivity.this, "Drawer Open",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				Toast.makeText(MainActivity.this, "Drawer Close",
						Toast.LENGTH_SHORT).show();
			}
		};
		drawerL.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class MyAdapter extends BaseAdapter {

		private Context context;
		int[] myImages = { R.drawable.ic_action_call,
				R.drawable.ic_action_mail, R.drawable.ic_action_photo,
				R.drawable.ic_action_share };

		public MyAdapter(Context context) {
			this.context = context;
			myActions = context.getResources()
					.getStringArray(R.array.myActions);
		}

		@Override
		public int getCount() {
			return myActions.length;
		}

		@Override
		public Object getItem(int position) {
			return myActions[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.list_row, parent, false);
			} else {
				row = convertView;
			}
			TextView title = (TextView) row.findViewById(R.id.textView1);
			ImageView image = (ImageView) row.findViewById(R.id.imageView1);

			title.setText(myActions[position]);
			image.setImageResource(myImages[position]);

			return row;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// LinearLayout tmpLayout = (LinearLayout) view;
		// TextView tmpTextView = (TextView) tmpLayout.getChildAt(1);
		Toast.makeText(this, myActions[position] + " was selected",
				Toast.LENGTH_SHORT).show();
		selectItem(position);
		changeFragment(position);
	}

	private void changeFragment(int position) {
		Fragment fragment = null;
		
		switch(position) {
		case 0 : fragment = new CallFragment();
		break;
		case 1 : fragment = new MailFragment();
		break;
		case 2 : fragment = new PhotoFragment();
		break;
		case 3 : fragment = new ShareFragment();
		break;
		}
		drawerL.closeDrawers();
		FragmentManager fManager = getFragmentManager();
		fManager.beginTransaction().replace(R.id.mainContent, fragment).commit();
	}

	private void selectItem(int position) {
		listView.setItemChecked(position, true);
		setBarTitle(myActions[position]);
	}

	private void setBarTitle(String title) {
		getActionBar().setTitle(title);
	}
}
