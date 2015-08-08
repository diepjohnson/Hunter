package com.vn.cooperate.moneyhunter.model;

public class EncryptDataModel {
	String encryptData;
	int randKey;
	int flag;
	int userId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEncryptData() {
		return encryptData;
	}
	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}
	public int getRandKey() {
		return randKey;
	}
	public void setRandKey(int randKey) {
		this.randKey = randKey;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	

}
