package com.vn.cooperate.moneyhunter.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.vn.cooperate.moneyhunter.MainActivity;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.connect.LuckyCardConnect;
import com.vn.cooperate.moneyhunter.model.UserModel;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class LuckyCardFragment extends Fragment {

	List<Integer> listCard = new ArrayList<Integer>();

	int rewardId = 1;
	Button btnCard1, btnCard2, btnCard3, btnCard4, btnCard5, btnCard6;
	int buttonIndex = 0;
	MainActivity mActivity;
	static Boolean isGetCoin=false;

	Handler handle = new Handler();
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity.setFragmentId(MainActivity.FRAGMENT_DOWNLOAD);
	};
	ConnectApiListener connectListener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(final JSONObject data) {
			// TODO Auto-generated method stub
			
			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					try {
						mActivity.hideLoadingMessage();
						int erorCode = data.getInt("errorCode");
						if(erorCode==0)
						{
							int resultCode = data.getInt("resultcode");
							if(resultCode==1)
							{
								rewardId=data.getInt("rewardId");	
							}
							else
							{
								isGetCoin=true;
									initData();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
		@Override
		public void connectError() {
			// TODO Auto-generated method stub
			handle.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mActivity.hideLoadingMessage();
				}
			});
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_lucky_card, container,
				false);
		listCard.add(0);
		listCard.add(100);
		listCard.add(300);
		listCard.add(1000);
		listCard.add(5000);
		listCard.add(10000);
		btnCard1 = (Button) v.findViewById(R.id.btnCard1);
		btnCard2 = (Button) v.findViewById(R.id.btnCard2);
		btnCard3 = (Button) v.findViewById(R.id.btnCard3);
		btnCard4 = (Button) v.findViewById(R.id.btnCard4);
		btnCard5 = (Button) v.findViewById(R.id.btnCard5);
		btnCard6 = (Button) v.findViewById(R.id.btnCard6);
		UserModel user = UserModel.getUserInfor(mActivity);
		mActivity.showLoadingMessage(mActivity.getString(R.string.processing));
		//DialogUtils.vDialogLoadingShowProcessing(mActivity, true);
		LuckyCardConnect.getDailyReward(user.getUserId(), connectListener);

		btnCard1.setOnClickListener(listener);
		btnCard2.setOnClickListener(listener);
		btnCard3.setOnClickListener(listener);
		btnCard4.setOnClickListener(listener);
		btnCard5.setOnClickListener(listener);
		btnCard6.setOnClickListener(listener);
		
		return v;
	}

	
	void initData()
	{
		MoneySharedPreferences share = new MoneySharedPreferences(mActivity);
		int selectedCard= share.getSelectedCard();
		for (int i = 1; i <= 6; i++) 
		{
			Button btnCard = getButtonById(i);
			int coin = share.getLuckyCardData(i);
			 if(coin==0)
			 {
				 btnCard.setText(mActivity.getString(R.string.unLucky));
			 }
			 else
			 {
				 btnCard.setText(mActivity.getString(R.string.Lucky) + coin+" coin");
			 }
			 
			 if(i ==selectedCard)
			 {
				 btnCard.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.selected_card));
			 }
			 else
			 {
				 btnCard.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.opened_card));
			 }
			
		}
	}
	void updateData(int id) {
        if(isGetCoin)
        {
        	return ;
        }
		Button btnCard = getButtonById(id);
	MoneySharedPreferences share = new MoneySharedPreferences(mActivity);
	
	
		int coin = listCard.get(rewardId);
		if(coin==0)
		{
			btnCard.setText(mActivity.getString(R.string.unLucky));
		}
		else
		{
			btnCard.setText(mActivity.getString(R.string.Lucky)+ coin+" coin");
		}
		
		btnCard.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.selected_card));
		listCard.remove(rewardId);
		
		share.setLuckyCardData(coin, id);
		share.setSelectedCard(id);
		for (int i = 1; i <= 6; i++) {
			if(i==id)
			{
				continue;
			}
			

	    	Random r = new Random();
	    	if(listCard.size()>1)
	    	{
	    		
	    		int number = r.nextInt(listCard.size()-1);
	    		 coin = listCard.get(number);
	    		 listCard.remove(number);
				 btnCard = getButtonById(i);
				 if(coin==0)
				 {
					 btnCard.setText(mActivity.getString(R.string.unLucky));
				 }
				 else
				 {
					 btnCard.setText(mActivity.getString(R.string.Lucky) + coin+" coin");
				 }
				 share.setLuckyCardData(coin, i);
					btnCard.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.opened_card));
	    	}
	    	else
	    	{
	    		 coin = listCard.get(0);
				 btnCard = getButtonById(i);
				 btnCard.setText(mActivity.getString(R.string.Lucky) + coin+" coin");
				 share.setLuckyCardData(coin, i);
				 btnCard.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.opened_card));
	    	}
	    	
		}
		
		isGetCoin = true;
	}

	Button getButtonById(int id) {
		switch (id) {
		case 1:
			buttonIndex = 1;
			return btnCard1;

		case 2:

			return btnCard2;
		case 3:

			return btnCard3;
		case 4:

			return btnCard4;
		case 5:

			return btnCard5;
		case 6:

			return btnCard6;

		default:
			return btnCard1;
		}
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.btnCard1:
				buttonIndex = 1;
				updateData(buttonIndex);
				break;
			case R.id.btnCard2:
				buttonIndex = 2;
				updateData(buttonIndex);
				break;
			case R.id.btnCard3:
				buttonIndex = 3;
				updateData(buttonIndex);
				break;
			case R.id.btnCard4:
				buttonIndex = 4;
				updateData(buttonIndex);
				break;
			case R.id.btnCard5:
				buttonIndex = 5;
				updateData(buttonIndex);
				break;
			case R.id.btnCard6:
				buttonIndex = 6;
				updateData(buttonIndex);
				break;

			default:
				break;
			}
		}
	};
}
