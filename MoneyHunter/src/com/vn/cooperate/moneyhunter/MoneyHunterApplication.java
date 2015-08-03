package com.vn.cooperate.moneyhunter;

import android.app.Application;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;

public class MoneyHunterApplication extends Application{
	public static int withScreen;
	public static int heightScreen;
	

	public static int getWithScreen() {
		return withScreen;
	}

	public static void setWithScreen(int withScreen) {
		MoneyHunterApplication.withScreen = withScreen;
	}

	public static int getHeightScreen() {
		return heightScreen;
	}

	public static void setHeightScreen(int heightScreen) {
		MoneyHunterApplication.heightScreen = heightScreen;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		AjaxCallback.setNetworkLimit(8);
		  BitmapAjaxCallback.setIconCacheLimit(40);
		  BitmapAjaxCallback.setCacheLimit(40);
		  BitmapAjaxCallback.setPixelLimit(500 * 500);
		  BitmapAjaxCallback.setMaxPixelLimit(8000000);
		  super.onCreate();
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		BitmapAjaxCallback.clearCache();
		super.onLowMemory();
	}
}
