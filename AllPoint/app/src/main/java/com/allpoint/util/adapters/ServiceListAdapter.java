/**
 *@ com.allpoint.util.adapters.ResultListAdapter
 */
package com.allpoint.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allpoint.R;

/**
 * ServiceListAdapter
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class ServiceListAdapter extends ArrayAdapter<String> {

	public ServiceListAdapter(Context context) {
		super(context, R.layout.service_item);
	}

	static class ViewHolder {
		TextView tvTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// noinspection UnusedAssignment
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.service_item, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tvService);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String value = " ";

		if (getItem(position) != null) {
			value = getItem(position);
		}

		holder.tvTitle.setText(value);

		if (position % 2 == 0) {
			convertView.setBackgroundResource(R.color.servicesAlternate);
		} else {
			convertView.setBackgroundResource(R.color.white);
		}

		return convertView;
	}
}