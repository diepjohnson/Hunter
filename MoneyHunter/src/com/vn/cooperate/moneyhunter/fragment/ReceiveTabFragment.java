package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListReceiveAdapter;
import com.vn.cooperate.moneyhunter.connect.TransactionConnect;
import com.vn.cooperate.moneyhunter.model.TransactionModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

public class ReceiveTabFragment extends BaseFragment {
	private ListView lvReceice;
	private ListReceiveAdapter mAdapter;
	private ArrayList<TransactionModel> list;
	private ConnectApiListener listener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(final JSONObject data)
				throws JSONException {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
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
							model.setType(object.getString("type"));
							if (!object.getString("type").equals("5")) {
								model.setmName(object.getString("appName"));
								model.setFromUser(object
										.getString("fromUserName"));
							}
							list.add(model);
						}
						mAdapter.notifyDataSetChanged();
					}
				}

			});
		}

		@Override
		public void connectError() {

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TransactionConnect.getListADAPP(0, 10, "1", "14", listener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab, container, false);
		lvReceice = (ListView) view.findViewById(R.id.lv_transaction);
		list = new ArrayList<TransactionModel>();
		mAdapter = new ListReceiveAdapter(list, getActivity());
		lvReceice.setAdapter(mAdapter);
		return view;
	}
}
