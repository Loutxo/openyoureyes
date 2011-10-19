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
import it.openyoureyes.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class PanoramioActivity extends Activity {

	private Handler h = new Handler();
	private ProgressDialog m_ProgressDialog;
	private ImageView im;
	private BitmapDrawable bitmDraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panoramio_layout);
		m_ProgressDialog = ProgressDialog.show(PanoramioActivity.this,
				"Attendere...", "Recupero dati...", true);

		if (getIntent().getExtras().containsKey("id")) {
			Thread tt = new Thread(new Runnable() {

				@Override
				public void run() {
					String id;
					id = getIntent().getExtras().getString("id");
					String url = "http://static2.bareka.com/photos/medium/"
							+ id + ".jpg";
					Bitmap bitm = downloadFile(url);
					bitmDraw = null;
					if (bitm != null)
						bitmDraw = new BitmapDrawable(bitm);
					im = (ImageView) findViewById(R.id.image_view_panoramio);
					h.post(new Runnable() {

						@Override
						public void run() {
							if (bitmDraw != null)
								im.setBackgroundDrawable(bitmDraw);
							m_ProgressDialog.dismiss();

						}
					});

				}
			});
			tt.start();
		}

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
			HttpURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			// BitmapFactory.Options op=new BitmapFactory.Options();
			// op.inSampleSize = 3;
			bmImg = BitmapFactory.decodeStream(is);// ,null,null);

		} catch (IOException e) {

			e.printStackTrace();
		}
		return bmImg;
	}

}
