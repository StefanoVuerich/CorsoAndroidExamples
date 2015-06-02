package com.corso.elencomarker;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.provider.Telephony.MmsSms;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements OnMapReadyCallback {

	MapFragment mapFragment;
	GoogleMap mMap;
	ArrayList<Marker> markersList;
	Button btnClear, btnShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.fragment1);
		mapFragment.getMapAsync(this);
		markersList = new ArrayList<Marker>();

		btnClear = (Button) findViewById(R.id.btnClear);
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMap.clear();
			}
		});

		btnShow = (Button) findViewById(R.id.btnShow);
		btnShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reShowMarker();
			}
		});
	}

	private void reShowMarker() {
		for (int i = 0; i < markersList.size(); i++) {
			Marker currentMarker = markersList.get(i);
			if (currentMarker.isVisible() == false) {
				currentMarker.setVisible(true);
			}
		}
	}

	@Override
	public void onMapReady(GoogleMap arg0) {
		LatLng latLanTarvisio = new LatLng(46.5057236, 13.583521);
		Marker mTarvisio = mMap.addMarker(new MarkerOptions().title("Tarvisio")
				.snippet("This is Tarvisio").position(latLanTarvisio));
		markersList.add(mTarvisio);

		LatLng latLanUdine = new LatLng(46.0560623, 13.2441623);
		Marker mUdine = mMap.addMarker(new MarkerOptions().title("Udine")
				.snippet("This is Udine").position(latLanUdine));
		markersList.add(mUdine);

		LatLng latLanPordenone = new LatLng(45.9571415, 12.6638464);
		Marker mPordenone = mMap.addMarker(new MarkerOptions()
				.title("Pordenone").snippet("This is Pordenone")
				.position(latLanPordenone));
		markersList.add(mPordenone);
	}
}
