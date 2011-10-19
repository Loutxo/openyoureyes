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

import it.openyoureyes.business.AbstractGeoItem;
import it.openyoureyes.iface.GeoItem;
import it.openyoureyes.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class Panoramio {

	public List<GeoItem> examinePanoramio(Location current, double distance,
			Drawable myImage, Intent intent) {
		ArrayList<GeoItem> result = new ArrayList<GeoItem>();

		/*
		 * var requiero_fotos = new Json.Remote(
		 * "http://www.panoramio.com/map/get_panoramas.php?order=popularity&
		 * set=
		 * full&from=0&to=10&minx=-5.8&miny=42.59&maxx=-5.5&maxy=42.65&size=
		 * medium "
		 */
		Location loc1 = new Location("test");
		Location loc2 = new Location("test_");
		AbstractGeoItem.calcDestination(current.getLatitude(),
				current.getLongitude(), 225, distance / 2, loc1);
		AbstractGeoItem.calcDestination(current.getLatitude(),
				current.getLongitude(), 45, distance / 2, loc2);
		try {
			URL url = new URL(
					"http://www.panoramio.com/map/get_panoramas.php?order=popularity&set=full&from=0&to=10&minx="
							+ loc1.getLongitude()
							+ "&miny="
							+ loc1.getLatitude()
							+ "&maxx="
							+ loc2.getLongitude()
							+ "&maxy="
							+ loc2.getLatitude() + "&size=thumbnail");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();

			String line;
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuffer buf = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buf.append(line + " ");
			}
			reader.close();
			is.close();
			conn.disconnect();
			// while (is.read(buffer) != -1);
			String jsontext = buf.toString();
			Log.d("Json Panoramio", jsontext);
			JSONObject entrie = new JSONObject(jsontext);
			JSONArray arr = entrie.getJSONArray("photos");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject panoramioObj = arr.getJSONObject(i);
				double longitude = panoramioObj.getDouble("longitude");
				double latitude = panoramioObj.getDouble("latitude");
				String urlFoto = panoramioObj.getString("photo_file_url");
				String idFoto = panoramioObj.getString("photo_id");
				Bundle bu = intent.getExtras();
				if (bu == null)
					bu = new Bundle();
				bu.putString("id", idFoto);
				intent.replaceExtras(bu);
				BitmapDrawable bb = new BitmapDrawable(downloadFile(urlFoto));
				PanoramioItem item = new PanoramioItem(latitude, longitude,
						false, bb, intent);
				result.add(item);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;

	}

	private Bitmap downloadFile(String url) {
		Bitmap bmImg = null;
		URL myFileUrl = null;
		if (Util.isEmpty(url))
			return null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			// BitmapFactory.Options op=new BitmapFactory.Options();
			// op.inSampleSize = 3;
			bmImg = BitmapFactory.decodeStream(is);// ,null,op);
			conn.disconnect();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return bmImg;
	}

}
