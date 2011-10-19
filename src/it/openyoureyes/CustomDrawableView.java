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
package it.openyoureyes;

import it.openyoureyes.iface.GeoItem;
import it.openyoureyes.util.Util;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * This view draw the poi on the screen and manage the touch event;
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class CustomDrawableView extends View implements View.OnTouchListener {
	private List<GeoItem> listOfGeoItems;
	private Context context;
	private float currentRoll = 0.0f;

	public CustomDrawableView(Context context, AttributeSet set) {
		super(context, set);
		this.context = context;
		this.setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Matrix m = canvas.getMatrix();
		// m.preRotate(currentRoll);
		// Matrix m=new Matrix();
		m.setRotate(currentRoll, getWidth() / 2, getHeight() / 2);
		canvas.setMatrix(m);

		if (!Util.isEmpty(listOfGeoItems)) {

			for (GeoItem p : listOfGeoItems) {
				// Bitmap bitmap = ((BitmapDrawable)p.getIcon()).getBitmap();
				// canvas.drawBitmap(bitmap, m,null);
				p.getIcon().draw(canvas);
			}
		}

	}

	/**
	 * Refresh the poi list.
	 * 
	 * @param poi
	 */
	void drawPOI(List<GeoItem> poi, float roll) {
		this.listOfGeoItems = poi;
		this.currentRoll = roll;
		invalidate();

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		Rect rr = new Rect((int) x - 2, (int) y - 2, (int) x + 2, (int) y + 2);
		for (GeoItem ii : listOfGeoItems) {

			if (ii.getIcon().getBounds().intersect(rr)) {
				if (ii.getActionIntent() != null)
					context.startActivity(ii.getActionIntent());
				return true;
			}
		}

		return false;
	}

}
