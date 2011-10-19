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

import it.openyoureyes.R;
import it.openyoureyes.business.Controller;
import it.openyoureyes.iface.GeoItem;
import it.openyoureyes.iface.GuiUpdate;
import it.openyoureyes.iface.RadarItem;
import it.openyoureyes.surface.CameraView;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

/**
 * This is the main activity of EyES. Here is managed the lifecycle of
 * {@link Controller} service, the {@link RadarView}, the {@link CameraView} and
 * the {@link CustomDrawableView}.This class implements a View notation in MVC pattern.
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class Start extends Activity implements GuiUpdate {

	public static int RESULT_FROM_PREFERENCE = 1001;
	private CustomDrawableView drawableView;
	private CameraView cameraPreview;
	private RadarView radarView;
	private Handler guiHandler;
	private boolean mIsBound = false;
	private WakeLock lock;
	private Controller mBoundService;

	private ServiceConnection mConnection;

	private void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		bindService(new Intent(this, Controller.class), mConnection,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	private void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			unbindService(mConnection);
			mIsBound = false;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName className,
					IBinder service) {
				// This is called when the connection with the service has been
				// established, giving us the service object we can use to
				// interact with the service. Because we have bound to a
				// explicit
				// service that we know is running in our own process, we can
				// cast its IBinder to a concrete class and directly access it.
				mBoundService = ((Controller.LocalControllerBinder) service)
						.getService();
				mBoundService.setUpdater(Start.this);

				mBoundService.setHorizontalCameraAngle(cameraPreview
						.getHorizontalAngle());
				mBoundService.setVerticalCameraAngle(cameraPreview
						.getVerticalAngle());
				mBoundService.setScreenHeight(drawableView.getHeight());
				mBoundService.setScreenWith(drawableView.getWidth());

				// Tell the user about this for our demo.
				/*
				 * Toast.makeText(Binding.this,
				 * R.string.local_service_connected, Toast.LENGTH_SHORT).show();
				 */
			}

			public void onServiceDisconnected(ComponentName className) {
				// This is called when the connection with the service has been
				// unexpectedly disconnected -- that is, its process crashed.
				// Because it is running in our same process, we should never
				// see this happen.
				mBoundService = null;
				/*
				 * Toast.makeText(Binding.this,
				 * R.string.local_service_disconnected,
				 * Toast.LENGTH_SHORT).show();
				 */
			}
		};
		guiHandler = new Handler();
		Intent svc = new Intent(this, Controller.class);
		startService(svc);
		doBindService();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		this.lock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				"EngAugmented");
		drawableView = (CustomDrawableView) findViewById(R.id.drawableView);
		cameraPreview = (CameraView) findViewById(R.id.CameraPreview);
		radarView = (RadarView) findViewById(R.id.RdararView);

	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

	}

	@Override
	protected void onPause() {
		super.onPause();

		try {
			this.lock.release();
			doUnbindService();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		try {
			this.lock.acquire();
			doBindService();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		destroy();

		super.onBackPressed();

	}

	/**
	 * Destroy all the components of EyES.
	 * 
	 * @return True if stop the service @link Controller}.
	 */
	private boolean destroy() {
		doUnbindService();
		mBoundService.disposAllAbstractContentProviderOfGeoItems();
		Intent svc = new Intent(this, Controller.class);
		boolean res = stopService(svc);
		return res;

	}

	@Override
	protected void onDestroy() {
		// destroy();
		super.onDestroy();

	}

	// Redraw
	@Override
	public void update(final List<GeoItem> seenItems,
			final List<? extends RadarItem> allItems,final float roll) {
		guiHandler.post(new Runnable() {

			@Override
			public void run() {
				// if(!Util.isEmpty(seenItems))
				drawableView.drawPOI(seenItems,roll);
				radarView.drawPoi(allItems);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;

		// return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.preference) {
			Intent inte = new Intent(getApplicationContext(),
					PreferenceActivity.class);
			startActivityForResult(inte, RESULT_FROM_PREFERENCE);
			return true;
		} else
			return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_FROM_PREFERENCE) {
			mBoundService.updateCurrentMaxDistance();
		} else
			super.onActivityResult(requestCode, resultCode, data);
	}

}
