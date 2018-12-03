/**
 *@ com.allpoint.util.ShareApp
 */
package com.allpoint.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.allpoint.AtmFinderApplication;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

/**
 * ShareApp Simple sharing class (based on library SocialAuth)
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class ShareApp {

	/**
	 * Adapter for library "SocialAuth".
	 */
	private static final SocialAuthAdapter adapter = new SocialAuthAdapter(
			new ResponseListener());

	/**
	 * Default success posting message.
	 */
	public static String DEFAULT_ALERT_COMPLETE_TEXT = "Message posted on ";

	/**
	 * Default unsuccess posting message.
	 */
	public static String DEFAULT_ALERT_ERROR_TEXT = "Message not posted on ";

	/**
	 * Posting message.
	 */
	private static String messageToProvider;

	/**
	 * Enable or Disable alert messages.
	 */
	private static boolean enableAlertMessage;

	/**
	 * Custom success posting message.
	 */
	private static String completeMessage;

	/**
	 * Custom unsuccess posting message.
	 */
	private static String errorMessage;

	/**
	 * @param context
	 *            Used context
	 * @param message
	 *            Posting message
	 * @param showAlertMessage
	 *            Allow showing alert messages
	 * @param alertComplete
	 *            Custom success posting message
	 * @param alertError
	 *            Custom unsuccess posting message
	 */
	public static void toFacebook(final Context context, final String message,
			final boolean showAlertMessage, final String alertComplete,
			final String alertError) {
		messageToProvider = message;
		enableAlertMessage = showAlertMessage;
		completeMessage = alertComplete;
		errorMessage = alertError;

		adapter.authorize(context, Provider.FACEBOOK);
	}

	/**
	 * @param context
	 *            Used context
	 * @param message
	 *            Posting message
	 * @param showAlertMessage
	 *            Allow showing alert messages
	 * @param alertComplete
	 *            Custom success posting message
	 * @param alertError
	 *            Custom unsuccess posting message
	 */
	public static void toTwitter(final Context context, final String message,
			final boolean showAlertMessage, final String alertComplete,
			final String alertError) {
		messageToProvider = message + " #Allpoint";
		enableAlertMessage = showAlertMessage;
		completeMessage = alertComplete;
		errorMessage = alertError;
		adapter.addCallBack(Provider.TWITTER,
				"http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		adapter.authorize(context, Provider.TWITTER);
	}

	/**
	 * @param context
	 *            Used context
	 * @param mailTo
	 *            "mailto:" field text
	 * @param subject
	 *            "subject:" field text
	 * @param message
	 *            "text:" field text
	 */
	public static void byEmail(final Context context, final String mailTo,
			final String subject, final String message) {

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", mailTo, null));
		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		context.startActivity(emailIntent);
	}

	/**
	 * @param context
	 *            Used context
	 * @param message
	 *            Text of sms-message
	 */
	public static void bySMS(final Context context, final String message) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("sms_body", message);
		context.startActivity(smsIntent);
	}

	private static class ResponseListener implements DialogListener {

		@Override
		public void onComplete(final Bundle values) {
			// "shareOption" allow sending messages to few providers
			adapter.updateStatus(messageToProvider, new MessageListener(),
					false);
		}

		@Override
		public void onError(SocialAuthError socialAuthError) {
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onBack() {
		}
	}

	// To get status of message after authentication

	/**
	 * @author vyacheslav.shmakin Inner listener of processing errors and
	 *         messages
	 */
	private static class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onError(final SocialAuthError e) {
		}

		@Override
		public void onExecute(final String provider, final Integer status) {
			// If alert messages is enabled...
			if (enableAlertMessage) {
				// Then user receives alert messages about posting statuses
				if (status == 200 || status == 201 || status == 204) {

					// If custom alert messages is set
					if (completeMessage.length() == 0) {
						Toast.makeText(AtmFinderApplication.getContext(),
								DEFAULT_ALERT_COMPLETE_TEXT + provider,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(AtmFinderApplication.getContext(),
								completeMessage, Toast.LENGTH_LONG).show();
					}
				} else {
					// If custom alert messages is not set
					if (errorMessage.length() == 0) {
						Toast.makeText(AtmFinderApplication.getContext(),
								DEFAULT_ALERT_ERROR_TEXT + provider,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(AtmFinderApplication.getContext(),
								errorMessage, Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}
}
