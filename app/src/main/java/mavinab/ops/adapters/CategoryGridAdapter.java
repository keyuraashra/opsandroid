package mavinab.ops.adapters;

import java.util.List;

import mavinab.ops.pojo.CategoryPojo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CategoryGridAdapter extends BaseAdapter {
	private final Context mContext;
	private List<CategoryPojo> mCategoryList = null;

	public CategoryGridAdapter(final Context context, final List<CategoryPojo> myList) {
		mContext = context;
		mCategoryList = myList;
	}

	@Override
	public int getCount() {
		return mCategoryList.size();
	}

	@Override
	public Object getItem(final int position) {
		return mCategoryList.get(position).getCategoryName();
	}

	@Override
	public long getItemId(final int position) {
		return 0;
	}

	@Override
	public View getView(final int position, final View view, final ViewGroup parent) {
		return null;
	}
}
