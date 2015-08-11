package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.view.TabPageIndicator;

public class IncomeStatisticFragment extends Fragment {
	public TabPageIndicator mTabPageIndicator;
	private ViewPager mViewPager;
	private ArrayList<BaseFragment> list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		AQuery aQ = new AQuery(getActivity());
		View view = inflater.inflate(R.layout.fragment_statistic, container, false);
		return view;
	}
}
