package com.allpoint.util;

import android.location.Location;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.CustomRichPushMessage;
import com.google.android.gms.maps.model.LatLng;
import com.urbanairship.richpush.RichPushMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Compute {

	public static float distanceBetweenPoints(final LatLng from, final LatLng to) {
		float[] result = new float[4];
		Location.distanceBetween(from.latitude, from.longitude, to.latitude,
				to.longitude, result);

		return result[0];
	}

	public static float zoomLevel(double distance, final boolean isMiles) {
		float zoom;
		double E = 40075;
		if (isMiles) {
			E = 21638.8;
		}
		distance = distance * 2;
		int zoomIncrement = AtmFinderApplication.getContext().getResources()
				.getInteger(R.integer.zoomIncrement);

		zoom = (float) ((Math.log(E / distance) / Math.log(2)) + zoomIncrement);

		if (zoom > 21) {
			zoom = 21;
		} else if (zoom < 1) {
			zoom = 1;
		}
		return zoom;
	}

	public static double round(final double value, final int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bigDecimal = new BigDecimal(value);
		bigDecimal = bigDecimal.setScale(places, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	public static boolean isEqualsLatLng(final LatLng firstLatLng,
			final LatLng secondLatLng, final int places) {
		double firstLatitude = round(firstLatLng.latitude, places);
		double firstLongitude = round(firstLatLng.longitude, places);

		double secondLatitude = round(secondLatLng.latitude, places);
		double secondLongitude = round(secondLatLng.longitude, places);

		LatLng firstPosition = new LatLng(firstLatitude, firstLongitude);
		LatLng secondPosition = new LatLng(secondLatitude, secondLongitude);

		return firstPosition.equals(secondPosition);
	}

	/**
	 * Converts list of RichPush-messages to list of CustomRichPush-messages
	 * 
	 * @param richPushMessages
	 *            Initial list of RichPush-messages
	 * @return Returns list of CustomRichPush-messages, which has been converted
	 *         from list of RichPush-messages
	 */
	public static List<CustomRichPushMessage> convertRichPushToCustom(
			final List<RichPushMessage> richPushMessages) {
		List<CustomRichPushMessage> messageList = new ArrayList<CustomRichPushMessage>();

		for (RichPushMessage rp : richPushMessages) {
			messageList.add(new CustomRichPushMessage(rp));
		}

		return messageList;
	}

	/**
	 * Compares list of RichPush-messages with list of CustomRichPush-messages
	 * 
	 * @param richPushList
	 *            List of RichPush-messages
	 * @param customRichPushList
	 *            List of CustomRichPush-messages
	 * @return Returns result of the comparsion
	 */
	public static boolean isEqualsRichPushes(
			final List<RichPushMessage> richPushList,
			final List<CustomRichPushMessage> customRichPushList) {
		// If size of lists are equals, then start to compare
		if (richPushList != null && customRichPushList != null) {
			if (richPushList.size() == customRichPushList.size()) {
				for (int i = 0; i < richPushList.size(); i++) {
					if (!richPushList.get(i).getMessageId()
							.equals(customRichPushList.get(i).getMessageId())) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
