package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.vn.cooperate.moneyhunter.MainActivity;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.ListAdAppAdapter;
import com.vn.cooperate.moneyhunter.connect.AppConnect;
import com.vn.cooperate.moneyhunter.fragment.dialog.DialogMessage;
import com.vn.cooperate.moneyhunter.model.AppModel;
import com.vn.cooperate.moneyhunter.model.UserModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.DialogUtils;

public class ListAdAppFragment extends Fragment {

	static MainActivity mActivity; 
	LayoutInflater inflater;
	ListView lvListAdApp;
	List<AppModel> listApp = new ArrayList<AppModel>();
	int start=0;
	int number =10;
	int USER_ID =2;
	Boolean isScroll =false;
	Boolean isStartScroll =false;
	ListAdAppAdapter adapter;
	UserModel user;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	
  ConnectApiListener getCoinListener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(final JSONObject data) throws JSONException {
			// TODO Auto-generated method stub
			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stubint 
					int errorCode;
					try {
						errorCode = data.getInt("errorCode");
					
					if(errorCode==0)
					{
						int userError = data.getInt("userError");
						if(userError==0)
						{
							int coin = data.getInt("coin");
							mActivity.showMessage("Message", mActivity.getString(R.string.getCoinSuccess)+coin);
						}
						
						if(userError==1)
						{
					
							mActivity.showMessage("Message", mActivity.getString(R.string.actionfail));
						}
						
						if(userError==2)
						{
					
							mActivity.showMessage("Message", mActivity.getString(R.string.actionErr));
						}
						}
					else
					{
						mActivity.showMessage("Message", mActivity.getString(R.string.badTransaction));
					}
					
					
					}
				
					
					 catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						mActivity.showMessage("Message", mActivity.getString(R.string.error));
					}
				}
			});
		}
		
		@Override
		public void connectError() {
			// TODO Auto-generated method stub
        handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				mActivity.showMessage("Message", mActivity.getString(R.string.connectErr));
				}
			});
		}
	};
	
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
		
		 user = UserModel.getUserInfor(mActivity);
//		if(user.getUserId()!=null&& !user.getUserId().equals(""))
//		{
			lvListAdApp = (ListView) v.findViewById(R.id.lvListAdApp);
			
			adapter = new ListAdAppAdapter (listApp,mActivity.getBaseContext(),getCoinListener);
			lvListAdApp.setAdapter(adapter);
			DialogUtils.vDialogLoadingShowProcessing(mActivity, false);
			AppConnect.getListADAPP(start, number,user.getUserId(), getListAppListener);
			
			//su kien keo den cuoi cua list de load them du lieu
			lvListAdApp.setOnScrollListener(new OnScrollListener() {
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem,
								int visibleItemCount, int totalItemCount) {
							
							if (isStartScroll&&!isScroll &&(firstVisibleItem + visibleItemCount) == totalItemCount) {
								
								if (start !=-1) {
									//List<ClipModel> temp =getDataByPage();
									DialogUtils.vDialogLoadingShowProcessing(mActivity, false);
									AppConnect.getListADAPP(start, number,user.getUserId(), getListAppListener);
									isScroll = true;
								}
							}
							
						}
						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {}
					});
			
//		}
		
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
					DialogUtils.vDialogLoadingDismiss();
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

			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					DialogUtils.vDialogLoadingDismiss();
				}
			});
		}
	};
	
}
