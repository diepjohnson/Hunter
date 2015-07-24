package com.vn.cooperate.moneyhunter.adater;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.model.AppModel;



public class ListAdAppAdapter extends BaseAdapter {
	List<AppModel> listApp;
	Context mContext;
	LayoutInflater inflater;
	


	public  ListAdAppAdapter()
	{
		
	}
	AQuery listAQ;
	public  ListAdAppAdapter(List<AppModel> data,Context mContext)
	{
		listApp=data;
		this.mContext = mContext;
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		listAQ = new AQuery(mContext);
		
//	    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
//        .threadPriority(Thread.NORM_PRIORITY - 2)
//        .denyCacheImageMultipleSizesInMemory()
//        .discCacheFileNameGenerator(new Md5FileNameGenerator())
//        .tasksProcessingOrder(QueueProcessingType.LIFO)
//        .enableLogging()
//        .build();
//		options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.ic_photo_loading)
//				.showImageForEmptyUri(R.drawable.ic_photo_corrupt)
//				.showImageOnFail(R.drawable.ic_photo_corrupt)
//				.cacheInMemory().cacheOnDisc().build();
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);
//		
//		imgLoadingListener  = new ImageLoadingListener() {
//			
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {}
//			
//			@Override
//			public void onLoadingFailed(String imageUri, View view,FailReason failReason) {}
//			
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {}
//			
//			@Override
//			public void onLoadingCancelled(String imageUri, View view) {}
//		};
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


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		Holder mHolder;
		AppModel data = listApp.get(position);
		if(convertView==null)
		{
			v= inflater.inflate(R.layout.item_ad_app, parent, false);
			mHolder = new Holder();
			mHolder.imgAvatar = (ImageView) v.findViewById(R.id.imgAppAvatar);
		//	mHolder.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
			mHolder.tvAppName = (TextView) v.findViewById(R.id.tvAppName);
			//mHolder.tvCreatedDate = (TextView)v.findViewById(R.id.tvCreatedDate);
			v.setTag(mHolder);
		}
		else
		{
			v = convertView;
			mHolder = (Holder) v.getTag();
		}
		
		mHolder.tvAppName.setText(data.getAppName());
		//mHolder.tvDescription.setText(data.getDescription());
	//	mHolder.tvCreatedDate.setText(data.getCreatedDate().toString());
		Log.e("Icon ", data.getAvatarUrl());
		AQuery aq = listAQ.recycle(v);
		aq.id(mHolder.imgAvatar).image(data.getAvatarUrl(), true, true, 0, 0, null, 0, 1.0f);
	
		//imageLoader.displayImage(data.getAvatarUrl(), mHolder.imgAvatar, options, imgLoadingListener);
		//PhotoLoader.loadPhotoOrigin(data.getAvatarUrl(),mHolder.imgAvatar, mContext);
		return v;
	}
	
	class Holder
	{
		ImageView imgAvatar;
		TextView tvAppName;
		//TextView tvDescription;
		//TextView tvCreatedDate;
	}
	

}
