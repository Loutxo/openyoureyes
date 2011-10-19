package it.openyoureyes.iface;

import java.util.List;

import android.content.Context;
import android.location.Location;

/**
 * Represent the concept provider of GeoItem.
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public interface ContentProviderOfGeoItems {

	/**
	 * Invoked from the controller class
	 * {@link it.openyoureyes.business.Controller} to initilize the provider.
	 */
	public void init();

	/**
	 * This is the list of the poi ready to draw on the screen. Called by the
	 * {@link it.openyoureyes.business.Controller}.
	 * 
	 * @return the geoitem list.
	 */
	public List<GeoItem> getGeoItems();

	/**
	 * Set max distance of the relevation, set from {@link it.openyoureyes.business.Controller}.
	 * 
	 * @param maxDistanze
	 *            Distance in meters.
	 */
	public void setMaxDistance(double maxDistanze);

	/**
	 * This the way how to {@link it.openyoureyes.business.Controller} notifies contentProvider the current
	 * location.
	 * 
	 * @param current
	 *            The current location based on gps and network provider.
	 */
	public void setCurrentLocation(Location current);

	/**
	 * Called by the {@link it.openyoureyes.business.Controller} before destruction of the resuource.
	 */
	public void dispose();

	/**
	 * Give to the contentProvider the ability to use the android {@link Context}.
	 * 
	 * @param context
	 *            The android context.
	 */
	public void setContext(Context context);
}
