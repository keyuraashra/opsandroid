package mavinab.ops.adapters;

import java.util.List;

import mavinab.ops.R;
import mavinab.ops.pojo.OrderListPojo;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {

	private final LayoutInflater inflator;
	private List<OrderListPojo> mOrderList = null;

	public OrderAdapter(final Context context, final List<OrderListPojo> myList) {
		this.inflator = LayoutInflater.from(context);
		this.mOrderList = myList;
	}

	@Override
	public int getCount() {
		return mOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		return mOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder myViewHolder;
		try {
			if (convertView == null) {
				convertView = inflator.inflate(R.layout.row_item_order_listview, null);
				myViewHolder = new ViewHolder();
				myViewHolder.menuItemName = (TextView) convertView.findViewById(R.id.row_item_name);
				myViewHolder.qty = (TextView) convertView.findViewById(R.id.row_item_qty);
				myViewHolder.amount = (TextView) convertView.findViewById(R.id.row_item_amount);
				convertView.setTag(myViewHolder);
			} else {
				myViewHolder = (ViewHolder) convertView.getTag();
			}

			myViewHolder.menuItemName.setText(mOrderList.get(position).getItemName());
			myViewHolder.qty.setText(mOrderList.get(position).getItemQty());
			myViewHolder.amount.setText(mOrderList.get(position).getAmount());

		} catch (final Exception e) {
			Log.e("ListView Adapter Error", e.getMessage());
		}
		return convertView;
	}

	class ViewHolder {
		private TextView menuItemName;
		private TextView qty;
		private TextView amount;
	}

}
