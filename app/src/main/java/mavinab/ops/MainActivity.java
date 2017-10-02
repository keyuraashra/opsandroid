package mavinab.ops;

import java.util.ArrayList;
import java.util.List;

import mavinab.ops.adapters.CategoryGridAdapter;
import mavinab.ops.adapters.MenuGridAdapter;
import mavinab.ops.constants.OPSGlobal;
import mavinab.ops.constants.OPSPreferences;
import mavinab.ops.pojo.CategoryPojo;
import mavinab.ops.pojo.MenuPojo;
import mavinab.ops.pojo.OrderListPojo;
import mavinab.ops.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "[OPS : MainActivity]";
	private GridView mCategoryGridView, mMenuGridView = null;
	private ListView mOrderLisView = null;

	private final List<CategoryPojo> mCategoryList = new ArrayList<CategoryPojo>(); // Category
	private final List<MenuPojo> mMenuList = new ArrayList<MenuPojo>(); // Menu
	private final List<OrderListPojo> mOrderList = new ArrayList<OrderListPojo>();

	private SharedPreferences mPref = null;

	private CategoryAsyncTask mCategoryTask = null;
	private MenuAsyncTask mMenuAsyncTask = null;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPref = getSharedPreferences(OPSPreferences.PREFERENCE, MODE_PRIVATE);

		mCategoryGridView = (GridView) findViewById(R.id.gv_category);
		mMenuGridView = (GridView) findViewById(R.id.gv_category);

		mCategoryGridView.setOnItemClickListener(categoryGridItemClickListner);
		mMenuGridView.setOnItemClickListener(menuGridClickListner);

		mOrderLisView = (ListView) findViewById(R.id.lv_order_listview);
		mCategoryTask = new CategoryAsyncTask();
		mCategoryTask.execute();
	}

	/**
	 * 
	 */
	OnItemClickListener categoryGridItemClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> adaprerView, final View view, final int position, final long id) {
			mMenuAsyncTask = new MenuAsyncTask();
			mMenuAsyncTask.execute(mCategoryList.get(position).getCategoryId());
		}
	};

	/**
	 * 
	 */
	OnItemClickListener menuGridClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> adaprerView, final View view, final int position, final long id) {
			Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			openDialog(((TextView) view).getText().toString());
		}
	};

	private void openDialog(final String title) {
		final MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
		myDialogFragment.show(getFragmentManager(), title);
	}

	/**
	 * Ok Button Clicked
	 */
	public void okClicked() {
		Toast.makeText(MainActivity.this, "OK Clicked!", Toast.LENGTH_LONG).show();
		
	}

	/**
	 * Cancel Button Clicked
	 */
	public void cancelClicked() {
		Toast.makeText(MainActivity.this, "Cancel Clicked!", Toast.LENGTH_LONG).show();
	}

	/**
	 * Category Task
	 * 
	 * @author Keyur Ashra
	 * 
	 */
	private class CategoryAsyncTask extends AsyncTask<Void, Void, Void> {

		private boolean flag = false;
		private ProgressDialog pDialog = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading Categories");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(final Void... params) {
			final String result = Utils.getResult(OPSGlobal.GET_CATEGORIES + mPref.getStringSet(OPSPreferences.RESTAURANT_ID, null));
			if (result != null && !result.equalsIgnoreCase("Error")) {
				flag = parseJsonData(result);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if (flag) {
				final CategoryGridAdapter adapter = new CategoryGridAdapter(MainActivity.this, mCategoryList);
				mCategoryGridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(MainActivity.this, "Error in Category.", Toast.LENGTH_LONG).show();
			}
		}

		/**
		 * Parse Category JSON Data which is received from Web Service
		 * 
		 * @param jsonData
		 *            JSON String
		 * @return true/false
		 */
		private boolean parseJsonData(final String jsonData) {
			boolean flag = false;
			try {
				final JSONObject jsonObject = new JSONObject(jsonData);
				if (jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.SUCCESS_VALUE)) {
					final JSONArray categoryJsonArray = jsonObject.getJSONArray("categories");
					if (categoryJsonArray != null && categoryJsonArray.length() > 0) {
						for (int index = 0; index < categoryJsonArray.length(); index++) {
							final CategoryPojo pojo = new CategoryPojo(categoryJsonArray.getJSONObject(index).getString(OPSGlobal.CATEGORY_ID),
									categoryJsonArray.getJSONObject(index).getString(OPSGlobal.CATEGORY_NAME));
							mCategoryList.add(pojo);
						}
					}
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return flag;
		}
	}

	/**
	 * Menu Task
	 * 
	 * @author Keyur Ashra
	 * 
	 */
	private class MenuAsyncTask extends AsyncTask<String, Void, Void> {

		private boolean flag = false;
		private ProgressDialog pDialog = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading Menus");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(final String... params) {
			final String result = Utils.getResult(OPSGlobal.GET_MENUS + params[0]);
			if (result != null && !result.equalsIgnoreCase("Error")) {
				flag = parseJsonData(result);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if (flag) {
				final MenuGridAdapter adapter = new MenuGridAdapter(MainActivity.this, mMenuList);
				mMenuGridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				
			}
		}

		/**
		 * Parse Category JSON Data which is received from Web Service
		 * 
		 * @param jsonData
		 *            JSON String
		 * @return true/false
		 */
		private boolean parseJsonData(final String jsonData) {
			boolean flag = false;
			try {
				final JSONObject jsonObject = new JSONObject(jsonData);
				if (jsonObject.getString(OPSGlobal.SUCCESS_CODE).equalsIgnoreCase(OPSGlobal.SUCCESS_VALUE)) {
					final JSONArray categoryJsonArray = jsonObject.getJSONArray("menu");
					if (categoryJsonArray != null && categoryJsonArray.length() > 0) {
						for (int index = 0; index < categoryJsonArray.length(); index++) {
							final JSONObject menuObject = categoryJsonArray.getJSONObject(index);
							final MenuPojo pojo = new MenuPojo();
							pojo.setCategoryId(menuObject.getString("category_id"));
							pojo.setMenuId(menuObject.getString("menu_id"));
							pojo.setMenuItemName(menuObject.getString("menu_item_name"));
							pojo.setMenuPrice(menuObject.getDouble("menu_item_price"));
							pojo.setMenuItemPhoto(menuObject.getString("menu_item_photo"));
							pojo.setMenuItemDesc(menuObject.getString("menu_item_desc"));
							pojo.setEta(menuObject.getString("eta"));
							mMenuList.add(pojo);
						}
					}
					flag = true;
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return flag;
		}
	}
}
