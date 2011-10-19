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

/**
 * This interface represent the point drawn on radar circle.
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public interface RadarItem {
	
	/**
	 * 
	 * @return The x coordinate on the real screen.
	 */
	public int getRadarX();

	/**
	 * 
	 * @return The y coordinate on the real screen.
	 */
	public int getRadarY();

	/**
	 * 
	 * @param x The x coordinate on the real screen.
	 */
	public void setRadarX(int x);

	/**
	 * 
	 * @param y The y coordinate on the real screen.
	 */
	public void setRadarY(int y);

	/**
	 * 
	 * @return The item color drawn on the screen.
	 */
	public int getRadarItemColor();

	/**
	 * 
	 * @param color The item color drawn on the screen.
	 */
	public void setRadarItemColor(int color);
}
