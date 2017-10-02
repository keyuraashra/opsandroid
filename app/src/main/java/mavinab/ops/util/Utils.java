package mavinab.ops.util;

import java.io.IOException;

import mavinab.ops.constants.OPSGlobal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class Utils {
	private static final String TAG = "[OPS : Utils]";

	// ARE WE CONNECTED TO THE NET
	/**
	 * Checks that Internet Connection is Available and are we connected to
	 * Internet.
	 * 
	 * @param context
	 *            Activity context used for SystemService
	 * @return true if connected or false if not connected
	 */
	public static boolean checkInternetConnection(final Context context) {
		final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static String getResult(final String url) {
		String line = new String();
		try {
			Log.i(TAG, "URL == " + url);
			final HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			HttpConnectionParams.setConnectionTimeout(httpParameters, OPSGlobal.CONNECTION_TIME_OUT);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, OPSGlobal.CONNECTION_TIME_OUT);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpGet httpGet = new HttpGet(url.toString());
			HttpResponse httpResponse = null;

			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (ClientProtocolException e) {
				line = "Error";
				Log.e(TAG, e.getMessage(), e);
			} catch (IOException e) {
				line = "Error";
				Log.e(TAG, e.getMessage(), e);
			}

			HttpEntity httpEntity = httpResponse.getEntity();
			line = null;
			try {
				line = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				line = "Error";
				Log.e(TAG, e.getMessage(), e);
			} catch (IOException e) {
				line = "Error";
				Log.e(TAG, e.getMessage(), e);
			}
		} catch (Exception e) {
			line = "Error";
			Log.e(TAG, e.getMessage(), e);
		}
		return line;
	}
}
