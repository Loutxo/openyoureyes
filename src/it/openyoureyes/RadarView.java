package it.openyoureyes;

import it.openyoureyes.R;
import it.openyoureyes.business.Controller;
import it.openyoureyes.iface.RadarItem;
import it.openyoureyes.test.another.TestBean;
import it.openyoureyes.util.Util;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * This view manage the radar drawn on the screen that represent the poi
 * geographic position.
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class RadarView extends View {

	private List<? extends RadarItem> listOfGeoItems;
	/** The radar's range */
	// float range;
	/** Radius in pixel on screen */
	public static final float RADIUS = 40;
	/** Position on screen */
	private static float originX = 0, originY = 0;
	/** Color */
	private static int radarColor = Color.argb(100, 0, 0, 200);

	public static RadarItem NORTH = new TestBean(0, 0, false);
	private Drawable northImage;

	public RadarView(Context context, AttributeSet set) {
		super(context, set);
		northImage = context.getResources().getDrawable(R.drawable.nord);
	}

	/**
	 * The x and y coordinates of the into the radar are managed from the
	 * {@link Controller}.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/** radius is in KM. */

		Paint paint = new Paint();
		paint.setColor(radarColor);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(originX + RADIUS, originY + RADIUS, RADIUS, paint);

		paint.setStyle(Paint.Style.FILL);
		if (!Util.isEmpty(listOfGeoItems)
				&& Controller.CURRENT_LOCATION != null)
			for (RadarItem ii : listOfGeoItems) {
				paint.setColor(ii.getRadarItemColor());
				int y = ii.getRadarY();
				int x = ii.getRadarX();
				if (x == 0 && y == 0)
					continue;
				canvas.drawRect(RADIUS - x - 1, RADIUS - y - 1, RADIUS - x + 1,
						RADIUS - y + 1, paint);
			}
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.TRANSPARENT);
		if (NORTH != null) {
			int widthNorth = northImage.getMinimumWidth();
			int heightNorth = northImage.getMinimumHeight();
			int y = NORTH.getRadarY();
			int x = NORTH.getRadarX();
			northImage.setBounds(new Rect((int) (RADIUS - x - widthNorth / 2),
					(int) (RADIUS - y - heightNorth / 2),
					(int) (RADIUS - x + widthNorth / 2),
					(int) (RADIUS - y + heightNorth / 2)));
			northImage.draw(canvas);
		}

		paint.setColor(Color.WHITE);
		canvas.drawCircle(originX + RADIUS, originY + RADIUS, 1, paint);

	}

	/**
	 * Refresh method for the radar.
	 * 
	 * @param list
	 *            The list of {@link RadarItem}.
	 */
	public void drawPoi(List<? extends RadarItem> list) {
		listOfGeoItems = list;
		invalidate();
	}
}
