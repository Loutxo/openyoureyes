package it.openyoureyes.iface;

/**
 * This interface represent the point drawn on radar circle.
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public interface RadarItem {
	
	/**
	 * 
	 * @return The x coordinate on the real screen.
	 */
	public int getRadarX();

	/**
	 * 
	 * @return The y coordinate on the real screen.
	 */
	public int getRadarY();

	/**
	 * 
	 * @param x The x coordinate on the real screen.
	 */
	public void setRadarX(int x);

	/**
	 * 
	 * @param y The y coordinate on the real screen.
	 */
	public void setRadarY(int y);

	/**
	 * 
	 * @return The item color drawn on the screen.
	 */
	public int getRadarItemColor();

	/**
	 * 
	 * @param color The item color drawn on the screen.
	 */
	public void setRadarItemColor(int color);
}
