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

package it.openyoureyes.test.another;

import it.openyoureyes.business.AbstractGeoItem;
import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class TestBean extends AbstractGeoItem {

	private Drawable dd;
	private String ciccio;
	private Intent intent;
	private int color;
	private int radarX;
	private int radarY;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6613392277532273656L;

	public TestBean(double latitude, double longitude, boolean isRadians) {
		super(latitude, longitude, isRadians);

	}

	public TestBean(double latitude, double longitude, boolean isRadians,
			String label, Drawable pippo) {
		super(latitude, longitude, isRadians);
		this.ciccio = label;
		this.dd = pippo;

	}

	public TestBean(double latitude, double longitude, boolean isRadians,
			String label, Drawable pippo, Intent ii, int color) {
		super(latitude, longitude, isRadians);
		this.ciccio = label;
		this.dd = pippo;
		this.intent = ii;
		this.color = color;

	}

	@Override
	public Intent getActionIntent() {
		// TODO Auto-generated method stub
		return intent;
	}

	@Override
	public Drawable getIcon() {
		// TODO Auto-generated method stub
		return dd;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return ciccio;
	}

	@Override
	public double getAltitude() {
		// TODO Auto-generated method stub
		return AbstractGeoItem.NO_ALTITUDE;
	}

	@Override
	public double getDistanceFromIt() {
		// TODO Auto-generated method stub
		return distanceFromIt;
	}

	public void setDistanceFromIt(double z) {
		this.distanceFromIt = z;
	}

	@Override
	public int getRadarX() {
		// TODO Auto-generated method stub
		return radarX;
	}

	@Override
	public int getRadarY() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setRadarItemColor(int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIcon(Drawable img) {
		dd=img;
		
	}

}