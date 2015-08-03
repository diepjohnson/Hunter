package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		FriendsConnect.getListFriend(new MoneySharedPreferences(getActivity())
//				.getUserID(getActivity()), listener);
		FriendsConnect.getListFriend("14", listener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend_list, container,
				false);
		tvNumberFriend = (TextView) view.findViewById(R.id.number_friend);
		tvNumberFriend2 = (TextView) view
				.findViewById(R.id.number_friend_level2);
		grdFriends = (GridView) view.findViewById(R.id.grd_friends);
		listFriends = new ArrayList<FriendModel>();
		mAdapter = new ListFriendsAdapter(getActivity(), listFriends);
		grdFriends.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
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

							JSONArray jArray = data.getJSONArray("listFriend");
							for (int i = 0; i < jArray.length(); i++) {
								FriendModel model = new FriendModel();
								JSONObject objFriend = jArray.getJSONObject(i);
								model.setAvatar(objFriend.getString("avatar"));
								model.setDisplayName(objFriend.getString("name"));
								model.setId(objFriend.getString("friendId"));
								model.setNumberFriend(objFriend.getString("numOfFriend"));
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
