package com.catdog.view;



import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.catdog.wsgs.R;

public class PopupwindowView extends PopupWindow {
	public PopupwindowView(Context mContext, View parent,OnClickListener listener) {

		View view = View
				.inflate(mContext, R.layout.popupwindow_layout_photo, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view
				.findViewById(R.id.ll_popup);
		// ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
		// R.anim.push_bottom_in_2));

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		
		bt1.setOnClickListener(listener);
		bt2.setOnClickListener(listener);
		bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				dismiss();
			}
		});
	}
}
