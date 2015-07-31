package com.vn.cooperate.moneyhunter.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.vn.cooperate.moneyhunter.model.AppModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.ConnectSupport;

public class AppConnect {
	 static String GET_LIST_AD_APP = "http://prosoftforlife.com/moneyhunter/getListAdApp.php";

	 
	 public static void getListADAPP(int start,int num,int userId,ConnectApiListener listener)
	{
		num = start+num;
		
		Log.e("Start", start+"end "+num);
		ConnectSupport cnn = new ConnectSupport();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(cnn.getParam("start", start+""));
		param.add(cnn.getParam("end", num+""));
		param.add(cnn.getParam("user_id", userId+""));
		cnn.getJSONFromUrl(GET_LIST_AD_APP, param, listener);
	}
	
	public static List<AppModel> getListAdAppFromJson(JSONObject data)
	{
		List<AppModel> listData = new ArrayList<AppModel>();
		try {
			int requestCode = data.getInt("resultCode");
			if(requestCode==1)
			{
				JSONArray jsonArray = data.getJSONArray("listApp");
				for(int i =0;i<jsonArray.length();i++)
				{
					JSONObject subdata = (JSONObject) jsonArray.get(i);
					AppModel temp = new AppModel();
					temp.setAppId(subdata.getInt("appId"));
					temp.setAppName(subdata.getString("appName"));
					temp.setAvatarUrl(subdata.getString("avatarUrl"));
					temp.setDescription(subdata.getString("description"));
					temp.setAppPackage(subdata.getString("package"));
					listData.add(temp);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listData;
	}
}
