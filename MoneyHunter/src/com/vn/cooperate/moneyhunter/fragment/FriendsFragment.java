package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.vn.cooperate.moneyhunter.MainActivity;
import com.vn.cooperate.moneyhunter.MoneyHunterApplication;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListFriendsAdapter;
import com.vn.cooperate.moneyhunter.connect.FriendsConnect;
import com.vn.cooperate.moneyhunter.model.FriendModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class FriendsFragment extends Fragment implements OnClickListener {
	private TextView tvNumberFriend;
	private TextView tvNumberFriend2;
	private GridView grdFriends;
	private ListFriendsAdapter mAdapter;
	private ArrayList<FriendModel> listFriends;
	private MainActivity homeActivity;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		homeActivity = (MainActivity)activity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		FriendsConnect.getListFriend("14", listener);
		View view = inflater.inflate(R.layout.fragment_friend_list, container,
				false);
		tvNumberFriend = (TextView) view.findViewById(R.id.number_friend);
		tvNumberFriend2 = (TextView) view
				.findViewById(R.id.number_friend_level2);
		grdFriends = (GridView) view.findViewById(R.id.grd_friends);
		listFriends = new ArrayList<FriendModel>();
		mAdapter = new ListFriendsAdapter(getActivity(), listFriends);
		grdFriends.setAdapter(mAdapter);
		grdFriends.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FriendModel model = new FriendModel();
				model = listFriends.get(position);
				String userId = new MoneySharedPreferences(getActivity()).getUserID(getActivity());
				String  friendId = model.getId();
				String avatar = model.getAvatar();
				String createDate = model.getCreateDate();
				homeActivity.changeFragment(new DetailFriendFragment(userId, friendId, avatar,createDate),false);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {

	}

	ConnectApiListener listener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(final JSONObject data) {
			final int hasFriend = 1;
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						if (data.getString("resultCode").equals("" + hasFriend)) {
							tvNumberFriend.setText(data
									.getString("numOfFriend"));
							tvNumberFriend2.setText(data
									.getString("numOfSecondFriend"));
							JSONArray jArray = data.getJSONArray("listFriend");
							for (int i = 0; i < jArray.length(); i++) {
								FriendModel model = new FriendModel();
								JSONObject objFriend = jArray.getJSONObject(i);
								model.setId(objFriend.getString("friendId"));
								model.setAvatar(objFriend.getString("avatarUrl"));
								model.setDisplayName(objFriend
										.getString("name"));
								model.setId(objFriend.getString("friendId"));
								model.setCreateDate(objFriend.getString("createdDate"));
								model.setNumberFriend(objFriend
										.getString("numOfFriend"));
								listFriends.add(model);
							}
							mAdapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}

		@Override
		public void connectError() {

		}
	};
}
