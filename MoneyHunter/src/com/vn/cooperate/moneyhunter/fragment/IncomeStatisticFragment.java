package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.adapter.TabAdpter;
import com.vn.cooperate.moneyhunter.connect.UserConnect;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.view.TabPageIndicator;

public class IncomeStatisticFragment extends Fragment {
	public TabPageIndicator mTabPageIndicator;
	private ViewPager mViewPager;
	private ArrayList<BaseFragment> list;
	private FragmentManager mMageger;
	private TabAdpter tabAdpter;
	private TextView tvTotal;
	private TextView tvCurrent;
	private TextView tvMonth;
	private TextView tvWeek;
	private TextView tvToday;
	private ConnectApiListener listener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(final JSONObject data) throws JSONException {
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
				try {
					tvCurrent.setText(data.getString("current"));
					tvTotal.setText(data.getString("total"));
					tvMonth.setText(data.getString("month"));
					tvToday.setText(data.getString("today"));
					tvWeek.setText(data.getString("week"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
					
				}
			});
		}
		
		@Override
		public void connectError() {
			
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		UserConnect.getUserProfit("14", listener);
		AQuery aQ = new AQuery(getActivity());
		View view = inflater.inflate(R.layout.fragment_statistic, container,
				false);
		tvTotal = (TextView) view.findViewById(R.id.tv_num_total);
		tvToday = (TextView) view.findViewById(R.id.tv_num_today);
		tvMonth = (TextView) view.findViewById(R.id.tv_num_month);
		tvWeek = (TextView) view.findViewById(R.id.tv_num_week);
		tvCurrent = (TextView) view.findViewById(R.id.tv_num_current);
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
