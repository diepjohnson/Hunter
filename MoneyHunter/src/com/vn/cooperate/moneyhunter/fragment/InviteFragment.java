package com.vn.cooperate.moneyhunter.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.vn.cooperate.moneyhunter.R;
import com.vn.cooperate.moneyhunter.connect.FriendsConnect;
import com.vn.cooperate.moneyhunter.myinterface.ConnectApiListener;
import com.vn.cooperate.moneyhunter.util.MoneySharedPreferences;

public class InviteFragment extends Fragment implements OnClickListener {
	private TextView tvContentCode;
	private TextView tvDescription;
	private EditText edtTypeCode;
	private Button btnSubmit;
	private TextView tvEmail;
	private TextView tvSMS;
	private TextView tvGPlus;
	private TextView tvFB;
	private ShareDialog dialog;

	public InviteFragment(ShareDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_invite, container, false);
		dialog = new ShareDialog(getActivity());
		tvContentCode = (TextView) view.findViewById(R.id.tv_content_code);
		tvDescription = (TextView) view.findViewById(R.id.tv_description);
		edtTypeCode = (EditText) view.findViewById(R.id.edt_invite_code);
		btnSubmit = (Button) view.findViewById(R.id.btn_submit);
		tvEmail = (TextView) view.findViewById(R.id.share_email);
		tvSMS = (TextView) view.findViewById(R.id.share_sms);
		tvGPlus = (TextView) view.findViewById(R.id.share_gplus);
		tvFB = (TextView) view.findViewById(R.id.share_fb);
		btnSubmit.setOnClickListener(this);
		tvEmail.setOnClickListener(this);
		tvFB.setOnClickListener(this);
		tvGPlus.setOnClickListener(this);
		tvSMS.setOnClickListener(this);
		String invite = new MoneySharedPreferences(getActivity())
				.getInviteCode(getActivity());
		tvContentCode.setText(invite);
		tvDescription.setText((Html.fromHtml(getString(R.string.description)
				+ " " + "<font color="
				+ getResources().getColor(R.color.orange) + ">"
				+ getActivity().getString(R.string.type_invite_code)
				+ "</font>" + " " + getString(R.string.invite_to) + " "
				+ "<font color=" + getResources().getColor(R.color.orange)
				+ ">" + getActivity().getString(R.string.invite_income)
				+ "</font>" + " " + getString(R.string.invite_benefit)))

		);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			validateInviteCode();
			break;
		case R.id.share_email:

			break;
		case R.id.share_fb:
			shareFB();
			break;
		case R.id.share_gplus:

			break;
		case R.id.share_sms:

			break;

		default:
			break;
		}

	}

	private void shareFB() {
		if (ShareDialog.canShow(ShareLinkContent.class)) {
			ShareLinkContent linkContent = new ShareLinkContent.Builder()
					.setContentTitle(getString(R.string.share_title_fb))
					.setContentDescription(
							getString(R.string.share_content_fb)
									+ new MoneySharedPreferences(getActivity())
											.getInviteCode(getActivity()))
					.setContentUrl(
							Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana"))
					.build();

			dialog.show(linkContent);
		}

	}

	private void validateInviteCode() {
		if (edtTypeCode
				.getText()
				.toString()
				.equals(new MoneySharedPreferences(getActivity())
						.getInviteCode(getActivity()))) {
			Toast.makeText(getActivity(),
					getActivity().getString(R.string.invite_code_error_toast),
					Toast.LENGTH_LONG).show();
		} else if (!edtTypeCode.getText().toString().equals("")) {
			FriendsConnect.makeFriend(new MoneySharedPreferences(getActivity())
					.getUserID(getActivity()),
					edtTypeCode.getText().toString(), listener);
		}
	}

	ConnectApiListener listener = new ConnectApiListener() {

		@Override
		public void connectSucessfull(JSONObject data) {
			try {
				String returnCode = data.getString("errorCode");
				final String msg = data.getString("message");
				if (returnCode.equals("0")) {

				} else {

				}
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void connectError() {

		}
	};

}
