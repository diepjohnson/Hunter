package com.vn.cooperate.moneyhunter.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vn.cooperate.moneyhunter.R;

/**
 * @since January 28, 2015
 * @inauthor hoangcaomobile
 *
 */
public class DialogUtils {

	private static Dialog dialogLoading;
	public static void vDialogLoadingShow(Context mContext, String message, boolean isCancelEnable) {
		dialogLoading = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialogLoading.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
		dialogLoading.setContentView(R.layout.dialog_loadding);
		dialogLoading.setCancelable(isCancelEnable);
		dialogLoading.show();

		//dialogLoading.findViewById(R.id.ivLoading).startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate_spinner_smooth));
		TextView tvMessage = (TextView) dialogLoading.findViewById(R.id.tvDialogLoading);
		tvMessage.setText(message);

		if (isCancelEnable) {
			RelativeLayout layoutParent = (RelativeLayout) dialogLoading.findViewById(R.id.layoutDialogLoading);
			layoutParent.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialogLoading.dismiss();
				}
			});
		}
	}

	public static void vDialogLoadingShowProcessing(Context mContext, boolean isCancelEnable) {
		vDialogLoadingShow(mContext, mContext.getString(R.string.processing), isCancelEnable);
	}

	public static void vDialogLoadingDismiss() {
		dialogLoading.dismiss();
	}

}
