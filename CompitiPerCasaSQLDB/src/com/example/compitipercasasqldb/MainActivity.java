package com.example.compitipercasasqldb;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String ADDCONTACTFRAGMENT = "ACFr";
	private static final String LISTFRAGMENT = "LsFr";
	Button lista, addContact;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {

			ListaFragment homeFragment = new ListaFragment();

			getFragmentManager().beginTransaction()
					.replace(R.id.fragmentContainer, homeFragment, LISTFRAGMENT).commit();
		}

		lista = ((Button) findViewById(R.id.btnLista));
		lista.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//Fragment fr = getFragmentManager().findFragmentByTag("ACFr");
				//getFragmentManager().beginTransaction().remove(fr);
				
				Fragment fr = getFragmentManager().findFragmentByTag(LISTFRAGMENT);
				
				if (fr == null || !(fr instanceof ListaFragment)) {
					getFragmentManager()
					.beginTransaction()
					.replace(R.id.fragmentContainer,
					new ListaFragment(), LISTFRAGMENT).commit();
				}
			}
		});

		addContact = (Button) findViewById(R.id.btnAddContact);
		addContact.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//Fragment fr = getFragmentManager().findFragmentByTag("LsFr");
				//getFragmentManager().beginTransaction().remove(fr);
				
				Fragment fr = getFragmentManager().findFragmentByTag(ADDCONTACTFRAGMENT);
				
				if (fr == null || !(fr instanceof AddContactFragment)) {
					getFragmentManager()
					.beginTransaction()
					.replace(R.id.fragmentContainer,
					new AddContactFragment(), ADDCONTACTFRAGMENT).commit();
				}
			}
		});
	}
}
