/**
 * @ com.allpoint.util.adapters.FilterListAdapter
 */
package com.allpoint.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.allpoint.R;
import com.allpoint.model.Filter;

/**
 * FilterListAdapter
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class FilterListAdapter extends ArrayAdapter<Filter> {
    public FilterListAdapter(Context context) {
        super(context, R.layout.filter_item);
    }

    static class ViewHolder {
        protected CheckBox checkBoxFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // noinspection UnusedAssignment
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.filter_item, null);
            holder = new ViewHolder();
            holder.checkBoxFilter = (CheckBox) convertView
                    .findViewById(R.id.chBoxFilterName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Filter item = getItem(position);
        String itemName = " ";
        boolean isChecked = false;

        if (item != null) {
            itemName = item.getName();
            isChecked = item.isActivated();
        }

        holder.checkBoxFilter.setText(itemName);
        holder.checkBoxFilter.setChecked(isChecked);

        return convertView;
    }
}
