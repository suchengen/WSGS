package com.catdog.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catdog.activity.Constant;
import com.catdog.util.SharePreferencesManager;
import com.catdog.wsgs.R;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {
	private MyApplication application;
	public static Context mContext;
	private boolean loadtitle = false;
	private TextView tv_title;
	private ImageView img_left, img_right;
	private com.catdog.util.SharePreferencesManager preferencesManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		application = (MyApplication) getApplication();
		application.addActivity(this);
		mContext = this;
		preferencesManager = new SharePreferencesManager(mContext,
				Constant.REMEMBER_PREFERENCE);
		View view = LayoutInflater.from(mContext).inflate(R.layout.title, null);
		if (view != null) {

			loadtitle(view);
			loadtitle = true;
		}
	}

	private void loadtitle(View view) {
		// TODO 自动生成的方法存根
		tv_title = (TextView) view.findViewById(R.id.title_text);
		img_left = (ImageView) view.findViewById(R.id.title_img_left);
		img_right = (ImageView) view.findViewById(R.id.title_img_right);
		loadtitle = true;
	}

	/**
	 * 设置标题
	 */
	public void setTitle(String str) {
		if (loadtitle) {
			Log.i("base", "---biaoti " + str);
			tv_title.setText(str);
		}
	}

	public void setLeftBack() {
		if (img_left != null) {
			img_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					finish();
				}
			});
		}
	}

	public void setRightImg(int id, OnClickListener listener) {
		if (img_right != null) {
			img_right.setBackgroundResource(id);
			img_left.setOnClickListener(listener);
		}
	}

	/**
	 * 作用：获取手机api版本
	 * 
	 * @return
	 */
	protected int getApiLevel() {
		return android.provider.Settings.System.getInt(getContentResolver(),
				android.provider.Settings.System.SYS_PROP_SETTING_VERSION, 3);
	}

	/**
	 * 作用：设置某个控件的背景；
	 * 
	 * @param view
	 * @param drawable
	 */
	@SuppressLint("NewApi")
	public void setViewBackground(View view, Drawable drawable) {
		if (getApiLevel() >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	/*********** 获取用户信息 ***/

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public String getUserId() {
		return preferencesManager.getString(Constant.USER_ID, null);
	}

	/**
	 * 获取用户uid
	 * 
	 * @return
	 */
	public String getUserUid() {
		return preferencesManager.getString(Constant.USER_UID, null);
	}

	/**
	 * 获取用户电话
	 * 
	 * @return
	 */
	public String getUserPhone() {
		return preferencesManager.getString(Constant.USER_TEL, null);
	}

	/**
	 * 获取用户email
	 * 
	 * @return
	 */
	public String getUserEmail() {
		return preferencesManager.getString(Constant.USER_EMAIL, null);
	}

	/**
	 * 获取用户卡券
	 * 
	 * @return
	 */
	public String getUserCard() {
		return preferencesManager.getString(Constant.USER_CARD, "");
	}

	/**
	 * 获取用户密码
	 * 
	 * @return
	 */
	public String getUserPwd() {
		return preferencesManager.getString(Constant.REMEMBER_PWD, null);
	}

	/**
	 * 用户是否登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return preferencesManager.getBoolean(Constant.IS_LOGIN, false);
	}

	/**
	 * 作用：设置某个控件的背景；
	 * 
	 * @param view
	 * @param drawable
	 */
	public void setViewBackground(View view, int drawableId) {
		Drawable drawable = getResources().getDrawable(drawableId);
		setViewBackground(view, drawable);
	}

	public boolean isEmpty(String str) {
		return (str == null) || (str.equals(""));
	}

	private Toast toast;

	/**
	 * 短时间显示Toast 作用:不重复弹出Toast,如果当前有toast正在显示，则先取消
	 * 
	 * @param info
	 *            显示的内容
	 */
	public void showToast(String info) {
		if (toast != null) {
			toast.cancel();
		}
		toast = Toast.makeText(this, info, Toast.LENGTH_SHORT);
		toast.setText(info);
		toast.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
