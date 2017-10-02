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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText mEdtName, mEdtEmail, mEdtDob;
	private SharedPreferences mPref = null;
	private static final String TAG = "[OPS : LoginActivity]";
	private Button mBtnNewUser, mBtnExistingUser;
	private boolean flag = false;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mPref = getSharedPreferences(OPSPreferences.PREFERENCE, MODE_PRIVATE);

		mEdtName = (EditText) findViewById(R.id.edt_user_name);
		mEdtDob = (EditText) findViewById(R.id.edt_user_dob);
		mEdtEmail = (EditText) findViewById(R.id.edt_user_email);
		final Button btnSignIn = (Button) findViewById(R.id.btn_sign_in);
		mBtnNewUser = (Button) findViewById(R.id.btn_new_user);
		mBtnExistingUser = (Button) findViewById(R.id.btn_existing_user);

		btnSignIn.setOnClickListener(btnSignInClickListner);
		mBtnNewUser.setOnClickListener(btnNewUserClickListner);
		mBtnExistingUser.setOnClickListener(btnExistingUserClickListner);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Sigin ClickListner
	 */
	OnClickListener btnSignInClickListner = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			if (flag) {
				if (mEdtName.getText().toString().length() > 0 && mEdtDob.getText().toString().length() > 0
						&& mEdtEmail.getText().toString().length() > 0) {
					new LoginTask().execute();
				} else {
					Toast.makeText(LoginActivity.this, "All fields are mandatory.", Toast.LENGTH_LONG).show();
				}
			} else {
				if (mEdtEmail.getText().toString().length() > 0) {
					new LoginTask().execute();
				} else {
					Toast.makeText(LoginActivity.this, "Email is mandatory.", Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	OnClickListener btnNewUserClickListner = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			flag = true;
			final RelativeLayout rlNewUserLayout = (RelativeLayout) findViewById(R.id.rl_new_login);
			rlNewUserLayout.setVisibility(View.VISIBLE);
			mBtnNewUser.setVisibility(View.GONE);
			mBtnExistingUser.setVisibility(View.GONE);
		}
	};

	OnClickListener btnExistingUserClickListner = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			flag = false;
			final RelativeLayout rlNewUserLayout = (RelativeLayout) findViewById(R.id.rl_new_login);
			rlNewUserLayout.setVisibility(View.VISIBLE);
			mEdtDob.setVisibility(View.GONE);
			mEdtName.setVisibility(View.GONE);
			mBtnNewUser.setVisibility(View.GONE);
			mBtnExistingUser.setVisibility(View.GONE);
		}
	};

	/**
	 * Insert Restaurant into DB on Server
	 * 
	 * @author Keyur Ashra
	 * 
	 */
	private class LoginTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog = null;
		private boolean flag = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Please Wait. Logging in...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(final Void... params) {
			final String result = Utils.getResult(OPSGlobal.LOGIN + mEdtName.getText().toString() + "&dob=" + mEdtDob.getText().toString()
					+ "&email=" + mEdtEmail.getText().toString());
			flag = parseJsonData(result);
			return null;
		}

		private boolean parseJsonData(final String jsonData) {
			boolean flag = false;
			try {
				final JSONObject jsonObject = new JSONObject(jsonData);
				if (jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.SUCCESS_VALUE)
						|| jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.EXIST_VALUE)) {
					flag = true;
					final String user_id = jsonObject.getJSONArray("customer").getJSONObject(0).getString("customer_id");
					final String user_name = jsonObject.getJSONArray("customer").getJSONObject(0).getString("customer_name");
					final String user_email = jsonObject.getJSONArray("customer").getJSONObject(0).getString("email");
					mPref.edit().putString(OPSPreferences.USER_ID, user_id).commit();
					mPref.edit().putString(OPSPreferences.USER_NAME, user_name).commit();
					mPref.edit().putString(OPSPreferences.USER_EMAIL, user_email).commit();
				}
			} catch (final JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}
			return flag;
		}

		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();

			if (flag) {
				startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		}
	}

}
