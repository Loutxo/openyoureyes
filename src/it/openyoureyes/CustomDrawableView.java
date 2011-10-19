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
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class CustomDrawableView extends View implements View.OnTouchListener {
	private List<GeoItem> listOfGeoItems;
	private Context context;
	private float currentRoll=0.0f;

	public CustomDrawableView(Context context, AttributeSet set) {
		super(context, set);
		this.context = context;
		this.setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
		Matrix m=canvas.getMatrix();
		//m.preRotate(currentRoll);
		//Matrix m=new Matrix();
		m.setRotate(currentRoll, getWidth()/2, getHeight()/2);
		canvas.setMatrix(m);
			
		if (!Util.isEmpty(listOfGeoItems)) {

			for (GeoItem p : listOfGeoItems) {
				//Bitmap bitmap = ((BitmapDrawable)p.getIcon()).getBitmap();				
				//canvas.drawBitmap(bitmap, m,null);
				p.getIcon().draw(canvas);
			}
		}

	}

	/**
	 * Refresh the poi list.
	 * 
	 * @param poi
	 */
	void drawPOI(List<GeoItem> poi,float roll) {
		this.listOfGeoItems = poi;
		this.currentRoll=roll;
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
