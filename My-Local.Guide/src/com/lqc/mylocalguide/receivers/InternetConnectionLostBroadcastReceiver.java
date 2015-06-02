package com.lqc.mylocalguide.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lqc.mylocalguide.activities.NoInternetActivity;

public class InternetConnectionLostBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle extras = intent.getExtras();
		if (extras != null) {
			
	         for (String key: extras.keySet()) {
	            
	            if(extras.get(key).toString().contains("DISCONNECTED/DISCONNECTED")) {
	            	
	            	Intent mIntent = new Intent(context, NoInternetActivity.class);
		            context.startActivity(mIntent);
	            }
	         }
	      }
	}
}
