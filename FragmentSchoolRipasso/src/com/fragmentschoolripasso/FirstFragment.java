package com.fragmentschoolripasso;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FirstFragment extends Fragment {

	private final static String RISULTATO = "Current risultato";
	int risultato = 0;
	Button piuBtn, menoBtn, inviaBtn, inviaBtn2;
	TextView etichettaRisultato;
	private final static String START_VALUE = "StartValue";

	public FirstFragment() {
	}
	
	public static FirstFragment getInstance(int aValue) {
		FirstFragment vFragment = new FirstFragment();
		Bundle vBundle = new Bundle();
		vBundle.putInt(START_VALUE , aValue);
		vFragment.setArguments(vBundle);
		return vFragment;
	}

	public interface ITestFragment {
		public void aggiornaEtichetta(String valore);
	}

	private ITestFragment mListener;

	public interface ITestFragment2 {
		public void aggiornaEtichetta2(String valore);
	}

	private ITestFragment2 mListener2;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(RISULTATO, risultato);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// testo se l'activity implementa ItestFragment
		if (activity instanceof ITestFragment) {
			mListener = (ITestFragment) activity;
		}
		if (activity instanceof ITestFragment2) {
			mListener2 = (ITestFragment2) activity;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		if(savedInstanceState != null) {
			risultato = savedInstanceState.getInt(RISULTATO);
		} else {
			Bundle vBundle = getArguments();
			if(vBundle != null) {
				risultato = vBundle.getInt(START_VALUE);
			}
		}

		piuBtn = (Button) rootView.findViewById(R.id.buttonPiu);
		piuBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				aggiornaValore("piu");
			}
		});

		menoBtn = (Button) rootView.findViewById(R.id.buttonMeno);
		menoBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				aggiornaValore("meno");
			}
		});

		inviaBtn = (Button) rootView.findViewById(R.id.inviaValore);
		inviaBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String valore = Integer.toString(risultato);
				if (mListener != null) {
					mListener.aggiornaEtichetta(valore);
				}
			}
		});

		inviaBtn2 = (Button) rootView.findViewById(R.id.inviaValore2);
		inviaBtn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String valore = Integer.toString(risultato);
				if (mListener2 != null) {
					mListener2.aggiornaEtichetta2(valore);
				}
			}
		});
		
		etichettaRisultato = (TextView)rootView.findViewById(R.id.etichettaRisultato);
		etichettaRisultato.setText("" + risultato);
		
		return rootView;
	}

	private void aggiornaValore(String operazione) {
		if (operazione.equals("piu")) {
			risultato += 1;
			etichettaRisultato.setText("" + risultato);
		} else {
			risultato -= 1;
			etichettaRisultato.setText("" + risultato);
		}
	}
}
