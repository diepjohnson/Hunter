package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.TabAdpter;
import com.vn.cooperate.moneyhunter.view.TabPageIndicator;

public class IncomeStatisticFragment extends Fragment {
	public TabPageIndicator mTabPageIndicator;
	private ViewPager mViewPager;
	private ArrayList<BaseFragment> list;
	private FragmentManager mMageger;
	private TabAdpter tabAdpter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		AQuery aQ = new AQuery(getActivity());
		View view = inflater.inflate(R.layout.fragment_statistic, container,
				false);
		mTabPageIndicator = (TabPageIndicator) view
				.findViewById(R.id.tab_indicator);
		mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
		ReceiveTabFragment fragReceive = new ReceiveTabFragment();
		list = new ArrayList<BaseFragment>();
		fragReceive.setTitle(getString(R.string.tab_name1));
		list.add(fragReceive);
		ExchangeTabFragment fragReceive1 = new ExchangeTabFragment();
		fragReceive1.setTitle(getString(R.string.tab_name2));
		list.add(fragReceive1);
		mMageger = getActivity().getSupportFragmentManager();
		tabAdpter = new TabAdpter(mMageger, list);
		mViewPager.setAdapter(tabAdpter);
		mTabPageIndicator.setViewPager(mViewPager);
		return view;
	}
}
