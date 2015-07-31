package com.vn.cooperate.moneyhunter.adater;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.connect.UserConnect;
import com.vn.cooperate.moneyhunter.model.AppModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.DialogUtils;



public class ListAdAppAdapter extends BaseAdapter {
	List<AppModel> listApp;
	Context mContext;
	LayoutInflater inflater;
	int curItem =0;
	AQuery listAQ;
	int USER_ID =2;

	public  ListAdAppAdapter()
	{
		super();
	}
	
	public  ListAdAppAdapter(List<AppModel> data,Context mContext)
	{
		listApp=data;
		this.mContext = mContext;
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		listAQ = new AQuery(mContext);
		
	}
    Handler handle = new Handler();
	ConnectApiListener listener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(final JSONObject data) {
			// TODO Auto-generated method stub
			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						int errorCode = data.getInt("errorCode");
						switch (errorCode) {
						case 0:
		                listApp.remove(curItem);
		                notifyDataSetChanged();
		                Toast.makeText(mContext, "Nhan tien thanh cong", 1000).show();
							break;
						case 1:

							break;
						case 2:

							break;

						default:
							break;
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					// DialogUtils.vDialogLoadingDismiss();
				}
			});
			
			
		}
		
		@Override
		public void connectError() {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		final Holder mHolder;
		final AppModel data = listApp.get(position);
		if(convertView==null)
		{
			v= inflater.inflate(R.layout.item_ad_app, parent, false);
			mHolder = new Holder();
			mHolder.imgAvatar = (ImageView) v.findViewById(R.id.imgAppAvatar);
		//	mHolder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
			mHolder.tvAppName = (TextView) v.findViewById(R.id.tvAppName);
			mHolder.btnInstal = (Button) v.findViewById(R.id.btnInstall);
			v.setTag(mHolder);
		}
		else
		{
			v = convertView;
			mHolder = (Holder) v.getTag();
		}
		
		mHolder.tvAppName.setText(data.getAppName());
		
		Log.e("Icon ", data.getAvatarUrl());
		AQuery aq = listAQ.recycle(v);
		aq.id(mHolder.imgAvatar).image(data.getAvatarUrl(), true, true, 0, 0, null, 0, 1.0f);
	
		
		if (mContext.getPackageManager().getLaunchIntentForPackage(data.getAppPackage()) == null)
		{
			mHolder.btnInstal.setText(mContext.getString(R.string.install));
			mHolder.btnInstal.setBackgroundResource(R.drawable.grey_button2);
			mHolder.btnInstal.setTextColor(mContext.getResources().getColor(R.color.grey));
			mHolder.isIntalled = false;
		}
		else
		{
			mHolder.btnInstal.setText(mContext.getString(R.string.getCoin));
			mHolder.btnInstal.setBackgroundResource(R.drawable.full_orange_button);
			mHolder.btnInstal.setTextColor(mContext.getResources().getColor(R.color.white));
			mHolder.isIntalled = true;
		}
		
		mHolder.btnInstal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				curItem = position;
				AppModel data = listApp.get(position);
				if(!mHolder.isIntalled)
				{
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+data.getAppPackage()));
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					    mContext.startActivity(intent);
					} catch (android.content.ActivityNotFoundException anfe) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+data.getAppPackage()));
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						
						 mContext.startActivity(intent);
					}
				}
				else
				{
					Intent intent;
					PackageManager manager = mContext.getPackageManager();
					try {
						intent = manager.getLaunchIntentForPackage(data.getAppPackage());
						
						intent.addCategory(Intent.CATEGORY_LAUNCHER);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						 mContext.startActivity(intent);
						// DialogUtils.vDialogLoadingShowProcessing(mContext, false);
						 UserConnect.addCoinToUser(data.getAppId()+"", USER_ID+"", listener);
						 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
				
			}
		});
		return v;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listApp.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	class Holder
	{
		ImageView imgAvatar;
		TextView tvAppName;
		Button btnInstal;
		Boolean isIntalled =false;
		//TextView tvDescription;
		//TextView tvCreatedDate;
	}
	

}
