package com.vn.cooperate.moneyhunter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.model.TransactionModel;

public class ListReceiveAdapter extends BaseAdapter {
	private ArrayList<TransactionModel> list;
	private Context mContext;
	private LayoutInflater inflater;

	public ListReceiveAdapter(ArrayList<TransactionModel> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		AQuery aQ = new AQuery(mContext);
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.item_receive, parent, false);
			holder = new ViewHolder();
			holder.imgApp = (ImageView) convertView.findViewById(R.id.img_app);
			holder.labelDes = (TextView) convertView
					.findViewById(R.id.label_description);
			holder.tvDes = (TextView) convertView
					.findViewById(R.id.tv_description);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		aQ.id(holder.imgApp).image(R.drawable.icon_dollar);
		if (list.get(position).getType().equals("1")) {
			holder.tvTitle.setText(mContext.getString(R.string.from_install));
		} else if (list.get(position).getType().equals("2")) {
			holder.tvTitle.setText(mContext.getString(R.string.from_f1));
		} else if (list.get(position).getType().equals("3")) {
			holder.tvTitle.setText(mContext.getString(R.string.from_f2));
		} else if (list.get(position).getType().equals("4")) {
			holder.tvTitle.setText(mContext
					.getString(R.string.from_make_friend));
		} else {
			holder.tvTitle.setText(mContext.getString(R.string.from_lucky));
		}
		holder.labelDes.setText(mContext.getString(R.string.earn));
		holder.tvDes.setText(list.get(position).getmCoin()
				+ mContext.getString(R.string.dollar));
		holder.tvDate.setText(list.get(position).getmDate());
		return convertView;
	}

	class ViewHolder {
		ImageView imgApp;
		TextView tvTitle;
		TextView labelDes;
		TextView tvDes;
		TextView tvDate;
	}

}
