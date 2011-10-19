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
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
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
