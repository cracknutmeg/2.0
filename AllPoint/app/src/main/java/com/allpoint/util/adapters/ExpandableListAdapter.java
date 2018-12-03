package com.allpoint.util.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.services.ResponseTransaction;
import com.allpoint.util.Utils;

import java.io.InputStream;
import java.text.DecimalFormat;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private ResponseTransaction mDataset;

	// private List<ResponseTransactionDetails> _listDataHeader;
	DecimalFormat dec = new DecimalFormat("0.00");

	public ExpandableListAdapter(Context context, ResponseTransaction objects) {
		this._context = context;
		this.mDataset = objects;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.mDataset.getRspTrans().get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.act_nav_list_item,
					null);
		}

		TextView address_ATM = (TextView) convertView
				.findViewById(R.id.txtATMAddress);
		TextView address_City = (TextView) convertView
				.findViewById(R.id.txtCity);
		TextView address_State = (TextView) convertView
				.findViewById(R.id.txtState);
		TextView address_Zip = (TextView) convertView.findViewById(R.id.txtZip);

		address_ATM.setText(mDataset.getRspTrans().get(groupPosition)
				.getAddress());
		address_City.setText(mDataset.getRspTrans().get(groupPosition)
				.getCity()
				+ ",");
		address_State.setText(mDataset.getRspTrans().get(groupPosition)
				.getState());
		address_Zip.setText(mDataset.getRspTrans().get(groupPosition).getZip());

		String url = null;

		if (Utils.isTablet()) {

			url = "http://maps.google.com/maps/api/staticmap?center="
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLatitude()
					+ ","
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLongitude()
					+ "center=0,0&zoom=14&size=800x150&sensor=false&markers=color:green|"
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLatitude()
					+ ","
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLongitude();

		} else {

			url = "http://maps.google.com/maps/api/staticmap?center="
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLatitude()
					+ ","
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLongitude()
					+ "&zoom=14&size=600x250&sensor=false&markers=color:green|"
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLatitude()
					+ ","
					+ this.mDataset.getRspTrans().get(groupPosition)
							.getLongitude();

		}

		ImageView image = (ImageView) convertView.findViewById(R.id.mapImage);
		new DownloadImageTask(image).execute(url);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.mDataset.getRspTrans().get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.mDataset.getRspTrans().size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.act_nav_list_group,
					null);
		}

		TextView dateText = (TextView) convertView.findViewById(R.id.txtDate);
		TextView amountText = (TextView) convertView.findViewById(R.id.txtAmt);
		TextView txnTypeText = (TextView) convertView
				.findViewById(R.id.txtTxnType);
		TextView txnAddress = (TextView) convertView
				.findViewById(R.id.txtCardAddress);

		dateText.setText(mDataset.getRspTrans().get(groupPosition).getDate());
		txnTypeText.setText(mDataset.getRspTrans().get(groupPosition)
				.getTransactionType());

		// convert value into decimal
		Double in = Double.parseDouble(mDataset.getRspTrans()
				.get(groupPosition).getAmount());
		amountText.setText("$" + dec.format(in));
		txnAddress.setText(mDataset.getRspTrans().get(groupPosition)
				.getAddress());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				
				//e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

}
