
package com.catdog.util;

import android.os.CountDownTimer;
import android.widget.Button;

import com.catdog.base.BaseActivity;



/**
 * 日期：2015-7-8
 * 作者：Paul
 * 功能：倒计时
 */
public class MyCount extends CountDownTimer {
	private Button button;
	private BaseActivity baseActivity;
	public MyCount(long millisInFuture, long countDownInterval,Button button,BaseActivity baseActivity) {     
		super(millisInFuture, countDownInterval); 
		this.button = button;
		this.baseActivity = baseActivity;
	}     
	@Override     
	public void onFinish() {     
		button.setText("重新获取");
		button.setClickable(true);
//		button.setTextColor(Color.BLACK);
	}     
	@Override     
	public void onTick(long millisUntilFinished) {
//		button.setTextColor(Color.GRAY);
//		baseActivity.setViewBackground(button,R.drawable.code_gray_bg);
		button.setClickable(false);
		button.setText("请等待(" + millisUntilFinished / 1000 + ")");
	}
}     
