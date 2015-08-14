package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListFriendsAdapter;
import com.vn.cooperate.moneyhunter.connect.FriendsConnect;
import com.vn.cooperate.moneyhunter.model.FriendModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

public class DetailFriendFragment extends Fragment {
	private String userId;
	private String friendId;
	private String urlAatar;
	private String createDate;
	private ImageView friendAvatar;
	private TextView dateCreate;
	private TextView moneyApp;
	private TextView moneyFriend;
	private GridView grdFriends;
	private ListFriendsAdapter mAdapter;
	private ArrayList<FriendModel> listFriends;
	private TextView titleIntro;

	public DetailFriendFragment(String userId, String friendId, String urlAvatar, String createDate) {
		super();
		this.userId = userId;
		this.friendId = friendId;
		this.urlAatar = urlAvatar;
		this.createDate = createDate;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		FriendsConnect.getFriendinfo("14", "4", listener);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_detail, container, false);
		AQuery aQ = new AQuery(getActivity());
		titleIntro = (TextView) view.findViewById(R.id.title_intro);
		friendAvatar = (ImageView) view.findViewById(R.id.img_avatar_fr);
		dateCreate = (TextView) view.findViewById(R.id.date_create);
		moneyApp = (TextView) view.findViewById(R.id.money_from_app);
		moneyFriend = (TextView) view.findViewById(R.id.money_from_friend);
		grdFriends = (GridView) view.findViewById(R.id.grd_friends);
		listFriends = new ArrayList<FriendModel>();
		mAdapter = new ListFriendsAdapter(getActivity(), listFriends);
		grdFriends.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		aQ.id(R.id.img_avatar_fr).image(urlAatar, true, true, 0, 0, null, 0,
				1.0f);
		return view;
	}

	ConnectApiListener listener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(final JSONObject data)
				throws JSONException {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						AQuery aQ = new AQuery(getActivity());
						aQ.id(friendAvatar).image(urlAatar, true, true, 0, 0, null, 0, 1);
						dateCreate.setText(createDate);
						moneyApp.setText(data.getString("installApp"));
						moneyFriend.setText(data.getString("profitFromFriend"));
						if(data.getInt("isHaveFriend") == 1){
							JSONArray jArray = data.getJSONArray("listfriend");
							titleIntro.setText(getActivity().getString(R.string.introduced_number) + jArray.length());
							Log.e("data", data.toString());
							for (int i = 0; i < jArray.length(); i++) {
								FriendModel model = new FriendModel();
								JSONObject objFriend = jArray.getJSONObject(i);
								model.setId(objFriend.getString("friendId"));
								model.setAvatar(objFriend.getString("avatarUrl"));
								model.setDisplayName(objFriend.getString("name"));
								model.setId(objFriend.getString("friendId"));
								model.setNumberFriend(objFriend
										.getString("numOfFriend"));
								listFriends.add(model);
							}
							mAdapter.notifyDataSetChanged();

						}else{
							titleIntro.setText(getActivity().getString(R.string.introduced_number) + 0);
						} 
						
					} catch (Exception e) {
					}

				}
			});
		}

		@Override
		public void connectError() {

		}
	};
}
