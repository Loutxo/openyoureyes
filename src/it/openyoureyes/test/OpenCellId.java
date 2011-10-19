package it.openyoureyes.test;

import it.openyoureyes.business.AbstractGeoItem;
import it.openyoureyes.iface.GeoItem;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;

/**
 * 
 * @author <a href="mailto:pasquale.paola@eng.it">Pasquale Paola</a>
 * 
 */
public class OpenCellId {

	private List<GeoItem> listaResult;
	private Drawable myImage;

	public DefaultHttpClient getClient() {
		DefaultHttpClient ret = null;

		// sets up parameters
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		params.setBooleanParameter("http.protocol.expect-continue", false);

		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		/*
		 * final SSLSocketFactory sslSocketFactory = SSLSocketFactory
		 * .getSocketFactory(); sslSocketFactory
		 * .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		 * registry.register(new Scheme("https", new EasySSLSocketFactory(),
		 * 443));
		 */

		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);
		ret = new DefaultHttpClient(manager, params);
		return ret;
	}

	public void getCellId(Context context, double latitudine,
			double longitudine, double distance, DefaultHandler dd)
			throws Exception {

		/*
		 * AbstractGeoItem gg=AbstractGeoItem.fromDegrees(latitudine,
		 * longitudine); AbstractGeoItem[] bb= gg.boundingCoordinates(1000,
		 * 100);
		 */
		DefaultHttpClient httpclient = getClient();

		HttpResponse response = null;
		HttpEntity entity = null;

		Location loc1 = new Location("test");
		Location loc2 = new Location("test_");
		AbstractGeoItem.calcDestination(latitudine, longitudine, 225, distance,
				loc1);
		AbstractGeoItem.calcDestination(latitudine, longitudine, 45, distance,
				loc2);
		// HttpGet httpost = new
		// HttpGet("http://www.opencellid.org/cell/getInArea?BBOX=14.117,40.781,14.412,40.934&fmt=xml");
		HttpGet httpost = new HttpGet(
				"http://www.opencellid.org/cell/getInArea/?key=622d4c6c01e7ab62187ef7875ce9ea48&BBOX="
						+ loc1.getLatitude()
						+ ","
						+ loc1.getLongitude()
						+ ","
						+ loc2.getLatitude()
						+ ","
						+ loc2.getLongitude()
						+ "&fmt=xml");
		response = httpclient.execute(httpost);

		entity = response.getEntity();
		if (entity != null)
			parseImage(entity, dd);
		response = null;
		entity = null;
		httpclient.getConnectionManager().shutdown();
		httpclient = null;

	}

	private String parseImage(HttpEntity entity, DefaultHandler dd)
			throws IOException {
		String result = "";
		String line;

		try {

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(dd);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(entity.getContent()));

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			entity.consumeContent();
		}
		return result;
	}

	public List<GeoItem> getListaResult() {
		return listaResult;
	}

}
