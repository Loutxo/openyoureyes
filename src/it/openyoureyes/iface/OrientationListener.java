package it.openyoureyes.iface;

/**
 * This is the interface listner used by orientation provider.
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public interface OrientationListener {
	/**
	 * This the method called by the orientation listner to notify the changement.
	 * @param azimuth Angular measurement in a spherical coordinate system with North.
	 * @param pitch Rotation about the lateral axis passes through the plane from wingtip to wingtip.
	 * @param roll Rotation about the longitudinal axis passes through the plane from nose to tail.
	 */
	public void onOrientationChanged(float azimuth, float pitch, float roll);

}
