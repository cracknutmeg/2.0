/**
 * @ PointRecord
 */
package com.allpoint.model;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.util.IconManager;
import com.allpoint.util.Utils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * PointRecord
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class PointRecord implements Comparable<PointRecord> {

    private final List<String> services = new ArrayList<String>();
    private final List<String> hours = new ArrayList<String>();

    private final IconManager iconManager;

    private LatLng startSearchPosition;
    private LatLng position;

    private boolean isSelected = false;
    private long locationId;
    private float distance;

    private String distanceUnit;
    private String name;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobileValue;
    private String eMail;
    private String logoName;
    private String pinName;

    public PointRecord setLogoName(final String logoName) {
        this.logoName = logoName;
        return this;
    }

    public PointRecord setPinName(final String pinName) {
        this.pinName = pinName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PointRecord))
            return false;

        PointRecord that = (PointRecord) o;

        if (Float.compare(that.distance, distance) != 0)
            return false;
        if (locationId != that.locationId)
            return false;
        if (addressLine != null ? !addressLine.equals(that.addressLine)
                : that.addressLine != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null)
            return false;
        if (country != null ? !country.equals(that.country)
                : that.country != null)
            return false;
        if (distanceUnit != null ? !distanceUnit.equals(that.distanceUnit)
                : that.distanceUnit != null)
            return false;
        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null)
            return false;
        if (hours != null ? !hours.equals(that.hours) : that.hours != null)
            return false;
        if (logoName != null ? !logoName.equals(that.logoName)
                : that.logoName != null)
            return false;
        if (mobileValue != null ? !mobileValue.equals(that.mobileValue)
                : that.mobileValue != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (pinName != null ? !pinName.equals(that.pinName)
                : that.pinName != null)
            return false;
        if (position != null ? !position.equals(that.position)
                : that.position != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode)
                : that.postalCode != null)
            return false;
        if (services != null ? !services.equals(that.services)
                : that.services != null)
            return false;
        if (startSearchPosition != null ? !startSearchPosition
                .equals(that.startSearchPosition)
                : that.startSearchPosition != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = services != null ? services.hashCode() : 0;
        result = 31 * result + (hours != null ? hours.hashCode() : 0);
        result = 31
                * result
                + (startSearchPosition != null ? startSearchPosition.hashCode()
                : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (int) (locationId ^ (locationId >>> 32));
        result = 31 * result
                + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result
                + (distanceUnit != null ? distanceUnit.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result
                + (addressLine != null ? addressLine.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result
                + (mobileValue != null ? mobileValue.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (logoName != null ? logoName.hashCode() : 0);
        result = 31 * result + (pinName != null ? pinName.hashCode() : 0);
        return result;
    }

    public PointRecord() {
        iconManager = IconManager.getInstance(AtmFinderApplication.getContext());
    }

    public PointRecord(final int locationId) {
        this.locationId = locationId;
        iconManager = IconManager.getInstance(AtmFinderApplication.getContext());
    }

    public PointRecord setLocationId(final long newLocationId) {
        this.locationId = newLocationId;
        return this;
    }

    public PointRecord setPosition(final LatLng position) {
        this.position = position;
        return this;
    }

    public PointRecord setName(final String name) {
        this.name = name;
        return this;
    }

    public PointRecord setAddressLine(final String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public PointRecord setCity(final String city) {
        this.city = city;
        return this;
    }

    public PointRecord setState(final String state) {
        this.state = state;
        return this;
    }

    public PointRecord setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public PointRecord setCountry(final String country) {
        this.country = country;
        return this;
    }

    public PointRecord setMobileValue(final String mobileValue) {
        this.mobileValue = mobileValue;
        return this;
    }

    public PointRecord setEmail(final String eMail) {
        this.eMail = eMail;
        return this;
    }

    public PointRecord setDistanceUnit(final String distanceUnit) {
        this.distanceUnit = distanceUnit;
        return this;
    }

    public MarkerOptions toMarkerOptions() {
        Integer resourceId = null;

        try {
            resourceId = iconManager.getPinByName(this.getPinName());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        if (resourceId == null) {
            resourceId = R.drawable.mapicon;
        }

        return (new MarkerOptions().position(this.position).title(this.name)
                .snippet(Utils.getDistanceString(this.distance))
                .icon(BitmapDescriptorFactory.fromResource(resourceId)));
    }

    public PointRecord addService(final String service) {
        services.add(service);
        return this;
    }

    public PointRecord addHour(final String hour) {
        hours.add(hour);
        return this;
    }

    public PointRecord setDistance(final float distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public int compareTo(final PointRecord another) {
        if (this.getDistance() > another.getDistance()) {
            return 1;
        } else if (this.getDistance() < another.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }

    public PointRecord setStartSearchPosition(final LatLng startSearchPosition) {
        this.startSearchPosition = startSearchPosition;
        return this;
    }

    public String getLogoName() {
        return logoName;
    }

    public String getPinName() {
        return pinName;
    }

    public long getLocationId() {
        return locationId;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getMobileValue() {
        return mobileValue;
    }

    public String getEmail() {
        return eMail;
    }

    public List<String> getServices() {
        return services;
    }

    public List<String> getHours() {
        return hours;
    }

    public float getDistance() {
        return distance;
    }

    public LatLng getStartSearchPosition() {
        return startSearchPosition;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
