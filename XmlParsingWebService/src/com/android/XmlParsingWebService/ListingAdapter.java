package com.android.XmlParsingWebService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class ListingAdapter extends BaseAdapter {
	ImageManager imageManager;
	private Activity activity;
	private ArrayList<MyData> lists;
	LayoutInflater li;
	Object[] datas;
	public ListingAdapter(Activity a, ArrayList<MyData> lists) {
		activity = a;
		this.lists = lists;
		datas = lists.toArray();
		li = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageManager = new ImageManager(activity.getApplicationContext());
	}
	@Override
	public int getCount() {
		return lists.size();
	}
	private class ViewHolder {
		TextView titleView;
		TextView artistView;
		TextView releasedDateView;
		TextView priceView;
		ImageView imageView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyData mydata;
		View view = convertView;
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			view = li.inflate(R.layout.listlayout, null);
			holder.titleView = (TextView) view.findViewById(R.id.titleView);
			holder.artistView = (TextView) view.findViewById(R.id.artistView);
			holder.releasedDateView = (TextView) view
					.findViewById(R.id.releaseDateView);
			holder.priceView = (TextView) view.findViewById(R.id.priceView);
			holder.imageView = (ImageView) view.findViewById(R.id.imageView);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		mydata = (MyData) datas[position];
		holder.titleView.setText(mydata.mTitle);
		holder.artistView.setText(mydata.mArtist);
		Date dateFormat = null;
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd")
					.parse(mydata.mReleaseDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("E, MMM d, yyyy");
		String dateString = formatter.format(dateFormat);
		holder.releasedDateView.setText(dateString);
		holder.priceView.setText(mydata.mPrice);
		imageManager.displayImage(mydata.mImage, activity, holder.imageView);
		return view;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
