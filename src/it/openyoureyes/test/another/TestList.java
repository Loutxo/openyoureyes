package it.openyoureyes.test.another;

import it.openyoureyes.R;
import it.openyoureyes.business.AbstractContentProviderOfGeoItems;
import it.openyoureyes.iface.GeoItem;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class TestList extends AbstractContentProviderOfGeoItems {

	private List<GeoItem> lista;

	public TestList() {

	}

	@Override
	public void run() {
		Resources res = context.getResources();
		lista = new ArrayList<GeoItem>();
		Intent in = new Intent(context, MyActivity.class);
		Drawable myImage = res.getDrawable(R.drawable.test);
		Drawable myImage2 = res.getDrawable(R.drawable.test_blue);
		Drawable myImage3 = res.getDrawable(R.drawable.test_yellow);
		Drawable myImage4 = res.getDrawable(R.drawable.test_green);

		// Lavoro
		// 41.067963, 14.372864 Napoli Nord
		// lista.add(new TestBean(41.067963, 14.372864 , false, "Napoli Nord",
		// myImage));
		// 40.751418, 13.922424 Ischia
		// lista.add(new TestBean(40.751418, 13.922424 , false, "Ischia",
		// myImage2,in,Color.RED));
		// 40.247040, 14.907074 Costa Salerno
		// lista.add(new TestBean(40.247040, 14.907074 , false, "CostaSalerno",
		// myImage3,in,Color.YELLOW));
		// 40.809652, 15.667877 Est * 1E6
		// lista.add(new TestBean(40.809652, 15.667877 , false, "Est",
		// myImage4,in,Color.GREEN));
		// 40.843607, 14.299422
		// lista.add(new TestBean(40.844824, 14.299255, false, "Lavoro",
		// myImage,in,Color.MAGENTA));
		// 40.844877, 14.299253 NEW LAVORO
		lista.add(new TestBean(40.844877, 14.299253, false, "LavoroNEW",
				myImage, in, Color.MAGENTA));

		// CASA
		// 40.765591, 14.527144
		lista.add(new TestBean(40.765591, 14.527144, false, "Lavoro", myImage,
				in, Color.RED));
		// 40.770954, 14.535213
		lista.add(new TestBean(40.770954, 14.535213, false, "Lavoro", myImage2,
				in, Color.YELLOW));
		// 40.771604, 14.517360
		lista.add(new TestBean(40.771604, 14.517360, false, "Lavoro", myImage3,
				in, Color.GREEN));
		// 40.778169, 14.527187
		lista.add(new TestBean(40.778169, 14.527187, false, "NORD", myImage,
				in, Color.RED));

		notifyUpdate();

	}

	@Override
	public List<GeoItem> getGeoItems() {
		// TODO Auto-generated method stub
		return lista;
	}

}
