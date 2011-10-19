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
package it.openyoureyes.test;

import it.openyoureyes.business.AbstractGeoItem;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class OpenCellIdBean extends AbstractGeoItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4930009197632381337L;
	private double lat;
	private double lon;
	private Drawable icon;

	public OpenCellIdBean(double lat, double lon, Drawable dd) {
		super(lat, lon, false);
		this.lat = lat;
		this.lon = lon;
		this.icon = dd;
	}

	@Override
	public double getLatitude() {
		// TODO Auto-generated method stub
		return lat;
	}

	@Override
	public double getLongitude() {
		// TODO Auto-generated method stub
		return lon;
	}

	@Override
	public Intent getActionIntent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getIcon() {

		return icon;
	}

	@Override
	public double getDistanceFromIt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAltitude() {
		// TODO Auto-generated method stub
		return -1;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	@Override
	public String getLabel() {
		return "Test label";
	}

	@Override
	public void setDistanceFromIt(double z) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRadarX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRadarY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRadarX(int x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRadarY(int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRadarItemColor() {
		// TODO Auto-generated method stub
		return Color.RED;
	}

	@Override
	public void setRadarItemColor(int color) {
		// TODO Auto-generated method stub

	}

}
