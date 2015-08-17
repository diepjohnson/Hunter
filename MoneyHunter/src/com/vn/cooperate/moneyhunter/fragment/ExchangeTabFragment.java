package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.vn.cooperate.moneyhunter.MainActivity;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListExchangeAdapter;
import com.vn.cooperate.moneyhunter.connect.TransactionConnect;
import com.vn.cooperate.moneyhunter.model.TransactionModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

public class ExchangeTabFragment extends BaseFragment {
	private ListView lvReceice;
	private ListExchangeAdapter mAdapter;
	private ArrayList<TransactionModel> list;
	int start = 0;
	int number = 10;
	int USER_ID = 2;
	Boolean isScroll = false;
	Boolean isStartScroll = false;
	
	MainActivity mActivity;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity =(MainActivity) activity;
	}
	private ConnectApiListener listener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(final JSONObject data)
				throws JSONException {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mActivity.hideLoadingMessage();
					try {
						parseJSONData(data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				private void parseJSONData(JSONObject data)
						throws JSONException {
					if (data.getString("errorCode").equals("0")
							&& data.getString("resultCode").equals("1")) {
						JSONArray jArray = data.getJSONArray("listTransaction");
						for (int i = 0; i < jArray.length(); i++) {
							TransactionModel model = new TransactionModel();
							JSONObject object = jArray.getJSONObject(i);
							model.setmCoin(object.getString("coin"));
							model.setmDate(object.getString("date"));
							model.setType(object.getString("agentType"));
							model.setmCard(object.getString("cardCode"));
							list.add(model);
						}
						if (!isScroll) {
							if (list.size() > 0) {
								mAdapter.notifyDataSetChanged();
								start += list.size();
								isStartScroll = true;
							}
						} else {
							if (list.size() > 0) {
								mAdapter.notifyDataSetChanged();
								start += list.size();
							}else{
								start = -1;
							}
							isScroll = false;
						}

					}
				}

			});
		}

		@Override
		public void connectError() {
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mActivity.hideLoadingMessage();
				}
			});
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TransactionConnect.getListADAPP(start, number, "-1", "14", listener);
		View view = inflater.inflate(R.layout.fragment_tab, container, false);
		lvReceice = (ListView) view.findViewById(R.id.lv_transaction);
		list = new ArrayList<TransactionModel>();
		mAdapter = new ListExchangeAdapter(list, getActivity());
		lvReceice.setAdapter(mAdapter);
		lvReceice.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (isStartScroll
						&& !isScroll
						&& (firstVisibleItem + visibleItemCount) == totalItemCount) {

					if (start != -1) {
						// List<ClipModel> temp =getDataByPage();
						mActivity.showLoadingMessage();
						TransactionConnect.getListADAPP(start, number, "-1",
								"12", listener);
						isScroll = true;
					}
				}

			}
		});
		return view;
	}
}
