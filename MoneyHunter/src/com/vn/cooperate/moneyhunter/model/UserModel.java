package com.vn.cooperate.moneyhunter.model;

import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

import android.content.Context;

public class UserModel {
	public String userId;
	public String displayName;
	public int uCoin;
	public String inviteCode;
	public String accessToken;
	public String avatar;
	public int vipLevel;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getuCoin() {
		return uCoin;
	}
	public void setuCoin(int uCoin) {
		this.uCoin = uCoin;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public static UserModel getUserInfor(Context context){
		UserModel model = new UserModel();
		MoneySharedPreferences moneySharedPreferences = new MoneySharedPreferences(context);
		model.setAccessToken(moneySharedPreferences.getAccessToken(context));
		model.setAvatar("");
		model.setDisplayName("");
		model.setInviteCode(moneySharedPreferences.getInviteCode(context));
		model.setVipLevel(0);
		model.setUserId(moneySharedPreferences.getUserID(context));
		model.setuCoin(0);
		return model;
		
	} 

}
