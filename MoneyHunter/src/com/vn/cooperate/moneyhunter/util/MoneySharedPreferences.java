package com.vn.cooperate.moneyhunter.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MoneySharedPreferences {
	private SharedPreferences mPreferences;
	public static final String APP_NAME = "MONEY_HUNTER";
	public static final String USER_ID = "UID";
	public static final String INVITE_CODE = "ICODE";
	public static final String ACCESS_TOKEN = "ATOKEN";

	public MoneySharedPreferences(Context context) {
		mPreferences = context.getSharedPreferences(APP_NAME,
				Context.MODE_PRIVATE);
	}

	public String getUserID(Context context) {
		return mPreferences.getString("", "");
	}

	public void setUserID(String userId) {
		Editor editor = mPreferences.edit();
		editor.putString(USER_ID, userId);
		editor.commit();
	}

	public String getInviteCode(Context context) {
		return mPreferences.getString("", "");
	}

	public void setInviteCode(String inviteCode) {
		Editor editor = mPreferences.edit();
		editor.putString(INVITE_CODE, inviteCode);
		editor.commit();
	}

	public String getAccessToken(Context context) {
		return mPreferences.getString("", "");
	}

	public void setAccessToken(String accessToken) {
		Editor editor = mPreferences.edit();
		editor.putString(ACCESS_TOKEN, accessToken);
		editor.commit();
	}

}
