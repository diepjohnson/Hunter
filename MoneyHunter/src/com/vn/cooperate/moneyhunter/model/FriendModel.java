package com.vn.cooperate.moneyhunter.model;

public class FriendModel {
	public String id;
	public String createDate;
	public String displayName;
	public String getDisplayName() {
		return displayName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String numberFriend;
	public String avatar;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumberFriend() {
		return numberFriend;
	}
	public void setNumberFriend(String numberFriend) {
		this.numberFriend = numberFriend;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	

}
