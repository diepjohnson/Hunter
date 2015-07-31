package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class GCMConnect {
	 static String REGIST_GCM = "http://prosoftforlife.com/moneyhunter/registGCM.php";
	 static String SENDER_ID="916878944429";
	 public static void registGCM(String regId, ConnectApiListener listener){
			ConnectSupport cnn = new ConnectSupport();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(ConnectSupport.getParam("reg_id",regId));
			cnn.getJSONFromUrl(REGIST_GCM, params, listener);
		} 
	 

	 
	 public static void initGCM(Activity mActivity) {
			try {
				GCMRegistrar.checkDevice(mActivity);
				GCMRegistrar.checkManifest(mActivity);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			String regId = GCMRegistrar.getRegistrationId(mActivity);
			
			if (regId.equals("")) {
				GCMRegistrar.register(mActivity, SENDER_ID);
				Log.e("stk", "SohaSDK; GCM registering device..");
			} else {
				
				Log.e("stk", "SohaSDK; GCM already registered device; regId=" + regId);
			}
		}
}
