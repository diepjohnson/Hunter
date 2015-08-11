package com.vn.cooperate.moneyhunter.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.vn.cooperate.moneyhunter.fragment.BaseFragment;
import com.vn.cooperate.moneyhunter.view.IconPagerAdapter;

public class TabAdpter extends FragmentPagerAdapter implements IconPagerAdapter{

	private ArrayList<BaseFragment> mList = null;

	public TabAdpter(FragmentManager fm, ArrayList<BaseFragment> mList) {
		super(fm);
		this.mList = mList;
	}


	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mList.get(position).getTitle();
	}


	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return mList.get(index).getDrawable();
	}


	@Override
	public String getNumber(int index) {
		// TODO Auto-generated method stub
		return mList.get(index).getNumbernotify();
	}

}
