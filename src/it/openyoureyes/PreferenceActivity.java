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

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Activity delegate managing preferences. In future, here you will configure
 * all your layers. Into general preferences og EyES, to day you manage only
 * distance from poi.
 * 
 * @author <a href="mailto:pasquale.paola@gmail.com">Pasquale Paola</a>
 * 
 */
public class PreferenceActivity extends Activity {

	public static final String PREFERENCE_NAME = "PREFERENCE_NAME_it.eng.na.augmented";
	public static final String PREFERENCE_NAME_DISTANZA = "PREFERENCE_NAME_DISTANZA__it.eng.na.augmented";
	private SeekBar seek;
	private Toast toast;
	private NumberFormat nf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);
		setResult(Start.RESULT_FROM_PREFERENCE);
		nf = NumberFormat.getInstance();
		seek = (SeekBar) findViewById(R.id.seekBar);
		toast = Toast.makeText(getBaseContext(),
				"" + nf.format(seek.getProgress()) + " m", Toast.LENGTH_SHORT);

		SharedPreferences prefs = this.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_WORLD_READABLE);

		int dist = prefs.getInt(PREFERENCE_NAME_DISTANZA, 1000);

		seek.setProgress(dist);

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				toast.setText(seek.getProgress() + " m");
				toast.show();
			}
		});

	}

	@Override
	public void onBackPressed() {

		SharedPreferences.Editor prefs = getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_WORLD_WRITEABLE).edit();

		// prefs.clear();
		prefs.putInt(PREFERENCE_NAME_DISTANZA, seek.getProgress());
		Controller.RAGGIO_VISUALIZZATO = seek.getProgress();
		prefs.commit();

		super.onBackPressed();
	}
}
