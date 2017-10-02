/**
 * 
 */
package mavinab.ops.bl;

import android.app.Application;
import android.content.Context;

/**
 * @author Keyur Ashra
 * 
 */
public class OPSApplication extends Application {

	private Context mContext = null;

	public OPSApplication() {
		mContext = this;
	}

	/**
	 * @return the mContext
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * @param context
	 *            the mContext to set
	 */
	public void setContext(Context context) {
		this.mContext = context;
	}

}
