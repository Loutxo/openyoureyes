package it.openyoureyes.util;

import java.util.Collection;

/**
 * Static utility object.
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
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
