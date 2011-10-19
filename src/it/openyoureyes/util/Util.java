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

package it.openyoureyes.util;

import java.util.Collection;

/**
 * Static utility object.
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class Util {

	/**
	 * Verify if a string is empty.
	 * @param str String to verify.
	 * @return True if empty, false everything else.
	 */
	public static boolean isEmpty(String str) {
		boolean result = true;
		if (str == null)
			;
		else {
			str = str.trim();
			if (str.length() > 0)
				result = false;
		}

		return result;
	}

	/**
	 * 
	 * Verify if a Collection is empty.
	 * @param collection Collection to verify.
	 * @return True if empty, false everything else.
	 *
	 */
	public static boolean isEmpty(Collection<?> collection) {
		boolean result = true;
		if (collection == null)
			result = true;
		else if (collection.isEmpty())
			result = true;
		else
			result = false;

		return result;
	}

}
