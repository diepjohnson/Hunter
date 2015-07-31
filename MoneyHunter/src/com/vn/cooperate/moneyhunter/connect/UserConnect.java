package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class UserConnect {
	static String URL = "http://prosoftforlife.com/moneyhunter/signin.php";
	static String ADD_COIN_URL="http://prosoftforlife.com/moneyhunter/addCoinByInstall.php";
	public static void logon(String name, String email, String facebookId, String deviceId, int type, ConnectApiListener listener){
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(ConnectSupport.getParam("name",name));
		params.add(ConnectSupport.getParam("facebook_account", email));
		params.add(ConnectSupport.getParam("facebook_id", facebookId));
		params.add(ConnectSupport.getParam("device_id", deviceId));
		params.add(ConnectSupport.getParam("type", ""+type));
		cnn.getJSONFromUrl(URL, params, listener);
	} 
	
	
	public static void addCoinToUser(String appId,String userId, ConnectApiListener listener)
	{
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(ConnectSupport.getParam("app_id",appId));
		params.add(ConnectSupport.getParam("user_id", userId));
		cnn.getJSONFromUrl(URL, params, listener);
	}
	
	
}
