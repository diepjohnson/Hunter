package com.vn.cooperate.moneyhunter.model;

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
	public UserModel getUserInfor(Context context){
		UserModel model = new UserModel();
		model.setAccessToken(this.accessToken);
		model.setAvatar(this.avatar);
		model.setDisplayName(this.displayName);
		model.setInviteCode(this.inviteCode);
		model.setVipLevel(this.vipLevel);
		model.setUserId(this.userId);
		model.setuCoin(this.uCoin);
		return model;
		
	} 

}
