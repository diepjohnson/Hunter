package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListAdAppAdapter;
import com.vn.cooperate.moneyhunter.connect.AppConnect;
import com.vn.cooperate.moneyhunter.model.AppModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

public class ListAdAppFragment extends Fragment {

	static Activity mActivity; 
	LayoutInflater inflater;
	ListView lvListAdApp;
	List<AppModel> listApp = new ArrayList<AppModel>();
	int start=0;
	int number =10;
	Boolean isScroll =false;
	Boolean isStartScroll =false;
	ListAdAppAdapter adapter;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_list_ad_app, container, false);
		lvListAdApp = (ListView) v.findViewById(R.id.lvListAdApp);
		
		adapter = new ListAdAppAdapter (listApp,mActivity.getBaseContext());
		lvListAdApp.setAdapter(adapter);
		AppConnect.getListADAPP(start, number, getListAppListener);
		
		//su kien keo den cuoi cua list de load them du lieu
		lvListAdApp.setOnScrollListener(new OnScrollListener() {
					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						
						if (isStartScroll&&!isScroll &&(firstVisibleItem + visibleItemCount) == totalItemCount) {
							Log.e(start+"START______________________________", start+"");
							if (start !=-1) {
								//List<ClipModel> temp =getDataByPage();
								
								AppConnect.getListADAPP(start, number, getListAppListener);
								isScroll = true;
							}
						}
						
					}
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {}
				});
		
		return v;
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
					if(!isScroll)
					{
						if(list.size()>0)
						{
						
						listApp.addAll(list);
						adapter.notifyDataSetChanged();
						start+= list.size();
						isStartScroll=true;
						}
					}
					else
					{
						if(list.size()>0)
						{
						listApp.addAll(list);
						adapter.notifyDataSetChanged();
						start+= list.size();
						}
						else
						{
							start=-1;
						}
						isScroll=false;
					}
					
				}
			});
	
		}
		
		@Override
		public void connectError() {
			// TODO Auto-generated method stub
			
		}
	};
	
}
