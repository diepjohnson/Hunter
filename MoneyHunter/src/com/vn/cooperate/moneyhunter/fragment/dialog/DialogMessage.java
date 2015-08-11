package com.vn.cooperate.moneyhunter.fragment.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vn.cooperate.moneyhunter.MoneyHunterApplication;
import com.vn.cooperate.moneyhunter.R;

public class DialogMessage extends DialogFragment {

	String title,message;
	public  DialogMessage(String title,String message)
	{
		this.title = title;
		this.message = message;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		LayoutParams params = new WindowManager.LayoutParams();
//		params.width = MoneyHunterApplication.getWithScreen()*2/3;
//		params.height =WindowManager.LayoutParams.WRAP_CONTENT;
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		View v = inflater.inflate(R.layout.dialog_mesage, container, false);
		LinearLayout lnHomeDialog = (LinearLayout) v.findViewById(R.id.lnHomeDialog);
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		TextView tvMessage = (TextView) v.findViewById(R.id.tvMessage);
		
		lnHomeDialog.setMinimumWidth(MoneyHunterApplication.getWithScreen()*2/3);
		Button btnOk = (Button) v.findViewById(R.id.btnOk);
		tvTitle.setText(title);
		tvMessage.setText(message);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogMessage.this.dismiss();
			}
		});
		return v;
	}
}
