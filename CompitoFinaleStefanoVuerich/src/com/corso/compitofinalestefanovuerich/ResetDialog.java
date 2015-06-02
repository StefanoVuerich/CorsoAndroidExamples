package com.corso.compitofinalestefanovuerich;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class ResetDialog extends DialogFragment{
	
	private final static String TITOLO = "Vuoi resettare il counter ?";
	
	public interface IActionsDialogo {		
		public void onYes();
		public void onNo();		
	}
	
	private IActionsDialogo mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(activity instanceof IActionsDialogo) {
			mCallback = (IActionsDialogo)activity;
		}
	}

	public static ResetDialog getInstance() {
		ResetDialog mPrimoDialogo = new ResetDialog();
		return mPrimoDialogo;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		
		vBuilder.setTitle(TITOLO);
		
		vBuilder.setMessage("Confermi la cancellazione del counter ?");
		
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
		
		Dialog vDialog = vBuilder.create();
		return vDialog;
	}
}
