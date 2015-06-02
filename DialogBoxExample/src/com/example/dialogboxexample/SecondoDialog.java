package com.example.dialogboxexample;

import com.example.dialogboxexample.PrimoDialogo.IPrimoDialogo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SecondoDialog extends DialogFragment {

	public static String[] listItems = { "Primo", "Secondo", "Terzo", "Quarto",
			"Quinto" };
	ButtonPressHandler mCallback;

	public static SecondoDialog getInstance() {
		SecondoDialog vItem = new SecondoDialog();
		return vItem;
	}

	public interface ButtonPressHandler {
		public void OnButtonPress(int which);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof IPrimoDialogo) {
			mCallback = (ButtonPressHandler) activity;
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
		vBuilder.setTitle("Sono una lista");
		vBuilder.setItems(listItems, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mCallback.OnButtonPress(which);
			}
		});

		Dialog vDialog = vBuilder.create();

		return vDialog;

	}

}
