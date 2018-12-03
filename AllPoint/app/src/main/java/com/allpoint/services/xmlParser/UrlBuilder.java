package com.allpoint.services.xmlParser;

import com.allpoint.util.Constant;
import com.google.android.gms.maps.model.LatLng;

/**
 * UrlBuilder
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class UrlBuilder {
	public static String getSearchUrl(String addressLine, String type) {
		if (addressLine == null) {
			addressLine = "";
		}

		return Constant.LOCATION_SEARCH_URL
				+ "UserName="
				+ Constant.API_USERNAME
				+ "&Password="
				+ Constant.API_PASSWORD
				+ "&AddressLine="
				+ addressLine
				+ "&City=&State=&PostalCode=&Country=&Latitude=&Longitude=&Type="
				+ type + "&Offset=";
	}

	public static String getSearchUrl(final LatLng position, String type) {
		String latitude = "";
		String longitude = "";
		if (position != null) {
			latitude = String.valueOf(position.latitude);
			longitude = String.valueOf(position.longitude);
		}

		if (type == null) {
			type = "";
		}

		return Constant.LOCATION_SEARCH_URL + "UserName="
				+ Constant.API_USERNAME + "&Password=" + Constant.API_PASSWORD
				+ "&AddressLine=&City=&State=&PostalCode=&Country=&Latitude="
				+ latitude + "&Longitude=" + longitude + "&Type=" + type
				+ "&Offset=";
	}

	public static String getFiltersUrl() {
		return Constant.FILTERS_SEARCH_URL + "UserName="
				+ Constant.API_USERNAME + "&Password=" + Constant.API_PASSWORD;
	}

	public static String getVersionInfoUrl(String deviceType) {
		if (deviceType == null) {
			deviceType = "2";
		}
		return Constant.VERSION_INFO_URL + "UserName=" + Constant.API_USERNAME
				+ "&Password=" + Constant.API_PASSWORD + "&AppType="
				+ deviceType;
	}
}
