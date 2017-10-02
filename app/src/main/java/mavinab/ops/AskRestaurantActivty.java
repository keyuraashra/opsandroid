package mavinab.ops;

import org.json.JSONException;
import org.json.JSONObject;

import mavinab.ops.constants.OPSGlobal;
import mavinab.ops.constants.OPSPreferences;
import mavinab.ops.util.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AskRestaurantActivty extends Activity {

	private SharedPreferences mPref = null;
	private EditText mEdtRestaurantName, mEdtTableNo;
	private static final String TAG = "[OPS : AskRestaurantActivty]";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_restaurant);

		mPref = getSharedPreferences(OPSPreferences.PREFERENCE, MODE_PRIVATE);

		mEdtRestaurantName = (EditText) findViewById(R.id.edt_restaurant_name);
		mEdtTableNo = (EditText) findViewById(R.id.edt_table_no);

		final Button btnOk = (Button) findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(btnOkClickListner);
	}

	OnClickListener btnOkClickListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mEdtRestaurantName.getText().toString().length() > 0 && mEdtTableNo.getText().toString().length() > 0) {
				// Insert Restaurant in Databse throgh web service
				new InsertRestaurantTask().execute();
			} else {
				mEdtRestaurantName.requestFocus();
				Toast.makeText(AskRestaurantActivty.this, "Fields are mandatory.", Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * Insert Restaurant into DB on Server
	 * 
	 * @author Keyur Ashra
	 * 
	 */
	private class InsertRestaurantTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog = null;
		private boolean flag = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AskRestaurantActivty.this);
			pDialog.setMessage("Adding Restaurant...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			final String result = Utils.getResult(OPSGlobal.INSERT_RESTAURANT + mEdtRestaurantName.getText().toString());
			Log.i(TAG, "Result = " + result);
			if (result != null && !result.equalsIgnoreCase("Error")) {
				flag = parseJsonData(result);
			} else {
				flag = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();

			if (flag) {
				// Start Login Activity
				startActivity(new Intent(AskRestaurantActivty.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				finish();
			} else {
				Toast.makeText(AskRestaurantActivty.this, "Sorry! Please Re-Enter Name and Table No.", Toast.LENGTH_LONG).show();
				mEdtRestaurantName.requestFocus();
			}
		}

		/**
		 * Parse Restaurant JSON Data which is received from Web Service
		 * 
		 * @param jsonData
		 *            JSON String
		 * @return true/false
		 */
		private boolean parseJsonData(final String jsonData) {
			boolean flag = false;
			try {
				final JSONObject jsonObject = new JSONObject(jsonData);
				if (jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.SUCCESS_VALUE)
						|| jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.EXIST_VALUE)) {
					final String restaurant_id = jsonObject.getJSONArray("restaurant").getJSONObject(0).getString("restaurant_id");
					mPref.edit().putString(OPSPreferences.RESTAURANT_ID, restaurant_id).commit();
					mPref.edit().putString(OPSPreferences.RESTAURANT_NAME, mEdtRestaurantName.getText().toString()).commit();
					mPref.edit().putString(OPSPreferences.TABLE_NO, mEdtTableNo.getText().toString()).commit();
					flag = true;
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return flag;
		}
	}

}