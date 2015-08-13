package com.vn.cooperate.moneyhunter.model;

import java.util.Calendar;

public class TransactionModel {
	public String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String mName;
	public String mCoin;
	public int mDrawable;
	public String mDate;
	public String mCard;
	public int agency;
	public String fromUser;

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmCoin() {
		return mCoin;
	}

	public void setmCoin(String mCoin) {
		this.mCoin = mCoin;
	}

	public int getmDrawable() {
		return mDrawable;
	}

	public void setmDrawable(int mDrawable) {
		this.mDrawable = mDrawable;
	}

	public String getmDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(mDate) * 1000);
		return cal.getTime().toString();
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmCard() {
		return mCard;
	}

	public void setmCard(String mCard) {
		this.mCard = mCard;
	}

	public int getAgency() {
		return agency;
	}

	public void setAgency(int agency) {
		this.agency = agency;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

}
