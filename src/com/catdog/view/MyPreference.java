package com.catdog.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author shang 单例 用来存储 账户（jxt——id 用来登陆 ） 密码 登陆名 phpsession
 * 
 */
public class MyPreference {

	private  SharedPreferences sharedPreference;
	private String packageName="cn.com.wsgy";
	private static Context mcontext;
	private  static final String USERNAME ="username";
	private static final String USER_ID ="userid";
	private static final String TRUENAME ="truename";
	
	private static final String PSWD ="pswd";
	private static final String TX ="tx";
	private static final String PHONE ="phone";
	private static final String EMAIL ="email";
	private static final String VIPID ="vipid";
	private static class InstanceHolder {

		private final static MyPreference preference = new MyPreference();

	}

	// 饿汉式 创建一个单例
	public static MyPreference getInstance(Context context) {
		mcontext = context;
		return InstanceHolder.preference;
	}

	/**
	 * 私有化构造函数
	 * 
	 * @param context
	 */
	private MyPreference() {

		packageName = mcontext.getPackageName() + "_preferences";
		sharedPreference = mcontext.getSharedPreferences(packageName,
				mcontext.MODE_PRIVATE);
	}

	public  String getVipId() {
		String vipid = sharedPreference.getString(VIPID,null);

		return vipid;
	}
	public  void setVipId(String vipid) {
		Editor editor = sharedPreference.edit();
		editor.putString(VIPID, vipid);
		editor.commit();
	}
	public  String getEmail() {
		String email = sharedPreference.getString(EMAIL,null);

		return email;
	}
	public  void setEmail(String email) {
		Editor editor = sharedPreference.edit();
		editor.putString(EMAIL, email);
		editor.commit();
	}


	public  String getUsername() {
		String username = sharedPreference.getString(USERNAME, null);

		return username;
	}
	public  void setUsername(String username) {
		Editor editor = sharedPreference.edit();
		editor.putString(USERNAME, username);
		editor.commit();
	}

	public static String getTruename() {
		return TRUENAME;
	}

	public static String getPswd() {
		return PSWD;
	}

	public static String getTx() {
		return TX;
	}

	public  void setPhone(String phone) {
		Editor editor = sharedPreference.edit();
		editor.putString(PHONE, phone);
		editor.commit();
	}
	public  String getPhone() {
		String phone = sharedPreference.getString(PHONE, null);
		return phone;
	}

	public  String getUserId() {
		String username = sharedPreference.getString(USER_ID, null);
		return username;
	}
	public  void setUserId(String userid) {
		Editor editor = sharedPreference.edit();
		editor.putString(USER_ID, userid);
		editor.commit();
	}

}
















