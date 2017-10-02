package mavinab.ops;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MyDialogFragment extends DialogFragment {

	static MyDialogFragment newInstance() {
		final String title = "My Fragment";
		final MyDialogFragment myDialogFragment = new MyDialogFragment();
		final Bundle args = new Bundle();
		args.putString("title", title);
		myDialogFragment.setArguments(args);
		return myDialogFragment;
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		Dialog mDialog = new Dialog(getActivity().getApplicationContext());
		mDialog.setContentView(R.layout.dialog_place_order);
		
		final String title = getArguments().getString("title");
		final Dialog myDialog = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setTitle(title)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						((MainActivity) getActivity()).okClicked();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						((MainActivity) getActivity()).cancelClicked();
					}
				}).create();

		return myDialog;
	}
}
