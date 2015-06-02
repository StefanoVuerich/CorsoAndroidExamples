package com.example.dialogboxexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PrimoDialogo extends DialogFragment{
	
	private final static String TITOLO = "Titolo";
	
	public interface IPrimoDialogo {
		
		public void onYes();
		public void onNo();
		public void onCancel();
		
	}
	
	private IPrimoDialogo mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(activity instanceof IPrimoDialogo) {
			mCallback = (IPrimoDialogo)activity;
		}
	}

	public static PrimoDialogo getInstance() {
		PrimoDialogo mPrimoDialogo = new PrimoDialogo();
		return mPrimoDialogo;
	}
	
	public static PrimoDialogo getInstance(String aTitle) {
		PrimoDialogo mPrimoDialogo = new PrimoDialogo();
		
		Bundle vBundle = new Bundle();
		vBundle.putString(TITOLO, aTitle);
		mPrimoDialogo.setArguments(vBundle);
		return mPrimoDialogo;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		
		Bundle vBundle = getArguments();
		if(vBundle != null){
			vBuilder.setTitle(vBundle.getString(TITOLO));
		} else {
			vBuilder.setTitle("My Title");
		}
		
		vBuilder.setMessage("My Message");
		
		vBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mCallback != null)
					mCallback.onYes();
			}
		});
		
		vBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mCallback != null)
					mCallback.onNo();
			}
		});
		
		vBuilder.setNeutralButton("NEUTRALE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mCallback != null)
					mCallback.onCancel();
			}
		});
		
		Dialog vDialog = vBuilder.create();
		return vDialog;
	}
}
