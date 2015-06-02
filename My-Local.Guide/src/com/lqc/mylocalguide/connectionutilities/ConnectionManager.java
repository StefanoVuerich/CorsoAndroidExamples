package com.lqc.mylocalguide.connectionutilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {
	
	private static ConnectionManager mInstance;
	private static ConnectivityManager connectionManager;
	
	private ConnectionManager() {}
	
	public static ConnectionManager getInstance(Context context) {
		if(mInstance == null) {
			mInstance = new ConnectionManager();
			connectionManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		
		return mInstance;
	}
	
	public boolean checkForInternetConnection(Context context) {
		
		NetworkInfo activeNetwork = connectionManager.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		if (!isConnected)
			return false;

		return true;
	}

}
