package com.vn.cooperate.moneyhunter;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.slidingmenu.lib.SlidingMenu;
import com.vn.cooperate.moneyhunter.fragment.ListAdAppFragment;


public class MainActivity extends FragmentActivity {

	SlidingMenu slideMenu;
	ImageView imgMenu;
	Boolean isShowMenu = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		slideMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
		imgMenu = (ImageView) findViewById(R.id.imgMenu);
		imgMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERR change frag ",""+ e.getMessage());
		}
		
	}
	
	
	public void addFragment(Fragment fragment) {
		try {
			curFragment = fragment;
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.lnHomeContainer, fragment);
			ft.commit();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERR change frag ",""+ e.getMessage());
		}
		
	}
}
