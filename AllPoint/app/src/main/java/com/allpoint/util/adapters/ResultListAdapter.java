/**
 * @ com.allpoint.util.adapters.ResultListAdapter
 */
package com.allpoint.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.model.PointRecord;
import com.allpoint.util.IconManager;
import com.allpoint.util.Utils;

/**
 * ResultListAdapter
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class ResultListAdapter extends ArrayAdapter<PointRecord> {

    private final IconManager iconManager;

    public ResultListAdapter(Context context) {
        super(context, R.layout.result_item);

        // ToDo
        iconManager = new IconManager();
        //iconManager = IconManager_.getInstance_(context);
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvAddress;
        ImageView iViewLogo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // noinspection UnusedAssignment
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(R.layout.result_item, null);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvResultsTitle);
            holder.tvAddress = convertView.findViewById(R.id.tvResultsAddress);
            holder.iViewLogo = convertView.findViewById(R.id.iViewRetailerLogo);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PointRecord item = getItem(position);
        String itemTitle = " ";
        String itemAddress = " ";
        // noinspection UnusedAssignment
        Integer itemLogoResource = null;

        if (item != null) {

            itemTitle = item.getName() + " ("
                    + Utils.getDistanceString(item.getDistance()) + ")";
            itemAddress = item.getAddressLine() + " " + item.getCity() + ", "
                    + item.getState() + " " + item.getPostalCode();

            if (Utils.isTablet()) {
                if (item.isSelected()) {
                    convertView.setBackgroundResource(R.color.alternateRow);
                } else {
                    convertView.setBackgroundResource(R.color.white);
                }
            }
        }

        try {
            if (item != null) {
                itemLogoResource = iconManager
                        .getIconByName(item.getLogoName());
            }
        } catch (NullPointerException npe) {
            itemLogoResource = null;
            npe.printStackTrace();
        }

        if (itemLogoResource == null) {
            itemLogoResource = R.drawable.retailer_allpoint;
        }

        holder.tvTitle.setText(itemTitle);
        holder.tvAddress.setText(itemAddress);
        holder.iViewLogo.setImageResource(itemLogoResource);

        return convertView;
    }
}