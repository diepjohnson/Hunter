package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class FriendsConnect {
	static String URL = "http://prosoftforlife.com/moneyhunter/makeFriend.php";
	public static void makeFriend(String userId, String inviteCode, ConnectApiListener listener){
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(ConnectSupport.getParam("user_id", userId));
		params.add(ConnectSupport.getParam("invite_code", inviteCode));
		cnn.getJSONFromUrl(URL, params, listener);
	} 
}
