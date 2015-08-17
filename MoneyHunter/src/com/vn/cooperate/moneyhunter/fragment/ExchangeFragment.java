package com.vn.cooperate.moneyhunter.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.vn.cooperate.moneyhunter.MainActivity;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.connect.TransactionConnect;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ExchangeFragment extends Fragment implements OnClickListener {
	private Button btnMobi10k;
	private Button btnMobi20k;
	private Button btnMobi50k;
	private Button btnMobi100k;
	private Button btnMobi200k;
	private Button btnMobi500k;
	private Button btnViettel10k;
	private Button btnViettel20k;
	private Button btnViettel50k;
	private Button btnViettel100k;
	private Button btnViettel200k;
	private Button btnViettel500k;
	private Button btnVina10k;
	private Button btnVina20k;
	private Button btnVina50k;
	private Button btnVina100k;
	private Button btnVina200k;
	private Button btnVina500k;
	private ConnectApiListener listener = new ConnectApiListener() {
		
		@Override
		public void connectSucessfull(JSONObject data) throws JSONException {
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(getActivity(), "Exchanging", Toast.LENGTH_LONG).show();
				}
			});
		}
		
		@Override
		public void connectError() {
			
		}
	};
	private void exchangeCard(int cardType, int agency){
		TransactionConnect.getCardExchange("14", "2", "1", "6wunas4919s1er3uc6l9", listener);
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_exchange, container,
				false);
		btnMobi100k = (Button) view.findViewById(R.id.btn_dongmo_100k);
		btnMobi10k = (Button) view.findViewById(R.id.btn_dongmo_10k);
		btnMobi20k = (Button) view.findViewById(R.id.btn_dongmo_20k);
		btnMobi200k = (Button) view.findViewById(R.id.btn_dongmo_200k);
		btnMobi50k = (Button) view.findViewById(R.id.btn_dongmo_50k);
		btnMobi500k = (Button) view.findViewById(R.id.btn_dongmo_500k);
		btnViettel100k = (Button) view.findViewById(R.id.btn_nguytheo_100k);
		btnViettel10k = (Button) view.findViewById(R.id.btn_nguytheo_10k);
		btnViettel20k = (Button) view.findViewById(R.id.btn_nguytheo_20k);
		btnViettel50k = (Button) view.findViewById(R.id.btn_nguytheo_50k);
		btnViettel200k = (Button) view.findViewById(R.id.btn_nguytheo_200k);
		btnViettel500k = (Button) view.findViewById(R.id.btn_nguytheo_500k);
		btnVina10k = (Button) view.findViewById(R.id.btn_thucvi_10k);
		btnVina20k = (Button) view.findViewById(R.id.btn_thucvi_20k);
		btnVina50k = (Button) view.findViewById(R.id.btn_thucvi_50k);
		btnVina100k = (Button) view.findViewById(R.id.btn_thucvi_100k);
		btnVina200k = (Button) view.findViewById(R.id.btn_thucvi_200k);
		btnVina500k = (Button) view.findViewById(R.id.btn_thucvi_500k);
		btnMobi100k.setOnClickListener(this);
		btnMobi10k.setOnClickListener(this);
		btnMobi200k.setOnClickListener(this);
		btnMobi20k.setOnClickListener(this);
		btnMobi500k.setOnClickListener(this);
		btnMobi50k.setOnClickListener(this);
		btnViettel100k.setOnClickListener(this);
		btnViettel10k.setOnClickListener(this);
		btnViettel200k.setOnClickListener(this);
		btnViettel20k.setOnClickListener(this);
		btnViettel500k.setOnClickListener(this);
		btnViettel50k.setOnClickListener(this);
		btnVina100k.setOnClickListener(this);
		btnVina10k.setOnClickListener(this);
		btnVina200k.setOnClickListener(this);
		btnVina20k.setOnClickListener(this);
		btnVina500k.setOnClickListener(this);
		btnVina50k.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_dongmo_10k:
			exchangeCard(1, 1);
			break;
		case R.id.btn_dongmo_20k:

			break;
		case R.id.btn_dongmo_50k:

			break;
		case R.id.btn_dongmo_100k:

			break;
		case R.id.btn_dongmo_200k:

			break;
		case R.id.btn_dongmo_500k:

			break;
		case R.id.btn_nguytheo_10k:

			break;
		case R.id.btn_nguytheo_20k:

			break;
		case R.id.btn_nguytheo_50k:

			break;
		case R.id.btn_nguytheo_100k:

			break;
		case R.id.btn_nguytheo_200k:

			break;
		case R.id.btn_nguytheo_500k:

			break;
		case R.id.btn_thucvi_500k:

			break;
		case R.id.btn_thucvi_100k:

			break;
		case R.id.btn_thucvi_50k:

			break;
		case R.id.btn_thucvi_200k:

			break;
		case R.id.btn_thucvi_10k:

			break;
		case R.id.btn_thucvi_20k:

			break;
		default:
			break;
		}

	}@Override
	public void onResume() {
		super.onResume();
		MainActivity.setFragmentId(MainActivity.FRAGMENT_EXCHANGE);
	}
}
