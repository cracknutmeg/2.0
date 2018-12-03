package com.allpoint.util.adapters;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.model.CustomRichPushMessage;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;

/**
 * MessagesNoCheckboxListAdapter
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class MessagesNoCheckboxListAdapter extends
		ArrayAdapter<CustomRichPushMessage> {

	public MessagesNoCheckboxListAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);
	}

	static class ViewHolder {
		TextView tvMessage;
		TextView tvTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// noinspection UnusedAssignment
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(
					R.layout.message_item_no_checkboxes, null);
			holder = new ViewHolder();
			holder.tvMessage = (TextView) convertView
					.findViewById(R.id.tvPushMessage);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CustomRichPushMessage item = getItem(position);
		String message = item.getTitle();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				Constant.DATE_TIME_FORMAT);
		String time = dateFormat.format(item.getSendDate());

		Boolean readState = item.isRead();

		holder.tvMessage.setText(message);
		holder.tvTime.setText(time);

		if (!readState) {
			holder.tvMessage.setTypeface(null, Typeface.BOLD);
		} else {
			holder.tvMessage.setTypeface(null, Typeface.NORMAL);
		}

		if (Utils.isTablet()) {
			if (item.isSelected()) {
				convertView.setBackgroundResource(R.color.alternateRow);
			} else {
				convertView.setBackgroundResource(R.color.white);
			}
		}

		return convertView;
	}
}
