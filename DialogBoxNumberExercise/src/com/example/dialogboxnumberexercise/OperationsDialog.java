package com.example.dialogboxnumberexercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class OperationsDialog extends DialogFragment {

	public static String[] listItems = { "Azzera", "Raddoppia", "Triplica" };
	OnItemSelected mCallback;

	public interface OnItemSelected {
		public void updateResult(int operation);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof OnItemSelected) {
			mCallback = (OnItemSelected) activity;
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		vBuilder.setTitle("Aggiorna numero");
		vBuilder.setItems(listItems, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mCallback != null) {
					mCallback.updateResult(which);
				}
			}
		});

		Dialog vDialog = vBuilder.create();

		return vDialog;

	}
}
