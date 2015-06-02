package com.merlino.fragmentripasso;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {
	
	String[] colori = {"Bianco", "Rosso", "Verde", "Giallo", "Nero"};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView =  inflater.inflate(R.layout.list_layout, container, false);
		
		populateListView(rootView);
		
		return rootView;
	}

	private void populateListView(View rootView) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_layout, colori);
		ListView list = (ListView)rootView.findViewById(R.id.itemsList);
		list.setAdapter(adapter);
		
	}
	
}
