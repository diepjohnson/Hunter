package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class DetailFriendFragment extends Fragment{
	private String userId;
	private String friendId;
	private String urlAatar;
	private ImageView friendAvatar;
	private TextView dateCreate;
	private TextView moneyApp;
	private TextView moneyFriend;
	private GridView grdFriends;
	private ListFriendsAdapter mAdapter;
	private ArrayList<FriendModel> listFriends;
	public DetailFriendFragment(String userId, String friendId, String urlAvatar) {
		super();
		this.userId = userId;
		this.friendId = friendId;
		this.urlAatar = urlAvatar;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		FriendsConnect.getFriendinfo(userId, friendId, listener);
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detail, container, false);
		AQuery aQ = new AQuery(getActivity());
		friendAvatar = (ImageView) view.findViewById(R.id.img_avatar_fr);
		dateCreate = (TextView) view.findViewById(R.id.date_create);
		moneyApp = (TextView) view.findViewById(R.id.app_benefit);
		moneyFriend = (TextView) view.findViewById(R.id.friend_benefit);
		grdFriends = (GridView) view.findViewById(R.id.grd_friends);
		listFriends = new ArrayList<FriendModel>();
		mAdapter = new ListFriendsAdapter(getActivity(), listFriends);
		grdFriends.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		aQ.id(R.id.img_avatar_fr).image(urlAatar, true, true, 0, 0, null, 0, 1.0f);
		return view;
	}
	ConnectApiListener listener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(JSONObject data) throws JSONException {
			JSONArray jArray = data.getJSONArray("listFriend");
			for (int i = 0; i < jArray.length(); i++) {
				FriendModel model = new FriendModel();
				JSONObject objFriend = jArray.getJSONObject(i);
				model.setId(objFriend.getString("friendId"));
				model.setAvatar(objFriend.getString("avatar"));
				model.setDisplayName(objFriend.getString("name"));
				model.setId(objFriend.getString("friendId"));
				model.setNumberFriend(objFriend.getString("numOfFriend"));
				listFriends.add(model);
			}
			mAdapter.notifyDataSetChanged();
		}
		
		@Override
		public void connectError() {
			
		}
	};
}
