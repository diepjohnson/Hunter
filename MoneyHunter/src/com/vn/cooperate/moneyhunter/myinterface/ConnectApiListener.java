package com.vn.cooperate.moneyhunter.myinterface;

import org.json.JSONException;
import org.json.JSONObject;


public interface ConnectApiListener {
void connectError();
void connectSucessfull(JSONObject data) throws JSONException;
}
