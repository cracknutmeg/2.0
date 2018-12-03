package com.allpoint.activities.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class AlertDialogFragment extends DialogFragment {

	private String title;
	private String message;
	private String positiveText;
	private String negativeText;
	private DialogInterface.OnClickListener positiveListener;
	private DialogInterface.OnClickListener negativeListener;

	private static AlertDialogFragment instance;

	/**
	 * Creates simple AlertDialogFragment without title
	 * 
	 * @param message
	 *            Message text
	 * @param positiveText
	 *            Text on positive button
	 */
	public AlertDialogFragment(final String message, final String positiveText) {
		this.message = message;
		this.positiveText = positiveText;
		instance = this;
	}

	/**
	 * Creates simple AlertDialogFragment with title
	 * 
	 * @param title
	 *            Title text
	 * @param message
	 *            Message text
	 * @param positiveText
	 *            Text on positive button
	 */
	public AlertDialogFragment(final String title, final String message,
			final String positiveText) {
		this.title = title;
		this.message = message;
		this.positiveText = positiveText;
		instance = this;
	}

	/**
	 * Creates AlertDialogFragment without title
	 * 
	 * @param message
	 *            Message text
	 * @param positiveText
	 *            Text on positive button
	 * @param onPositiveListener
	 *            Positive button clicklistener
	 */
	public AlertDialogFragment(final String message, final String positiveText,
			final DialogInterface.OnClickListener onPositiveListener) {
		this.message = message;
		this.positiveText = positiveText;
		this.positiveListener = onPositiveListener;
		instance = this;
	}

	/**
	 * Creates AlertDialogFragment without title
	 * 
	 * @param message
	 *            Message text
	 * @param positiveText
	 *            Text on positive button
	 * @param onPositiveListener
	 *            Positive button clicklistener
	 * @param negativeText
	 *            Text on negative button
	 * @param onNegativeListener
	 *            Negative button clicklistener
	 */
	public AlertDialogFragment(final String message, final String positiveText,
			final DialogInterface.OnClickListener onPositiveListener,
			final String negativeText,
			final DialogInterface.OnClickListener onNegativeListener) {

		this.message = message;
		this.positiveText = positiveText;
		this.positiveListener = onPositiveListener;
		this.negativeText = negativeText;
		this.negativeListener = onNegativeListener;
		instance = this;
	}

	/**
	 * @return Returns AlertDialogFragment instance if it does exists
	 */
	public static AlertDialogFragment getInstance() {
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onDestroyView() {
		// removes the dismiss message to avoid shown dialogs being dismissed.
		if (getDialog() != null && getRetainInstance()) {
			getDialog().setDismissMessage(null);
		}
		super.onDestroyView();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		instance = null;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setMessage(this.message);

		if (this.title != null) {
			builder.setTitle(this.title);
		}

		if (this.positiveListener != null) {
			builder.setPositiveButton(this.positiveText, this.positiveListener);
		} else if (this.positiveText != null) {
			builder.setPositiveButton(this.positiveText, null);
		}

		if (this.negativeListener != null) {
			builder.setNegativeButton(this.negativeText, this.negativeListener);
		}

		return builder.create();
	}

	@Override
	public void show(FragmentManager manager, String tag) {

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			// only for gingerbread and newer versions
			if (instance != null) {
				FragmentTransaction ft = manager.beginTransaction();
				final Fragment prev = instance;
				// final Fragment prev = manager.findFragmentByTag(tag);

				try {
					ft.remove(prev);

				} catch (Exception e) {
					// TODO: handle exception

				}
				ft.addToBackStack(null);
				ft.commit();
			}

			super.show(manager, tag);
		} else {
			if (instance != null && getRetainInstance()) {
				FragmentTransaction ft = manager.beginTransaction();
				final Fragment prev = instance;
				// final Fragment prev = manager.findFragmentByTag(tag);

				try {
					ft.remove(prev);
				} catch (Exception e) {
					// TODO: handle exception

				}
				ft.addToBackStack(null);
				ft.commit();
			}

			super.show(manager, tag);
		}

	}

}
