package com.vn.cooperate.moneyhunter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.model.FriendModel;

public class ListFriendsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FriendModel> listFriends;
	private LayoutInflater inflater;
	private AQuery mAQ;

	public ListFriendsAdapter(Context context,
			ArrayList<FriendModel> listFriends) {
		super();
		this.context = context;
		this.listFriends = listFriends;
		this.inflater = LayoutInflater.from(context);
		this.mAQ = new AQuery(context);
	}

	@Override
	public int getCount() {
		return listFriends.size();
	}

	@Override
	public Object getItem(int position) {
		return listFriends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_friend, parent, false);
			holder = new ViewHolder();
			holder.imgAvatar = (ImageView) convertView
					.findViewById(R.id.img_avatar);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_display_name);
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.tv_number_friend);
			holder.rlParent = (RelativeLayout) convertView.findViewById(R.id.rl_parent);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AQuery aQuery = mAQ.recycle(convertView);
		aQuery.id(holder.imgAvatar).image(
				listFriends.get(position).getAvatar(), true, true, 0, 0, null,
				0, 1);
		holder.tvName.setText(listFriends.get(position).getDisplayName());
		holder.tvNumber.setText(listFriends.get(position).getNumberFriend());
		holder.rlParent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//call detail
			}
		});

		return convertView;
	}

	class ViewHolder {
		RelativeLayout rlParent;
		ImageView imgAvatar;
		TextView tvName;
		TextView tvNumber;
	}
}
