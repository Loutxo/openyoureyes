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
package it.openyoureyes.iface;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Represent the common features of poi items.
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
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
