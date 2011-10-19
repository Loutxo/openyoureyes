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

import it.openyoureyes.R;
import it.openyoureyes.business.AbstractContentProviderOfGeoItems;
import it.openyoureyes.iface.GeoItem;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class OpenCellIdList extends AbstractContentProviderOfGeoItems {

	private List<GeoItem> lista;

	public OpenCellIdList() {

	}

	@Override
	public List<GeoItem> getGeoItems() {
		// TODO Auto-generated method stub

		return lista;
	}

	@Override
	public void run() {
		OpenCellId op = new OpenCellId();
		try {
			if (current != null)
				op.getCellId(context, current.getLatitude(),
						current.getLongitude(), distance, new MyHandler());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyHandler extends DefaultHandler {
		private List<GeoItem> temp = new ArrayList<GeoItem>();
		Resources res = context.getResources();
		Drawable myImage = res.getDrawable(R.drawable.test);

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			// <cell mnc="10" lac="45025" lat="40.781042778125" nbSamples="96"
			// lon="14.3801897239583" cellId="2754404" mcc="222"/>
			if ("cell".equals(localName)) {
				String lat = attributes.getValue("lat");
				String lon = attributes.getValue("lon");
				temp.add(new OpenCellIdBean(Double.parseDouble(lat), Double
						.parseDouble(lat), myImage));

			}
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			lista = temp;
			notifyUpdate();
		}
	}
}
