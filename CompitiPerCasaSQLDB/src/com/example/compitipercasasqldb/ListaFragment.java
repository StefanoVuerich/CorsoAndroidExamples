package com.example.compitipercasasqldb;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaFragment extends Fragment{

	ListView contactsListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.lista_layout, container, false);
		
		contactsListView = (ListView)rootView.findViewById(R.id.contactsListView);
		
		DBHelper dbHelper = new DBHelper(getActivity());
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();	
				
		Cursor mCursor = db.query(ContactsHelper.TABLE_NAME, new String[] {
				ContactsHelper._ID, ContactsHelper.NAME, ContactsHelper.SURNAME, ContactsHelper.NUMBER },
				null, null, null, null, null);
		
		ContactsAdapter mAdapter = new ContactsAdapter(getActivity(), mCursor);
		
		contactsListView.setAdapter(mAdapter);
		
		return rootView;
	}
	
	private class ViewHolder {
		public TextView mName , mNumber;
	}
	
	private class ContactsAdapter extends CursorAdapter {

		public ContactsAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			
			LayoutInflater mInflater = LayoutInflater.from(context);
			
			View mView = mInflater.inflate(R.layout.single_contact_layout, null);
			
			ViewHolder vHolder = new ViewHolder();
			
			vHolder.mName = (TextView)mView.findViewById(R.id.nameTxtView);
			vHolder.mNumber = (TextView)mView.findViewById(R.id.numberTxtView);
			
			mView.setTag(vHolder);
			
			return mView;			
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			int vNameColumnIndex = cursor.getColumnIndex(ContactsHelper.NAME);
			int vSurnameColumnIndex = cursor.getColumnIndex(ContactsHelper.SURNAME);
			int vNumberColumnIndex = cursor.getColumnIndex(ContactsHelper.NUMBER);
			
			ViewHolder vHolder = (ViewHolder)view.getTag();
			
			vHolder.mName.setText(cursor.getString(vNameColumnIndex)
									+ " "
									+ cursor.getString(vSurnameColumnIndex));
			
			vHolder.mNumber.setText(cursor.getString(vNumberColumnIndex));
		}
	}
}
