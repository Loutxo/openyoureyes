package it.openyoureyes.test.panoramio;

import it.openyoureyes.business.AbstractGeoItem;
import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class PanoramioItem extends AbstractGeoItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -389946316817050428L;
	private Drawable icon;
	private Intent action;

	public PanoramioItem(double latitude, double longitude, boolean isRadians,
			Drawable myImage, Intent intent) {
		super(latitude, longitude, isRadians);
		icon = myImage;
		action = intent;
	}

	@Override
	public Intent getActionIntent() {
		// TODO Auto-generated method stub
		return action;
	}

	@Override
	public Drawable getIcon() {

		return icon;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Ciao";
	}

	@Override
	public void setIcon(Drawable img) {
		icon=img;
		
	}

}
