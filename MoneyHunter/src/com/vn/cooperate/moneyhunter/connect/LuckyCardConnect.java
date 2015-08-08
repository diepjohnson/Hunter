package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class LuckyCardConnect {
	 static String GET_DAILY_REWARD = "http://prosoftforlife.com/moneyhunter/getDailyReward.php";
	 
	 public static void getDailyReward(String string,ConnectApiListener listener)
	{
			Log.e("hehehehe++++++++++", "____");
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Log.e("userrID______", string);
		param.add(cnn.getParam("user_id", string+""));
		cnn.getJSONFromUrl(GET_DAILY_REWARD, param, listener);
	}
}
