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
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
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
