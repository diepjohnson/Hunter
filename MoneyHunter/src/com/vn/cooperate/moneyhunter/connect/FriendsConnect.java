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
	static String URL_LIST = "http://prosoftforlife.com/moneyhunter/getListFriend.php";
	public static void getListFriend(String userId, ConnectApiListener listener){
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(ConnectSupport.getParam("user_id", userId));
		cnn.getJSONFromUrl(URL_LIST, params, listener);
	} 
	static String URL_DETAIL = "http://prosoftforlife.com/moneyhunter/getFriendInfo.php";
	public static void getFriendinfo(String userId, String friendId, ConnectApiListener listener){
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(ConnectSupport.getParam("user_id", userId));
		params.add(ConnectSupport.getParam("friend_id", friendId));
		cnn.getJSONFromUrl(URL_DETAIL, params, listener);
	}
}
