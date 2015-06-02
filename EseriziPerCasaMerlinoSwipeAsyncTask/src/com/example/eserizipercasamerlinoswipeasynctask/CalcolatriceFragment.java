package com.example.eserizipercasamerlinoswipeasynctask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CalcolatriceFragment extends Fragment {

	private boolean canBeOperator = false;
	private String digitedNumber = "";
	private TextView risultatoLabel;
	private Button zero, uno, due, tre, quattro, cinque, sei, sette, otto,
			nove;
	private Button diviso, moltiplica, sottrai, somma, risultato, cancel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.calcolatrice_layout,
				container, false);

		// operatori
		diviso = (Button) rootView.findViewById(R.id.btndiviso);
		diviso.setOnClickListener(new OnOperatorsClickListener());
		moltiplica = (Button) rootView.findViewById(R.id.btnmoltiplica);
		moltiplica.setOnClickListener(new OnOperatorsClickListener());
		sottrai = (Button) rootView.findViewById(R.id.btnmeno);
		sottrai.setOnClickListener(new OnOperatorsClickListener());
		somma = (Button) rootView.findViewById(R.id.btnpiu);
		somma.setOnClickListener(new OnOperatorsClickListener());
		risultato = (Button) rootView.findViewById(R.id.btnuguale);
		risultato.setOnClickListener(new OnResultClickListener());
		cancel = (Button) rootView.findViewById(R.id.btncancel);
		cancel.setOnClickListener(new OnCancelClickListener());
		// label
		risultatoLabel = (TextView) rootView.findViewById(R.id.risultatoLabel);
		// numeri
		zero = (Button) rootView.findViewById(R.id.btnzero);
		zero.setOnClickListener(new OnNumbersClickListener());
		uno = (Button) rootView.findViewById(R.id.btnuno);
		uno.setOnClickListener(new OnNumbersClickListener());
		due = (Button) rootView.findViewById(R.id.btndue);
		due.setOnClickListener(new OnNumbersClickListener());
		tre = (Button) rootView.findViewById(R.id.btntre);
		tre.setOnClickListener(new OnNumbersClickListener());
		quattro = (Button) rootView.findViewById(R.id.btnquattro);
		quattro.setOnClickListener(new OnNumbersClickListener());
		cinque = (Button) rootView.findViewById(R.id.btncinque);
		cinque.setOnClickListener(new OnNumbersClickListener());
		sei = (Button) rootView.findViewById(R.id.btnsei);
		sei.setOnClickListener(new OnNumbersClickListener());
		sette = (Button) rootView.findViewById(R.id.btnsette);
		sette.setOnClickListener(new OnNumbersClickListener());
		otto = (Button) rootView.findViewById(R.id.btnotto);
		otto.setOnClickListener(new OnNumbersClickListener());
		nove = (Button) rootView.findViewById(R.id.btnnove);
		nove.setOnClickListener(new OnNumbersClickListener());

		if (savedInstanceState != null) {
			canBeOperator = savedInstanceState.getBoolean("canbeoperator");
			digitedNumber = savedInstanceState.getString("digitnumber");
			String a = savedInstanceState.getString("risultatolabel");
			risultatoLabel.setText(a);
		}

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Bundle mBundle = new Bundle();
		mBundle.putBoolean("canbeoperator", canBeOperator);
		mBundle.putString("digitnumber", digitedNumber);
		mBundle.putString("risultatolabel", risultatoLabel.getText().toString());
		outState.putAll(mBundle);
	}

	public class OnNumbersClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Button tmp = (Button) v;
			String value = tmp.getText().toString();
			digitedNumber += value;
			risultatoLabel.setText(risultatoLabel.getText() + value);
			canBeOperator = true;
		}
	}

	public class OnOperatorsClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (canBeOperator) {
				Button tmp = (Button) v;
				String value = tmp.getText().toString();
				((MyApplication) (getActivity().getApplication())).stackOperatori
						.push(value);
				int numero = Integer.parseInt(digitedNumber);
				((MyApplication) (getActivity().getApplication())).stackNumeri
						.push(numero);
				risultatoLabel.setText(risultatoLabel.getText() + value);
				digitedNumber = "";
				canBeOperator = false;
			}
		}
	}

	public class OnCancelClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			canBeOperator = false;
			digitedNumber = "";
			risultatoLabel.setText("");
			((MyApplication) (getActivity().getApplication())).stackNumeri
					.clear();
			((MyApplication) (getActivity().getApplication())).stackOperatori
					.clear();
		}
	}

	public class OnResultClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			if (digitedNumber != "") {

				int numero = Integer.parseInt(digitedNumber);
				((MyApplication) (getActivity().getApplication())).stackNumeri
						.push(numero);

				while (!((MyApplication) (getActivity().getApplication())).stackNumeri
						.isEmpty()) {
					Integer x = ((MyApplication) (getActivity()
							.getApplication())).stackNumeri.pop();
					((MyApplication) (getActivity().getApplication())).orderedNumbersStack
							.push(x);
					while (!((MyApplication) (getActivity().getApplication())).stackOperatori
							.isEmpty()) {
						String z = ((MyApplication) (getActivity()
								.getApplication())).stackOperatori.pop();
						((MyApplication) (getActivity().getApplication())).orderedOperatorsStack
								.push(z);
					}
				}

				int result = 0;
				int firstNumber = 0;
				int secondNumber = 0;

				while (!((MyApplication) (getActivity().getApplication())).orderedNumbersStack
						.isEmpty()) {

					if (result == 0) {
						firstNumber = ((MyApplication) (getActivity()
								.getApplication())).orderedNumbersStack.pop();
						secondNumber = ((MyApplication) (getActivity()
								.getApplication())).orderedNumbersStack.pop();
					} else {
						secondNumber = ((MyApplication) (getActivity()
								.getApplication())).orderedNumbersStack.pop();
					}
					switch (((MyApplication) (getActivity().getApplication())).orderedOperatorsStack
							.pop()) {
					case "+":
						result = firstNumber + secondNumber;
						break;
					case "-":
						result = firstNumber + secondNumber;
						break;
					case "*":
						result = firstNumber * secondNumber;
						break;
					case "/":
						result = firstNumber / secondNumber;
						break;
					}
					firstNumber = result;
				}

				risultatoLabel.setText("" + result);
				digitedNumber = Integer.toString(result);
				((MyApplication) (getActivity().getApplication())).stackNumeri
						.clear();
				((MyApplication) (getActivity().getApplication())).stackOperatori
						.clear();
				canBeOperator = true;
			}
		}
	}
}
