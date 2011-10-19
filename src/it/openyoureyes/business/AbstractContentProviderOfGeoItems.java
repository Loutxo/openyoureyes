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

package it.openyoureyes.business;

import it.openyoureyes.iface.ContentProviderOfGeoItems;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;

/**
 * Abstract implementation of the interface {@link ContentProviderOfGeoItems}.
 * This is a useful class because implements all common features of a
 * ContentProvider of GeoItems. Also, this class implements the Model notation, in MVC pattern.
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public abstract class AbstractContentProviderOfGeoItems implements
		ContentProviderOfGeoItems, Runnable {

	private volatile Controller mBoundService;
	protected Context context;
	protected Location current;
	protected double distance;
	private ServiceConnection mConnection;
	private boolean mIsBound;

	// protected abstract boolean isAutoNotifyable();

	public AbstractContentProviderOfGeoItems() {

	}

	@Override
	public void init() {
		doBindService();

	}

	/**
	 * This method estabilishes a connection (IPC) with the service {@link Controller}.  
	 */
	@Override
	public void setContext(Context cnt) {
		context = cnt;
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
				notifyUpdate();
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
	}

	/**
	 * Make a binding with the service {@link Controller}.
	 */
	private void doBindService() {
		// Establish a connection with the service. We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		context.bindService(new Intent(context, Controller.class), mConnection,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	/**
	 * Make an unbinding with the service {@link Controller}.<br>
	 * Used before destruction of the entire applicaion.
	 */
	private void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			context.unbindService(mConnection);
			mIsBound = false;
		}
	}

	@Override
	public void setCurrentLocation(Location current) {
		this.current = current;
		if (distance > 0 && current != null)
			executeRunnable();

	}

	/**
	 * Implementaion of the <b>Command</b> pattern.
	 */
	private void executeRunnable() {
		Thread tt = new Thread(this);

		tt.start();

	}

	@Override
	public void setMaxDistance(double maxDistanze) {

		this.distance = maxDistanze;
		if (distance > 0 && current != null)
			executeRunnable();
	}

	/**
	 * Inform the {@link Controller} about updates.
	 */
	protected void notifyUpdate() {
		if (mBoundService != null)
			mBoundService.updateItems(this);
	}

	@Override
	public void dispose() {
		doUnbindService();

	}

}
