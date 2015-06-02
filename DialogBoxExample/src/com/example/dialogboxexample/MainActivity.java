package com.example.dialogboxexample;

import com.example.dialogboxexample.PrimoDialogo.IPrimoDialogo;
import com.example.dialogboxexample.SecondoDialog.ButtonPressHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements IPrimoDialogo , ButtonPressHandler{

	TextView mEtichetta;
	Button mButton, mButton2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEtichetta = (TextView)findViewById(R.id.etichetta);
		
		mButton = (Button)findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMyDialog();
			}
		});
		
		mButton2 = (Button)findViewById(R.id.button2);
		mButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMyDialog2();
			}
		});
	}
	
	private void showMyDialog() {
		PrimoDialogo vPD = PrimoDialogo.getInstance("Ecco il nuovo titolo");
		vPD.show(getFragmentManager(), "DIALOGO");
		
	}
	
	private void showMyDialog2() {
		SecondoDialog vSD = SecondoDialog.getInstance();
		vSD.show(getFragmentManager(), "Secondo Dialogo");
		
	}

	@Override
	public void onYes() {
		mEtichetta.setText("YES");
	}

	@Override
	public void onNo() {
		mEtichetta.setText("NO");
	}

	@Override
	public void onCancel() {
		mEtichetta.setText("CANCEL");
	}

	@Override
	public void OnButtonPress(int which) {
		
		String testo = "";
		
		switch(which) {
		case 0: testo = SecondoDialog.listItems[which];
				break;
		case 1: testo = SecondoDialog.listItems[which];
				break;
		case 2: testo = SecondoDialog.listItems[which];
				break;
		case 3: testo = SecondoDialog.listItems[which];
				break;
		case 4: testo = SecondoDialog.listItems[which];
				break;
		}
		mEtichetta.setText(testo);
	}
}
