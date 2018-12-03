package com.allpoint.util.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.model.CustomRichPushMessage;
import com.allpoint.util.Constant;

import java.text.SimpleDateFormat;

/**
 * MessageListAdapter
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class MessagesListAdapter extends ArrayAdapter<CustomRichPushMessage> {

	public MessagesListAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);
	}

	static class ViewHolder {
		TextView tvMessage;
		TextView tvTime;
		CheckBox chBoxCheckState;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// noinspection UnusedAssignment
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.message_item, null);
			holder = new ViewHolder();
			holder.tvMessage = (TextView) convertView
					.findViewById(R.id.tvPushMessage);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.chBoxCheckState = (CheckBox) convertView
					.findViewById(R.id.chBoxMessages);
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
		Boolean checkState = item.isChecked();

		holder.tvMessage.setText(message);
		holder.tvTime.setText(time);

		if (!readState) {
			holder.tvMessage.setTypeface(null, Typeface.BOLD);
		} else {
			holder.tvMessage.setTypeface(null, Typeface.NORMAL);
		}
		holder.chBoxCheckState.setChecked(checkState);

		return convertView;
	}
}
