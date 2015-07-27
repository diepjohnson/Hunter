package com.vn.cooperate.moneyhunter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

import android.util.Log;

public class ConnectSupport {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public ConnectSupport() {

	}

	public void getJSONFromUrl(final String url,
			final List<NameValuePair> params, final ConnectApiListener listener) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					Log.e("URL", url);
					httpPost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					// httpPost.setEntity(new UrlEncodedFormEntity(params));

					
					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

				} catch (Exception e) {
					e.printStackTrace();
					listener.connectError();
					return;
				}

				try {

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "n");
					}
					is.close();
					json = sb.toString();
					// Logger.e(json);
				} catch (Exception e) {
					Log.e("Buffer Error",
							"Error converting result: " + e.toString());
					listener.connectError();
					return;
				}

				// try parse the string to a JSON object
				try {
					Log.e("_responce", json);
					jObj = new JSONObject(json);
				} catch (JSONException e) {
					
					listener.connectError();
					return;
				}

				listener.connectSucessfull(jObj);
			}
		});
		t.start();
		// Making HTTP request

	}
	

	public static NameValuePair getParam(final String name, final String value) {
		NameValuePair data = new NameValuePair() {

			@Override
			public String getValue() {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return name;
			}
		};

		return data;
	}
}
