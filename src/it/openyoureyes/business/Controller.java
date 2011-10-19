package it.openyoureyes.business;

import it.openyoureyes.R;
import it.openyoureyes.PreferenceActivity;
import it.openyoureyes.RadarView;
import it.openyoureyes.iface.ContentProviderOfGeoItems;
import it.openyoureyes.iface.GeoItem;
import it.openyoureyes.iface.GuiUpdate;
import it.openyoureyes.iface.OrientationListener;
import it.openyoureyes.iface.ServiceUpdate;
import it.openyoureyes.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * This is the service Controller of EyES project, it acts like local service in
 * the android architecture. His lifecycle is managed by the application, that
 * start and stop it, on app start and stop. Manage all the sensors and notify
 * the changements, to all provider registered like listner. Also each provider
 * use this service to notify, in turn, some specific changements. Each provider
 * is invoked and registered by Controller reading an xml file "configure.xml".
 * Implements controller notation in MVC pattern.
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class Controller extends Service implements OrientationListener,
		ServiceUpdate {

	private Map<String, List<GeoItem>> globalContentGeoItemsByProviderName;
	private List<ContentProviderOfGeoItems> listContentGeoProvider;
	private SensorManager mSensorManager;
	private Sensor mOrientation;
	private float horizontalCameraAngle;
	private float verticalCameraAngle;
	private int screenWith;
	private int screenHeight;
	public static int RAGGIO_VISUALIZZATO = 0;// in metri
	public static double RAGGIO_TERRA = 6371000;

	private LocationManager locationManager;
	public static volatile Location CURRENT_LOCATION;
	private GuiUpdate updater;
	private final IBinder mBinder = new LocalControllerBinder();

	enum Side {
		TOP, BOTTOM, LEFT, RIGHT;
	}

	public float getHorizontalCameraAngle(float roll) {
		int diff=(int)Math.abs(horizontalCameraAngle-verticalCameraAngle);
		
		return horizontalCameraAngle+(int)(diff*roll/90);
	}

	public void setHorizontalCameraAngle(float horizontalCameraAngle) {
		this.horizontalCameraAngle = horizontalCameraAngle;
	}

	public float getVerticalCameraAngle(float roll) {
		int diff=(int)Math.abs(horizontalCameraAngle-verticalCameraAngle);
		
		return verticalCameraAngle+(int)(diff*roll/90);
	}

	public void setVerticalCameraAngle(float verticalCameraAngle) {
		this.verticalCameraAngle = verticalCameraAngle;
	}

	public int getScreenWith(float roll) {
		int diff=(int)Math.abs(screenHeight-screenWith);
		
		return screenWith+(int)(diff*roll/90);
	}

	public void setScreenWith(int screenWith) {
		this.screenWith = screenWith;
	}

	public int getScreenHeight(float roll) {
		int diff=(int)Math.abs(screenHeight-screenWith);
		return screenHeight-(int)(diff*roll/90);
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public class LocalControllerBinder extends Binder {
		public Controller getService() {
			return Controller.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LocalControllerService", "Received start id " + startId + ": "
				+ intent);
		SharedPreferences prefs = this
				.getSharedPreferences(PreferenceActivity.PREFERENCE_NAME,
						Context.MODE_WORLD_READABLE);
		RAGGIO_VISUALIZZATO = prefs.getInt(
				PreferenceActivity.PREFERENCE_NAME_DISTANZA, 1000);
		listContentGeoProvider = new ArrayList<ContentProviderOfGeoItems>();
		globalContentGeoItemsByProviderName = new HashMap<String, List<GeoItem>>();
		try {

			Resources res = this.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.configure);
			xpp.next();
			int eventType = xpp.getEventType();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String ss = xpp.getName();
					if ("ContentProviderOfGeoItems".equals(ss)) {
						String clazz = "";
						int count = xpp.getAttributeCount();
						for (int i = 0; i < count; i++) {
							String name = xpp.getAttributeName(i);
							String value = xpp.getAttributeValue(i);
							if (name.equals("class")) {
								try {
									Class<?> cc = loader.loadClass(value);
									ContentProviderOfGeoItems ll = (ContentProviderOfGeoItems) cc
											.newInstance();
									ll.setContext(getApplicationContext());
									// ll.setServiceUpdater(this);
									ll.setMaxDistance(RAGGIO_VISUALIZZATO);
									ll.setCurrentLocation(CURRENT_LOCATION);
									ll.init();
									listContentGeoProvider.add(ll);

								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InstantiationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						// clazz=xpp.getAttributeNamespace(0);

						Log.d("aa", clazz);
					}
				}
				eventType = xpp.next();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(sensorEventListener, mOrientation,
				SensorManager.SENSOR_DELAY_UI);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
		CURRENT_LOCATION = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		updateCurrentLocation();
		updateCurrentMaxDistance();

	}

	@Override
	public void onDestroy() {
		if (!Util.isEmpty(listContentGeoProvider))
			for (ContentProviderOfGeoItems tmp : listContentGeoProvider)
				tmp.dispose();
		mSensorManager.unregisterListener(sensorEventListener);
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		if (!Util.isEmpty(listContentGeoProvider))
			for (ContentProviderOfGeoItems tmp : listContentGeoProvider)
				tmp.dispose();
		mSensorManager.unregisterListener(sensorEventListener);
		locationManager.removeUpdates(locationListener);
		return super.onUnbind(intent);
	}

	@Deprecated
	public void addListOfItems(ContentProviderOfGeoItems ii) {
		listContentGeoProvider.add(ii);
	}

	@Override
	public IBinder onBind(Intent intent) {

		return mBinder;
	}

	public void setUpdater(GuiUpdate updater) {
		this.updater = updater;
	}

	public GuiUpdate getUpdater() {
		return updater;
	}

	/**
	 * Dispose all content providers.
	 */
	public synchronized void disposAllAbstractContentProviderOfGeoItems() {
		if (!Util.isEmpty(listContentGeoProvider)) {
			for (ContentProviderOfGeoItems cc : listContentGeoProvider)
				cc.dispose();
			listContentGeoProvider.clear();
		}
	}

	@Override
	@Deprecated
	public void updateSingleListItem(ContentProviderOfGeoItems list) {
		if (list != null) {

			this.listContentGeoProvider.add(list);

		}
	}

	/**
	 * 
	 * @return All geoitems for every content provider registered.
	 */
	private List<GeoItem> getAllItems() {
		ArrayList<GeoItem> result = new ArrayList<GeoItem>();
		if (globalContentGeoItemsByProviderName != null) {
			for (List<GeoItem> tmp : globalContentGeoItemsByProviderName
					.values())
				if (!Util.isEmpty(tmp))
					result.addAll(tmp);
		}
		return result;
	}

	/**
	 * Update only the items associated with provider passed as parameter.
	 * 
	 * @param listOf
	 *            The content provider that request updating its items.
	 */
	public synchronized void updateItems(ContentProviderOfGeoItems listOf) {

		if (listOf != null) {
			globalContentGeoItemsByProviderName.put(
					listOf.getClass().getName(), listOf.getGeoItems());
		}

	}

	/**
	 * Notify to all providers the new current location.
	 */
	private synchronized void updateCurrentLocation() {
		if (!Util.isEmpty(listContentGeoProvider))
			for (ContentProviderOfGeoItems ll : listContentGeoProvider)
				ll.setCurrentLocation(CURRENT_LOCATION);

	}

	/**
	 * Notify to all providers the new max distance.
	 */
	public synchronized void updateCurrentMaxDistance() {
		if (!Util.isEmpty(listContentGeoProvider))
			for (ContentProviderOfGeoItems ll : listContentGeoProvider)
				ll.setMaxDistance(RAGGIO_VISUALIZZATO);

	}
	/**
	 * Roll normalization
	 * @param pitch
	 * @param roll
	 * @return
	 */
	private float computeCompleteRoll(float pitch,float roll)
	{
		float result=0;
		if(pitch<=0)
		{
			if(roll<0)
				result= roll;
			else if(roll==0)
				result=0;
			else
				result=-360+roll;//-360 - -270 to left 90
		}
		else if(pitch>=0)
		{
			if(roll<0)		
				result=-180-roll;//-180 - 90 to left 270
			else if(roll==0)
				result=-180;
			else 
				result= -270+(90-roll);//-270 - -180 to left 180
		}
		return  result;
	}

	@Override
	public void onOrientationChanged(float azimuth, float pitch, float roll) {
		List<GeoItem> geoItems = getAllItems();
		List<GeoItem> itemsResult = new ArrayList<GeoItem>();
		float completeRoll=computeCompleteRoll(pitch,roll);
		
		float[] range = new float[2];
		float angleX = getHorizontalCameraAngle(roll);
		float angleY = getVerticalCameraAngle(roll);
		calculateNorth(azimuth);
		azimuth+=roll;
		pitch-=Math.abs(roll);
		float[] littleAngle = new float[2];
		littleAngle[0] = (pitch - (angleY / 2.0f))%180;
		littleAngle[1] = (pitch + (angleY / 2.0f))%180;
		/*if (littleAngle[0] > 0)
			littleAngle[0] -= 180;
		if (littleAngle[1] > 0)
			littleAngle[1] -= 180;*/
		
		Log.i("liitle 0", ""+littleAngle[0]);
		Log.i("liitle 1", ""+littleAngle[1]);
		range[0] = (azimuth - (angleX / 2)) % 360;
		range[1] = (azimuth + (angleX / 2)) % 360;
		if (range[0] < 0)
			range[0] += 360;
		if (range[1] < 0)
			range[1] += 360;

		if (!Util.isEmpty(geoItems)) {
			for (GeoItem ii : geoItems) {
				double bearingY;
				double bearingX = AbstractGeoItem.getRhumbLineBearing(
						CURRENT_LOCATION.getLatitude(),
						CURRENT_LOCATION.getLongitude(), ii.getLatitude(),
						ii.getLongitude());
				ii.setDistanceFromIt(AbstractGeoItem.distanceBetween(
						CURRENT_LOCATION.getLatitude(),
						CURRENT_LOCATION.getLongitude(), ii.getLatitude(),
						ii.getLongitude()));

				if (ii.getAltitude() == AbstractGeoItem.NO_ALTITUDE)
					bearingY = 90;
				else
					bearingY = Math.atan(ii.getDistanceFromIt()
							/ ii.getAltitude());

				if (ii.getDistanceFromIt() > RAGGIO_VISUALIZZATO)
					continue;

				float distaRdar = (float) (ii.getDistanceFromIt()
						/ RAGGIO_VISUALIZZATO * RadarView.RADIUS);
				if (bearingX < 0)
					bearingX += 360;
				int radarX = 0;
				int radarY = 0;
				double angleForRadar = azimuth - bearingX;

				/*
				 * In un triangolo rettangolo la misura di un cateto  uguale al
				 * prodotto dell'ipotenusa per il seno dell'angolo opposto o per
				 * il coseno dell'angolo adiacente
				 * 
				 * In un triangolo rettangolo la misura di un cateto  uguale al
				 * prodotto dell'altro cateto per la tangente dell'angolo
				 * opposto o per la cotangente dell'angolo adiacente
				 */

				radarY = (int) (Math.cos(Math.toRadians(angleForRadar)) * distaRdar);
				radarX = (int) (Math.sin(Math.toRadians(angleForRadar)) * distaRdar);

				ii.setRadarX(radarX);
				ii.setRadarY(radarY);
				//ii.setIcon(rotateImage(ii.getIcon(),roll));
				/*
				 * if (altitudeSeen != AbstractGeoItem.NO_ALTITUDE&&
				 * altitudeSeen <= ii.getAltitude()) continue;
				 */
				if ((bearingX >= range[0] && bearingX <= range[0] + angleX)
						|| (bearingX <= range[1] && bearingX >= range[1]
								- angleX)) {
					int height = ii.getIcon().getMinimumHeight();
					int width = ii.getIcon().getMinimumWidth();
					// Log.d("azimuth", "" + azimuth);
					if (bearingX < 0)
						bearingX *= -1;
					// if (bearing > angleX)
					bearingX = bearingX - range[0];
					bearingY = (littleAngle[0] * -1) - bearingY;
					if(bearingY<0)
						bearingY=bearingY+180;
					int x = (int) ((bearingX / angleX) * (float) getScreenWith(roll));
					int y = (int) ((bearingY / angleY) * (float) getScreenHeight(roll));
					//Log.i("X", ""+x);
					Log.i("Y", ""+y);
					Log.i("bearingY",""+bearingY);
					Log.i("angleY",""+angleY);
					Log.i("screen height", ""+getScreenHeight(roll));
					ii.getIcon().setBounds(
							new Rect(x, y, width + x, height + y));
					itemsResult.add(ii);
				}
			}
		}
		if (getUpdater() != null)
			getUpdater().update(itemsResult, geoItems,completeRoll);

	}

	public void onControllerLocationChanged(Location loc) {
		if (AbstractGeoItem.isBetterLocation(loc, CURRENT_LOCATION)) {
			CURRENT_LOCATION = loc;
			updateCurrentLocation();
		}
	}

	

	/**
	 * Useful method calculating the North on radar.
	 * 
	 * @param azimuth
	 */
	private void calculateNorth(float azimuth) {

		float distaRdar = (float) RadarView.RADIUS;

		int radarX = 0;
		int radarY = 0;
		double angleForRadar = azimuth;
		/*
		 * In un triangolo rettangolo la misura di un cateto Ã¨ uguale al
		 * prodotto dell'ipotenusa per il seno dell'angolo opposto o per il
		 * coseno dell'angolo adiacente
		 * 
		 * In un triangolo rettangolo la misura di un cateto Ã¨ uguale al
		 * prodotto dell'altro cateto per la tangente dell'angolo opposto o per
		 * la cotangente dell'angolo adiacente
		 */

		radarY = (int) (Math.cos(Math.toRadians(angleForRadar)) * distaRdar);
		radarX = (int) (Math.sin(Math.toRadians(angleForRadar)) * distaRdar);

		RadarView.NORTH.setRadarX(radarX);
		RadarView.NORTH.setRadarY(radarY);
	}

	/* SENSOR Listner */

	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@SuppressWarnings("unused")
		private Side currentSide = null;
		@SuppressWarnings("unused")
		private Side oldSide = null;
		private volatile float azimuth;
		private volatile float pitch;
		private volatile float roll;

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			float new_azimuth;
			float new_pitch;
			float new_roll;

			new_azimuth = event.values[0]; // azimuth
			// Log.d("azimuth", ""+azimuth);
			new_pitch = event.values[1]; // pitch
			// Log.d("pitch", ""+pitch);
			new_roll = event.values[2]; // roll
			// Log.d("roll", ""+roll);

			if (new_pitch < -45 && new_pitch > -135) {
				// top side up
				currentSide = Side.TOP;
			} else if (new_pitch > 45 && new_pitch < 135) {
				// bottom side up
				currentSide = Side.BOTTOM;
			} else if (new_roll > 45) {
				// right side up
				currentSide = Side.RIGHT;
			} else if (new_roll < -45) {
				// left side up
				currentSide = Side.LEFT;
			}

			/*
			 * if (currentSide != null && !currentSide.equals(oldSide)) { switch
			 * (currentSide) { case TOP : listener.onTopUp(); break; case BOTTOM
			 * : listener.onBottomUp(); break; case LEFT: listener.onLeftUp();
			 * break; case RIGHT: listener.onRightUp(); break; } oldSide =
			 * currentSide; }
			 */

			// forwards orientation to the OrientationListener

			if (Math.abs(Math.abs(new_azimuth) - Math.abs(azimuth)) > 1
					|| Math.abs(Math.abs(new_pitch) - Math.abs(pitch)) > 1
					|| Math.abs(Math.abs(new_roll) - Math.abs(roll)) > 1) {

				azimuth = new_azimuth;
				roll = new_roll;
				pitch = new_pitch;
				onOrientationChanged(azimuth, pitch, roll);
			}
		}

	};

	/* GPS LISTNER */

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			onControllerLocationChanged(location);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};

}
