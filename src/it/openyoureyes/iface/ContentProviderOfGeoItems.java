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

import java.util.List;

import android.content.Context;
import android.location.Location;

/**
 * Represent the concept provider of GeoItem.
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
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
