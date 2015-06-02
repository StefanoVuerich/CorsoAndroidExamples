package com.example.listviewexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewFragment extends Fragment{
	
	String[] colori = {"Bianco", "Rosso", "Verde", "Giallo", "Nero"};
	
	public ListViewFragment(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.listview_layout, container,
				false);
		populateListView(rootView);
		return rootView;
	}

	private void populateListView(View rootView) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_layout, colori);
		ListView list = (ListView)rootView.findViewById(R.id.myListView);
		list.setAdapter(adapter);
	}
	
	

}
