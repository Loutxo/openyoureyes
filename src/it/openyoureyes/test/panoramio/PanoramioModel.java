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

package it.openyoureyes.test.panoramio;

import it.openyoureyes.R;
import it.openyoureyes.business.AbstractContentProviderOfGeoItems;
import it.openyoureyes.iface.GeoItem;

import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class PanoramioModel extends AbstractContentProviderOfGeoItems {

	List<GeoItem> lista;

	@Override
	public List<GeoItem> getGeoItems() {
		return lista;
	}

	@Override
	public void run() {
		Resources res = context.getResources();
		Drawable myImage = res.getDrawable(R.drawable.test);
		Panoramio pp = new Panoramio();
		Intent iintent = new Intent(context, PanoramioActivity.class);
		lista = pp.examinePanoramio(current, distance, myImage, iintent);

		notifyUpdate();

	}

}
