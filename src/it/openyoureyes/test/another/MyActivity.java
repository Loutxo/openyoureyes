package it.openyoureyes.test.another;

import it.openyoureyes.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class MyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
	}

}
