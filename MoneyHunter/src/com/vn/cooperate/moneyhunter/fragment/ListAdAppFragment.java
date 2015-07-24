package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adater.ListAdAppAdapter;
import com.vn.cooperate.moneyhunter.connect.AppConnect;
import com.vn.cooperate.moneyhunter.model.AppModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class ListAdAppFragment extends Fragment {

	static Activity mActivity; 
	LayoutInflater inflater;
	ListView lvListAdApp;
	List<AppModel> listApp = new ArrayList<AppModel>();
	int start=0;
	int number =10;
	
	ListAdAppAdapter adapter;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	Handler handle = new Handler();
	
	ConnectApiListener getListAppListener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(final JSONObject data) {
			// TODO Auto-generated method stub
	
		
			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					List<AppModel> list = 	AppConnect.getListAdAppFromJson(data);
					if(list.size()>0)
					{
					
					listApp.addAll(list);
					adapter.notifyDataSetChanged();

					}
				}
			});
	
		}
		
		@Override
		public void connectError() {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_list_ad_app, container, false);
		lvListAdApp = (ListView) v.findViewById(R.id.lvListAdApp);
		
		adapter = new ListAdAppAdapter (listApp,mActivity.getBaseContext());
		lvListAdApp.setAdapter(adapter);
		AppConnect.getListADAPP(start, number, getListAppListener);
		
		//connect.getJSONFromUrl(API.GET_LIST_AD_APP, params, listener)
		
		return v;
	}
	
	
}
