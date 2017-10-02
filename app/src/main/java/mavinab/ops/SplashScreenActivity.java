package mavinab.ops;

import mavinab.ops.constants.OPSPreferences;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

	private SharedPreferences mPref = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		mPref = getSharedPreferences(OPSPreferences.PREFERENCE, MODE_PRIVATE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(runnable, 5000);
	}

	/**
	 * Start Activity
	 */
	private void startOPS() {
		if (mPref.getString(OPSPreferences.TABLE_NO, null) != null && mPref.getString(OPSPreferences.RESTAURANT_NAME, null) != null) {
			// Start Login Activity
			startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			finish();
		} else {
			// Start Table and Restaurant Activity
			startActivity(new Intent(SplashScreenActivity.this, AskRestaurantActivty.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			finish();
		}
	}

	final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			startOPS();
		}
	};

}
