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
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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

import com.androidquery.AQuery;
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
import com.vn.cooperate.moneyhunter.fragment.ExchangeFragment;
import com.vn.cooperate.moneyhunter.fragment.FriendsFragment;
import com.vn.cooperate.moneyhunter.fragment.IncomeStatisticFragment;
import com.vn.cooperate.moneyhunter.fragment.InviteFragment;
import com.vn.cooperate.moneyhunter.fragment.ListAdAppFragment;
import com.vn.cooperate.moneyhunter.fragment.LuckyCardFragment;
import com.vn.cooperate.moneyhunter.fragment.dialog.DialogMessage;
import com.vn.cooperate.moneyhunter.model.UserModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private SlidingMenu slideMenu;
	private ImageView imgMenu;
	private Boolean isShowMenu = false;
	private TextView tvMonetize, tvExchange, tvInvite, tvFriends, tvStatistic,
			tvLucky;
	private TextView tvTerms, tvGuide, tvSetting, tvContacts, tvMessage,tvLoadingMessage;
	private LinearLayout lnHomeContent;
	
	private LinearLayout lnSystemMessage;
	private LoginButton btnLogin;
	private CallbackManager managerCallback;
	private ShareDialog dialog;
	private Handler handler;
	private MoneySharedPreferences mPreferences;

	private int heightScreen;
	private int widthScreen;
	public ImageView myAvatar;
	
	public static int FRAGMENT_DOWNLOAD=1;
	public static int FRAGMENT_INVITE=2;
	public static int FRAGMENT_FRIENDLIST=3;
	public static int FRAGMENT_LUCKYCARD=4;
	public static int FRAGMENT_USERPROFIT=5;
	public static int FRAGMENT_EXCHANGE=6;
	static int fragmentId=0;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isPause = false;
		if (isUnShowMessage) {
			DialogMessage mesage = new DialogMessage(title, message);
			mesage.show(getSupportFragmentManager(), "");
			isUnShowMessage = false;
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPause = true;
	}

	
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
		// DialogUtils.showDialogMessage(MainActivity.this,
		// "Dang nhap thanh cong", true);
		// DialogUtils.vDialogLoadingShowProcessing(getBaseContext(), true);

	}
	
	
	 @Override
	   public void onBackPressed() {
	   	// TODO Auto-generated method stub
		 isShowMenu =false;
		   slideMenu.showAbove();
		   FragmentManager fm =getSupportFragmentManager();
		   if(fm.getBackStackEntryCount()>0&&fragmentId!=FRAGMENT_DOWNLOAD)
		   {
			   getSupportFragmentManager().popBackStack();
		   }
		   else
		   {
			   finish();
		   }
	
	   	
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

	static String message = "";
	static String title = "";
	static Boolean isPause = false;
	static Boolean isUnShowMessage = false;


	public void showMessageDialog(String title, String message) {
		if (!isPause) {
			DialogMessage mesage = new DialogMessage(title, message);
			mesage.show(getSupportFragmentManager(), "");
		} else {
			this.message = message;
			this.title = title;
			isUnShowMessage = true;
		}

	}
	
	public void showLoadingMessage() {
		
        lnSystemMessage.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(getString(R.string.processing));
	}
	public void showLoadingMessage(String message) {
	
        lnSystemMessage.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(message);
	}
	
	public void hideLoadingMessage() {
		
        lnSystemMessage.setVisibility(View.GONE);
        //tvLoadingMessage.setText(message);
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
		myAvatar = (ImageView) findViewById(R.id.img_avatar);
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
		tvLoadingMessage = (TextView) findViewById(R.id.tvLoadingMessage);
		tvContacts = (TextView) findViewById(R.id.menu_contacts);
		lnHomeContent = (LinearLayout) findViewById(R.id.lnHomeContainer);
		lnSystemMessage = (LinearLayout) findViewById(R.id.lnSystemMessage);
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
	
	  
		public void addFragment(Fragment fragment) {
			try {
				curFragment = fragment;
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.addToBackStack(""+fragmentId);
				ft.add(R.id.lnHomeContainer, fragment);
				ft.commit();
			} catch (Exception e) {
				Log.e("ERR change frag ", "" + e.getMessage());
			}

		}


	Fragment curFragment;

	public void changeFragment(Fragment fragment,Boolean isClearTop) {
		try {
			curFragment = fragment;
			
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if(isClearTop)
			{
				 getSupportFragmentManager().popBackStack("", FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
			ft.addToBackStack(""+fragmentId);
			ft.replace(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			Log.e("ERR change frag ", "" + e.getMessage());
		}

	}
	
	
	public static  void setFragmentId(int id)
	{
		fragmentId = id;
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

	int SHOW_MENU_DELAY=300;
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
			isShowMenu = false;
			if (checkLogIn()&&fragmentId!=FRAGMENT_DOWNLOAD) {
				
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						changeFragment(new ListAdAppFragment(),true);
					}
				}, SHOW_MENU_DELAY);
				
			}
			fragmentId = FRAGMENT_DOWNLOAD;
			slideMenu.showAbove();
			break;
		case R.id.menu_invite:
			isShowMenu = false;
			isShowMenu = false;
			changeFragment(new InviteFragment(dialog),false);
			if (checkLogIn()&&fragmentId!=FRAGMENT_INVITE) {
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						changeFragment(new InviteFragment(dialog),false);
					}
				}, SHOW_MENU_DELAY);
				

			}
			fragmentId = FRAGMENT_INVITE;
			slideMenu.showAbove();
			break;
		case R.id.menu_friend_list:
			isShowMenu = false;
			if (checkLogIn()&&fragmentId!=FRAGMENT_FRIENDLIST) {
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						changeFragment(new FriendsFragment(),false);
					}
				}, SHOW_MENU_DELAY);
				
			}
			fragmentId = FRAGMENT_FRIENDLIST;
			slideMenu.showAbove();
			break;
		case R.id.menu_lucky:
			isShowMenu = false;
			if (checkLogIn()&&fragmentId!=FRAGMENT_LUCKYCARD) {
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						changeFragment(new LuckyCardFragment(),false);
					}
				}, SHOW_MENU_DELAY);
				
			}
			fragmentId = FRAGMENT_LUCKYCARD;
			slideMenu.showAbove();
			break;
		
		case R.id.menu_statistic:
			isShowMenu = false;
			if (checkLogIn()&&fragmentId!=FRAGMENT_USERPROFIT) {
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						changeFragment(new IncomeStatisticFragment(),false);
					}
				}, SHOW_MENU_DELAY);
				
			}
			fragmentId = FRAGMENT_USERPROFIT;
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
         case R.id.menu_exchange:
        	 if (checkLogIn()&&fragmentId!=FRAGMENT_EXCHANGE) {
 				
 				handler.postDelayed(new Runnable() {
 					
 					@Override
 					public void run() {
 						// TODO Auto-generated method stub
 						changeFragment(new ExchangeFragment(), false);
 					}
 				}, SHOW_MENU_DELAY);
 				
 			}
        	 slideMenu.showAbove();
        	 fragmentId = FRAGMENT_EXCHANGE;
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
											final String urlAvatar = object
													.getJSONObject("picture")
													.getJSONObject("data")
													.getString("url");
											mPreferences.setMyAvatar(urlAvatar);
											Log.e("URL_AVATAR", ": "+urlAvatar);
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
															urlAvatar,
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
					AQuery aQ = new AQuery(MainActivity.this);
					if (checkLogIn()) {
						addFragment(new ListAdAppFragment());
						aQ.id(myAvatar).image(mPreferences.getMyAvatar(MainActivity.this), true, true, 0, 0, null, 0, 1);
					}
					slideMenu.showAbove();
				}
			});
		}
	}
}
