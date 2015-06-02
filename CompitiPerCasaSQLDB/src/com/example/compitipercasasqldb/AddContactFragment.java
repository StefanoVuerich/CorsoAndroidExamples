package com.example.compitipercasasqldb;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactFragment extends Fragment{

	public static final String NAME = "NAME";
	public static final String SURNAME = "SURNAME";
	public static final String NUMBER = "NUMBER";
	
	EditText nome, cognome, numero;
	Button registra;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.addcontact_layout, container, false);
		
		nome = (EditText)rootView.findViewById(R.id.inputName);
		cognome = (EditText)rootView.findViewById(R.id.inputSurname);
		numero = (EditText)rootView.findViewById(R.id.inputNumber);
		
		if(savedInstanceState != null) {
			nome.setText(savedInstanceState.getString(NAME));
			cognome.setText(savedInstanceState.getString(SURNAME));
			numero.setText(savedInstanceState.getString(NUMBER));
		}
		
		registra = (Button)rootView.findViewById(R.id.btnAddContact);
		registra.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cName = nome.getText().toString();
				String cSurname = cognome.getText().toString();
				String cNumber = numero.getText().toString();
				
				DBHelper dbHelper = new DBHelper(getActivity());
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				
				ContentValues values = new ContentValues();	
				values.put(ContactsHelper.NAME, cName);
				values.put(ContactsHelper.SURNAME, cSurname);
				values.put(ContactsHelper.NUMBER, cNumber);
				
				db.insert(ContactsHelper.TABLE_NAME, null, values);
				
				Toast.makeText(getActivity(), "Record Inserito", Toast.LENGTH_SHORT).show();
			}
		});
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Bundle vBundle = new Bundle();
		vBundle.putString(NAME, nome.getText().toString());
		vBundle.putString(SURNAME, cognome.getText().toString());
		vBundle.putString(NUMBER, numero.getText().toString());
		outState.putAll(vBundle);
	}
	
	
}
