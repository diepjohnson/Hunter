package com.vn.cooperate.moneyhunter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.widget.ShareDialog;
import com.slidingmenu.lib.SlidingMenu;
import com.vn.cooperate.moneyhunter.connect.GCMConnect;
import com.vn.cooperate.moneyhunter.connect.UserConnect;
import com.vn.cooperate.moneyhunter.fragment.FriendsFragment;
import com.vn.cooperate.moneyhunter.fragment.IncomeStatisticFragment;
import com.vn.cooperate.moneyhunter.fragment.InviteFragment;
import com.vn.cooperate.moneyhunter.fragment.ListAdAppFragment;
import com.vn.cooperate.moneyhunter.fragment.LuckyCardFragment;
import com.vn.cooperate.moneyhunter.fragment.dialog.DialogMessage;
import com.vn.cooperate.moneyhunter.model.UserModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.DialogUtils;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private SlidingMenu slideMenu;
	private ImageView imgMenu;
	private Boolean isShowMenu = false;
	private TextView tvMonetize, tvExchange, tvInvite, tvFriends, tvStatistic,
			tvLucky;
	private TextView tvTerms, tvGuide, tvSetting, tvContacts, tvMessage;
	private LinearLayout lnHomeContent;
	private LoginButton btnLogin;
	private CallbackManager managerCallback;
	private ShareDialog dialog;
	private Handler handler;
	private MoneySharedPreferences mPreferences;

	private int heightScreen;
	private int widthScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		managerCallback = CallbackManager.Factory.create();
		mPreferences = new MoneySharedPreferences(this);
		saveDimensionScreenandDeviceID();
		setUpFBShare();

		initUIControl();
		getKeyHash();
		GCMConnect.initGCM(this);
		if (checkLogIn()) {
			addFragment(new ListAdAppFragment());
		}
		//DialogUtils.showDialogMessage(MainActivity.this, "Dang nhap thanh cong", true);
		//DialogUtils.vDialogLoadingShowProcessing(getBaseContext(), true);

	}

	private void setUpFBShare() {
		dialog = new ShareDialog(this);
		dialog.registerCallback(managerCallback,
				new FacebookCallback<Sharer.Result>() {

					@Override
					public void onSuccess(Result result) {

					}

					@Override
					public void onError(FacebookException error) {

					}

					@Override
					public void onCancel() {

					}

				});

	}
	static String message="";
    static String title="";
	static Boolean isPause = false;
	static Boolean isUnShowMessage = false;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPause = true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isPause = false;
		if(isUnShowMessage)
		{
			DialogMessage mesage = new DialogMessage(title, message);
			mesage.show(getSupportFragmentManager(), "");
			isUnShowMessage=false;
		}
	}
	public void showMessage(String title,String message)
	{
		if(!isPause)
		{
			DialogMessage mesage = new DialogMessage(title, message);
			mesage.show(getSupportFragmentManager(), "");
		}else
		{
			this.message = message;
			this.title = title;
			isUnShowMessage=true;
		}
		
	}
	
	
	private void saveDimensionScreenandDeviceID() {
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		heightScreen = point.y;
		widthScreen = point.x;
		MoneyHunterApplication.setHeightScreen(heightScreen);
		MoneyHunterApplication.setWithScreen(widthScreen);
		String myAndroidDeviceId = Secure.getString(getApplicationContext()
				.getContentResolver(), Secure.ANDROID_ID);
		mPreferences = new MoneySharedPreferences(MainActivity.this);
		mPreferences.setDeviceID(myAndroidDeviceId);

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
		tvInvite = (TextView) findViewById(R.id.menu_invite);
		tvInvite.setOnClickListener(this);
		tvFriends = (TextView) findViewById(R.id.menu_friend_list);
		tvFriends.setOnClickListener(this);
		tvExchange = (TextView) findViewById(R.id.menu_exchange);
		tvGuide = (TextView) findViewById(R.id.menu_usage_guide);
		tvLucky = (TextView) findViewById(R.id.menu_lucky);
		tvStatistic = (TextView) findViewById(R.id.menu_statistic);
		tvTerms = (TextView) findViewById(R.id.menu_term_policy);
		tvSetting = (TextView) findViewById(R.id.menu_setting);
		tvMessage = (TextView) findViewById(R.id.tvMessage);
		tvContacts = (TextView) findViewById(R.id.menu_contacts);
		lnHomeContent = (LinearLayout) findViewById(R.id.lnHomeContainer);
		tvContacts.setOnClickListener(this);
		tvExchange.setOnClickListener(this);
		tvGuide.setOnClickListener(this);
		tvLucky.setOnClickListener(this);
		tvSetting.setOnClickListener(this);
		tvStatistic.setOnClickListener(this);
		tvTerms.setOnClickListener(this);
		btnLogin = (LoginButton) findViewById(R.id.login_button);
		btnLogin.setPublishPermissions("user_friends");// public_profile
														// //user_status
														// //publish_actions
		btnLogin.setPublishPermissions("public_profile");
		btnLogin.setPublishPermissions("publish_actions");
		handler = new Handler();

		btnLogin.setOnClickListener(this);

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

	boolean checkLogIn() {
		boolean result;
		UserModel user = UserModel.getUserInfor(getApplicationContext());
		if (user.getUserId() != null && !user.getUserId().equalsIgnoreCase("")) {
			lnHomeContent.setVisibility(View.VISIBLE);
			tvMessage.setText("");
			tvMessage.setVisibility(View.GONE);

			result = true;
		} else {
			lnHomeContent.setVisibility(View.GONE);
			tvMessage.setText(getString(R.string.unLogin));
			tvMessage.setVisibility(View.VISIBLE);
			result = false;
		}
		return result;

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
			if (checkLogIn()) {
				changeFragment(new ListAdAppFragment());
			}

			slideMenu.showAbove();
			break;
		case R.id.menu_invite:

			changeFragment(new InviteFragment(dialog));
			if (checkLogIn()) {
				changeFragment(new InviteFragment(dialog));

			}
			slideMenu.showAbove();
			break;
		case R.id.menu_friend_list:
			if (checkLogIn()) {
				changeFragment(new FriendsFragment());
			}

			slideMenu.showAbove();
			break;
		case R.id.menu_lucky:
			if (checkLogIn()) {
				changeFragment(new LuckyCardFragment());
			}

			slideMenu.showAbove();
			break;
		case R.id.login_button:
			if (AccessToken.getCurrentAccessToken() != null
					&& com.facebook.Profile.getCurrentProfile() != null) {
				LoginManager.getInstance().logOut();
				mPreferences.clearAll();
				checkLogIn();
				slideMenu.showAbove();
			} else {
				facebookLogin();
			}
			break;
		case R.id.menu_statistic:
			if (checkLogIn()) {
				changeFragment(new IncomeStatisticFragment());
			}

			slideMenu.showAbove();
			break;
			
		default:
			break;
		}
	}

	private void facebookLogin() {
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
											String emailTemp = "";
											try {
												emailTemp = object
														.getString("email");
											} catch (Exception e) {
												// TODO: handle exception
											}

											final String email = emailTemp;
											handler.post(new Runnable() {

												@Override
												public void run() {
													UserConnect.logon(
															name,
															email,
															facebookId,

															mPreferences
																	.getDeviceID(MainActivity.this),
															1,

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
			Log.e("login response", data.toString());
			JSONObject user = data.getJSONObject("user");
			String userId = user.getString("userId");
			String inviteCode = user.getString("invited_code");
			String accessToken = user.getString("access_token");
			mPreferences.setUserID(userId);
			mPreferences.setInviteCode(inviteCode);
			mPreferences.setAccessToken(accessToken);

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
					if(checkLogIn()){
						addFragment(new ListAdAppFragment());
					}
					slideMenu.showAbove();
				}
			});
		}
	}
}
