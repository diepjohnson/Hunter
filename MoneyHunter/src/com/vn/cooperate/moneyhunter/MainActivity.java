package com.vn.cooperate.moneyhunter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.slidingmenu.lib.SlidingMenu;
import com.vn.cooperate.moneyhunter.fragment.ListAdAppFragment;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private SlidingMenu slideMenu;
	private ImageView imgMenu;
	private Boolean isShowMenu = false;
	private RelativeLayout rlAvatar;
	private LoginButton btnLogin;
	private CallbackManager managerCallback;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		managerCallback = CallbackManager.Factory.create();
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
		rlAvatar = (RelativeLayout) findViewById(R.id.rl_avatar);
		rlAvatar.setOnClickListener(this);
		btnLogin = (LoginButton) findViewById(R.id.login_button);
		btnLogin.setPublishPermissions("user_friends");//public_profile //user_status //publish_actions
		btnLogin.setPublishPermissions("public_profile");
		btnLogin.setPublishPermissions("publish_actions");
		btnLogin.registerCallback(managerCallback, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				Log.e("USERID", "is " + result.getAccessToken().getUserId());
				
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
				if(!isShowMenu)
				{
					slideMenu.showBehind();
					isShowMenu = true;
				}
				else
				{
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
		case R.id.rl_avatar:
			Toast.makeText(MainActivity.this, "loging in facebook",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	
	
	public void addFragment(Fragment fragment) {
		try {
			curFragment = fragment;
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			Log.e("ERR change frag ",""+ e.getMessage());
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		managerCallback.onActivityResult(requestCode, resultCode, data);
	}
}
