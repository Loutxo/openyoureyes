/*
 *  openyoureyes - Augmented Reality for android
 *  Copyright (C) 2011 Pasquale Paola
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  openyoureyes  Copyright (C) 2011  Pasquale Paola
 *  This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 *  This is free software, and you are welcome to redistribute it
 *  under certain conditions; type `show c' for details.
 *  
 *  Contact info: pasquale.paola@gmail.com
 */

package it.openyoureyes.business;

import it.openyoureyes.iface.GeoItem;
import android.graphics.Color;
import android.location.Location;

/**
 * Abstract implementation of {@link GeoItem}, contains the common features useful to standard implementation.
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public abstract class AbstractGeoItem implements GeoItem {
	
	protected double altitude;
	protected double distanceFromIt;
	protected int radarX;
	protected int radarY;
	protected int radarItemColor;
	

	private double radLat; // latitude in radians
	private double radLon; // longitude in radians

	private double degLat; // latitude in degrees
	private double degLon; // longitude in degrees

	private static final int TWO_MINUTES = 1000 * 60 * 2;
	public static final double NO_ALTITUDE=-2000;
	private static final long serialVersionUID = 1090283314286486981L;
	private static final double MIN_LAT = Math.toRadians(-90d); // -PI/2
	private static final double MAX_LAT = Math.toRadians(90d); // PI/2
	private static final double MIN_LON = Math.toRadians(-180d); // -PI
	private static final double MAX_LON = Math.toRadians(180d); // PI

	/**
	 * Default initialization.
	 */
	private AbstractGeoItem() {
		this.radarItemColor = Color.GREEN;
		this.altitude = NO_ALTITUDE;
	}

	/**
	 * Build geoitem from latitude and longitude.
	 * @param latitude Latitude value.
	 * @param longitude Longitude value.
	 * @param isRadians Specify if the latitude and longitude have a radians value.
	 */
	public AbstractGeoItem(double latitude, double longitude, boolean isRadians) {
		this();
		if (isRadians)
			fromRadians(latitude, longitude, this);
		else
			fromDegrees(latitude, longitude, this);
	}

	/**
	 * Build a new geoitem like a clone.
	 * @param clone Clone geoitem.
	 * @param isRadians Specify if the latitude and longitude have a radians value.
	 */
	public AbstractGeoItem(GeoItem clone, boolean isRadians) {
		this(clone.getLatitude(), clone.getLongitude(), isRadians);
	}

	/**
	 * Initialize an active istance of AbstracGeoItem in degree mode.
	 * @param latitude
	 *            the latitude, in degrees.
	 * @param longitude
	 *            the longitude, in degrees.
	 * @param result
	 *            istanced geolocation
	 */
	public static void fromDegrees(double latitude, double longitude,
			AbstractGeoItem result) {

		result.radLat = Math.toRadians(latitude);
		result.radLon = Math.toRadians(longitude);
		result.degLat = latitude;
		result.degLon = longitude;
		result.checkBounds();

	}

	/**
	 * Initialize an active istance of AbstracGeoItem in radian mode.
	 * @param latitude
	 *            the latitude, in radians.
	 * @param longitude
	 *            the longitude, in radians.
	 * @param result
	 *            istanced geolocation
	 */
	public static void fromRadians(double latitude, double longitude,
			AbstractGeoItem result) {

		result.radLat = latitude;
		result.radLon = longitude;
		result.degLat = Math.toDegrees(latitude);
		result.degLon = Math.toDegrees(longitude);
		result.checkBounds();

	}

	@Deprecated
	private void checkBounds() {
		if (radLat < MIN_LAT || radLat > MAX_LAT || radLon < MIN_LON
				|| radLon > MAX_LON)
			throw new IllegalArgumentException();
	}

	/**
	 * @return the latitude, in degrees.
	 */
	public double getLatitudeInDegrees() {
		return degLat;
	}

	/**
	 * @return the longitude, in degrees.
	 */
	public double getLongitudeInDegrees() {
		return degLon;
	}

	/**
	 * @return the latitude, in radians.
	 */
	public double getLatitudeInRadians() {
		return radLat;
	}

	/**
	 * @return the longitude, in radians.
	 */
	public double getLongitudeInRadians() {
		return radLon;
	}

	@Override
	public String toString() {
		return "(" + degLat + "\u00B0, " + degLon + "\u00B0) = (" + radLat
				+ " rad, " + radLon + " rad)";
	}

	/**
	 * 
	 * Computes the great circle distance between this AbstractGeoItem instance
	 * and the location argument.
	 * 
	 * @param radius
	 *            the radius of the sphere, e.g. the average radius for a
	 *            spherical approximation of the figure of the Earth is
	 *            approximately 6371.01 kilometers.
	 * @return the distance, measured in the same unit as the radius argument.
	 * @deprecated Use {@link #distanceBetween}.
	 */
	@Deprecated
	public double distanceTo(AbstractGeoItem location, double radius) {
		return Math.acos(Math.sin(radLat) * Math.sin(location.radLat)
				+ Math.cos(radLat) * Math.cos(location.radLat)
				* Math.cos(radLon - location.radLon))
				* radius;
	}

	/**
	 * <p>
	 * Computes the bounding coordinates of all points on the surface of a
	 * sphere that have a great circle distance to the point represented by this
	 * AbstractGeoItem instance that is less or equal to the distance argument.
	 * </p>
	 * 
	 * @param distance
	 *            the distance from the point represented by this
	 *            AbstractGeoItem instance. Must me measured in the same unit as
	 *            the radius argument.
	 * @param radius
	 *            the radius of the sphere, e.g. the average radius for a
	 *            spherical approximation of the figure of the Earth is
	 *            approximately 6371.01 kilometers.
	 * @param result
	 *            an array of two AbstractGeoItem objects such that:
	 *            <ul>
	 *            <li>The latitude of any point within the specified distance is
	 *            greater or equal to the latitude of the first array element
	 *            and smaller or equal to the latitude of the second array
	 *            element.</li>
	 *            <li>If the longitude of the first array element is smaller or
	 *            equal to the longitude of the second element, then the
	 *            longitude of any point within the specified distance is
	 *            greater or equal to the longitude of the first array element
	 *            and smaller or equal to the longitude of the second array
	 *            element.</li>
	 *            <li>If the longitude of the first array element is greater
	 *            than the longitude of the second element (this is the case if
	 *            the 180th meridian is within the distance), then the longitude
	 *            of any point within the specified distance is greater or equal
	 *            to the longitude of the first array element
	 *            <strong>or</strong> smaller or equal to the longitude of the
	 *            second array element.</li>
	 *            </ul>
	 */
	@Deprecated
	public void boundingCoordinates(double distance, double radius,
			AbstractGeoItem[] result) {

		if (radius < 0d || distance < 0d)
			throw new IllegalArgumentException();

		// angular distance in radians on a great circle
		double radDist = distance / radius;

		double minLat = radLat - radDist;
		double maxLat = radLat + radDist;

		double minLon, maxLon;
		if (minLat > MIN_LAT && maxLat < MAX_LAT) {
			double deltaLon = Math.asin(Math.sin(radDist) / Math.cos(radLat));
			minLon = radLon - deltaLon;
			if (minLon < MIN_LON)
				minLon += 2d * Math.PI;
			maxLon = radLon + deltaLon;
			if (maxLon > MAX_LON)
				maxLon -= 2d * Math.PI;
		} else {
			// a pole is within the distance
			minLat = Math.max(minLat, MIN_LAT);
			maxLat = Math.min(maxLat, MAX_LAT);
			minLon = MIN_LON;
			maxLon = MAX_LON;
		}

		fromRadians(minLat, minLon, result[0]);
		fromRadians(maxLat, maxLon, result[1]);
		/*
		 * return new AbstractGeoItem[]{, };
		 */
	}

	

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	public static boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	/**
	 * Calculate the destination geopoint. 
	 * @param lat1Deg Current latitude.
	 * @param lon1Deg Current longitude.
	 * @param bear The bearing value.
	 * @param d The distance between in meters.
	 * @param dest The destination found.
	 * @see <a href="http://en.wikipedia.org/wiki/Great-circle_distance">http://en.wikipedia.org/wiki/Great-circle_distance</a>
	 */
	public static void calcDestination(double lat1Deg, double lon1Deg,
			double bear, double d, Location dest) {
		
		double brng = Math.toRadians(bear);
		double lat1 = Math.toRadians(lat1Deg);
		double lon1 = Math.toRadians(lon1Deg);
		double R = 6371.0 * 1000.0;

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R)
				+ Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));

		double lon2 = lon1
				+ Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1),
						Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

		dest.setLatitude(Math.toDegrees(lat2));
		dest.setLongitude(Math.toDegrees(lon2));
	}
	
	/**
	 * @deprecated Use {@link #getRhumbLineBearing}.
	 * Calculate bearing between two geo points.
	 * @param aLat
	 * @param aLong
	 * @param bLat
	 * @param bLong
	 * @return The bearing value.
	 */

	@Deprecated 
	public static double getBearing(double aLat, double aLong, double bLat,
			double bLong) {
		double y = Math.sin(bLong - aLong) * Math.cos(bLat);
		double x = Math.cos(aLat) * Math.sin(bLat) - Math.sin(aLat)
				* Math.cos(bLat) * Math.cos(bLong - aLong);
		double brng = Math.toDegrees(Math.atan2(y, x));
		return brng;
	}

	/**
	 * Calculate the bearing between two geopints.
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return The bearing value.
	 */
	public static double getRhumbLineBearing(double lat1, double lon1,
			double lat2, double lon2) {
		// difference in longitudinal coordinates
		double dLon = Math.toRadians(lon2) - Math.toRadians(lon1);

		// difference in the phi of latitudinal coordinates
		double dPhi = Math.log(Math.tan(Math.toRadians(lat2) / 2 + Math.PI / 4)
				/ Math.tan(Math.toRadians(lat1) / 2 + Math.PI / 4));

		// we need to recalculate $dLon if it is greater than pi
		if (Math.abs(dLon) > Math.PI) {
			if (dLon > 0) {
				dLon = (2 * Math.PI - dLon) * -1;
			} else {
				dLon = 2 * Math.PI + dLon;
			}
		}
		// return the angle, normalized

		return (Math.toDegrees(Math.atan2(dLon, dPhi)) + 360) % 360;
	}

	/**
	 * Calculate the compass direction.
	 * @param bearing
	 * @return A string rappresentation of the compass direction.
	 */
	public static String getCompassDirection(double bearing) {
		int tmp = (int) Math.round(bearing / 22.5);
		String direction = "";
		switch (tmp) {
		case 1:
			direction = "NNE";
			break;
		case 2:
			direction = "NE";
			break;
		case 3:
			direction = "ENE";
			break;
		case 4:
			direction = "E";
			break;
		case 5:
			direction = "ESE";
			break;
		case 6:
			direction = "SE";
			break;
		case 7:
			direction = "SSE";
			break;
		case 8:
			direction = "S";
			break;
		case 9:
			direction = "SSW";
			break;
		case 10:
			direction = "SW";
			break;
		case 11:
			direction = "WSW";
			break;
		case 12:
			direction = "W";
			break;
		case 13:
			direction = "WNW";
			break;
		case 14:
			direction = "NW";
			break;
		case 15:
			direction = "NNW";
			break;
		default:
			direction = "N";
		}
		return direction;
	}

	@Override
	public double getLatitude() {
		return getLatitudeInDegrees();
	}

	@Override
	public double getLongitude() {

		return getLongitudeInDegrees();
	}

	/**
	 * Calculate distance between two points, using google api.
	 * @param lat1 First point latitude.
	 * @param lon1 First point longitude.
	 * @param lat2 Second point latitude.
	 * @param lon2 Second point longitude.
	 * @return Distance in meters.
	 */
	public static float distanceBetween(double lat1, double lon1, double lat2,
			double lon2) {
		/*
		 * double R = 6371; // km double d =
		 * Math.acos(Math.sin(lat1)*Math.sin(lat2) +
		 * Math.cos(lat1)*Math.cos(lat2) * Math.cos(lon2-lon1)) * R; return d;
		 */
		Location loc1 = new Location("loc1");
		loc1.setLatitude(lat1);
		loc1.setLongitude(lon1);
		Location loc2 = new Location("loc2");
		loc2.setLatitude(lat2);
		loc2.setLongitude(lon2);
		float dist = loc1.distanceTo(loc2);
		return dist;
	}

	@Override
	public double getAltitude() {

		return altitude;
	}

	@Override
	public double getDistanceFromIt() {

		return distanceFromIt;
	}

	@Override
	public void setDistanceFromIt(double z) {
		this.distanceFromIt = z;

	}

	@Override
	public int getRadarX() {

		return radarX;
	}

	@Override
	public int getRadarY() {

		return radarY;
	}

	@Override
	public void setRadarX(int x) {
		this.radarX = x;

	}

	@Override
	public void setRadarY(int y) {
		this.radarY = y;

	}

	@Override
	public int getRadarItemColor() {

		return radarItemColor;
	}

	@Override
	public void setRadarItemColor(int color) {
		this.radarItemColor = color;

	}

}