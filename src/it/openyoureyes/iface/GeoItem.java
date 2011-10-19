package it.openyoureyes.iface;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Represent the common features of poi items.
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public interface GeoItem extends Serializable, RadarItem {

	/**
	 * Return latitude in degrees.
	 * 
	 * @return
	 */
	public double getLatitude();

	/**
	 * Return longitude in degrees.
	 * 
	 * @return
	 */
	public double getLongitude();

	/**
	 * Return latitude in meters.
	 * 
	 * @return
	 */
	public double getAltitude();

	/**
	 * Represent the {@link Intent} associated with the touch.
	 * 
	 * @return
	 */
	public Intent getActionIntent();

	/**
	 * The icon drwan on the screen.
	 * 
	 * @return
	 */
	public Drawable getIcon();
	
	/**
	 * To manage th rotation.
	 * @param img
	 */
	public void setIcon(Drawable img);

	/**
	 * The label drawn on the screen.
	 * 
	 * @return
	 */
	public String getLabel();

	/**
	 * Getter method for the real distance between the current position and this
	 * poi.
	 * 
	 * @return
	 */
	public double getDistanceFromIt();

	/**
	 * Setter method for the real distance between the current position and this
	 * poi.
	 * 
	 * @param z
	 */
	public void setDistanceFromIt(double z);

}
