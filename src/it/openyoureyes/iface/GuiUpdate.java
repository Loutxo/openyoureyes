package it.openyoureyes.iface;

import java.util.List;

import android.graphics.Matrix;

/**
 * This interface represents a handle refreshing gui of {@link RadarItem} and
 * {@link GeoItem}.
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public interface GuiUpdate {
	/**
	 * Request a refresh of gui.
	 * 
	 * @param seenItems
	 *            What you are looking now.
	 * @param allItems
	 *            Those are all items shown on radar.
	 *            
	 * @param roll Current rolling. 
	 */
	public void update(List<GeoItem> seenItems,
			List<? extends RadarItem> allItems, float roll);
}
