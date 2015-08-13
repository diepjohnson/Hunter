package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class TransactionConnect {
	static String URL_TRANSACTION = "http://prosoftforlife.com/moneyhunter/getListTransaction.php";
	 public static void getListADAPP(int start,int num,String flag,String userId,ConnectApiListener listener)
		{
			num = start+num;
			
			Log.e("Start", start+"end "+num);
			ConnectSupport cnn = new ConnectSupport();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(ConnectSupport.getParam("start", start+""));
			param.add(ConnectSupport.getParam("end", num+""));
			param.add(ConnectSupport.getParam("flag", flag));
			param.add(ConnectSupport.getParam("user_id", userId+""));
			cnn.getJSONFromUrl(URL_TRANSACTION, param, listener);
		}
}
