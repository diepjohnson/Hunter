package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

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
		FriendsConnect.getListFriend(new MoneySharedPreferences(getActivity()).getUserID(getActivity()), listener);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
		tvNumberFriend = (TextView) view.findViewById(R.id.number_friend);
		tvNumberFriend2 = (TextView) view.findViewById(R.id.number_friend_level2);
		grdFriends = (GridView) view.findViewById(R.id.grd_friends);
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
		public void connectSucessfull(JSONObject data) {
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
				}
			});
		}
		
		@Override
		public void connectError() {
			
		}
	};
}
