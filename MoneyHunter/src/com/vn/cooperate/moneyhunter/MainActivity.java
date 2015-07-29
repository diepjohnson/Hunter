package com.vn.cooperate.moneyhunter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.slidingmenu.lib.SlidingMenu;
import com.vn.cooperate.moneyhunter.connect.UserConnect;
import com.vn.cooperate.moneyhunter.fragment.ListAdAppFragment;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private SlidingMenu slideMenu;
	private ImageView imgMenu;
	private Boolean isShowMenu = false;
	private TextView tvMonetize;
	private TextView tvExchange;
	private TextView tvInvite;
	private TextView tvFriends;
	private TextView tvStatistic;
	private TextView tvLucky;
	private TextView tvTerms;
	private TextView tvGuide;
	private TextView tvSetting;
	private TextView tvContacts;

	private LoginButton btnLogin;
	private CallbackManager managerCallback;
	private Handler handler;
	private MoneySharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		managerCallback = CallbackManager.Factory.create();
		mPreferences = new MoneySharedPreferences(this);
		initUIControl();
		getKeyHash();
	}

	private void getKeyHash() {
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo(
					"com.vn.cooperate.moneyhunter",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				Log.e("hash key", something);
			}
		} catch (NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}
	}

	private void initUIControl() {
		slideMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
		imgMenu = (ImageView) findViewById(R.id.imgMenu);
		imgMenu.setOnClickListener(this);
		tvMonetize = (TextView) findViewById(R.id.menu_download);
		tvMonetize.setOnClickListener(this);
		btnLogin = (LoginButton) findViewById(R.id.login_button);
		btnLogin.setPublishPermissions("user_friends");// public_profile
														// //user_status
														// //publish_actions
		btnLogin.setPublishPermissions("public_profile");
		btnLogin.setPublishPermissions("publish_actions");
		handler = new Handler();
		btnLogin.registerCallback(managerCallback,
				new FacebookCallback<LoginResult>() {

					@Override
					public void onSuccess(LoginResult result) {
						Log.e("USERID", "is "
								+ result.getAccessToken().getUserId());
						GraphRequest request = GraphRequest.newMeRequest(
								result.getAccessToken(),
								new GraphJSONObjectCallback() {

									@Override
									public void onCompleted(JSONObject object,
											GraphResponse response) {
										Log.d("response",
												"response" + object.toString());

										try {
											final String name = object
													.getString("name");
											final String facebookId = object
													.getString("id");
											final String email = object
													.getString("email");
											handler.post(new Runnable() {

												@Override
												public void run() {
													UserConnect.logon(name,
															email, facebookId,
															"a1b2c3d4", 1,
															logonListener);

												}
											});

										} catch (JSONException e) {
											e.printStackTrace();
										}

									}
								});
						Bundle parameters = new Bundle();
						parameters
								.putString("fields",
										"id,name,email,gender,birthday,picture.width(300)");
						request.setParameters(parameters);
						request.executeAsync();
					}

					@Override
					public void onError(FacebookException error) {
						Log.e("Error", "is " + error.toString());
					}

					@Override
					public void onCancel() {

					}
				});

		slideMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
		imgMenu = (ImageView) findViewById(R.id.imgMenu);
		imgMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isShowMenu) {
					slideMenu.showBehind();
					isShowMenu = true;
				} else {
					slideMenu.showAbove();
					isShowMenu = false;
				}
			}
		});

		addFragment(new ListAdAppFragment());

	}

	Fragment curFragment;

	public void changeFragment(Fragment fragment) {
		try {
			curFragment = fragment;
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			Log.e("ERR change frag ", "" + e.getMessage());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgMenu:
			if (!isShowMenu) {
				slideMenu.showBehind();
				isShowMenu = true;
			} else {
				slideMenu.showAbove();
				isShowMenu = false;
			}
			break;
		case R.id.menu_download:
			Toast.makeText(MainActivity.this, "tai app kiem tien",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	public void addFragment(Fragment fragment) {
		try {
			curFragment = fragment;
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.add(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			Log.e("ERR change frag ", "" + e.getMessage());
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		managerCallback.onActivityResult(requestCode, resultCode, data);
	}

	ConnectApiListener logonListener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(JSONObject data) {
			handleLogin(data);
		}

		@Override
		public void connectError() {

		}
	};

	protected void handleLogin(JSONObject data) {
		int returnCode = -1;
		try {
			returnCode = data.getInt("errorCode");
			JSONObject user = data.getJSONObject("user");
			String userId = user.getString("userId");
			mPreferences.setUserID(userId);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (returnCode != 0) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(MainActivity.this, "Error Login",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(MainActivity.this, " Login successfuly",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
