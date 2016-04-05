package com.catdog.activity;

public class Constant {

	/** sharePreference信息 */
	/**
	 * 用户名和电话号码都可以进行登录  uid/tel
	 */
	/**----------------------------------*/
	public static final String IS_AUTO_LOGIN = "is_auto_login";// 是否自动登陆
	public static final String IS_LOGIN = "is_login"; //是否登录
	public static final String REMEMBER_PREFERENCE = "users";// 保存用户的帐号密码
	public static final String REMEMBER_PWD = "password";  //保存密码
	public static final String REMEMBER_ACCOUNT = "account";  //保存密码
	
	public static final String USER_ID ="id";
	public static final String USER_UID ="uid";  //用户名
	public static final String USER_EMAIL ="email";
	public static final String USER_CARD ="card";
	public static final String USER_TEL ="tel"; //电话号码
	
	public static final int SDK_PAY_FLAG = 1;

	public static final int SDK_CHECK_FLAG = 2;
	
	/**
	 * 以下为拍照功能
	 */
	public static final int TAKE_PICTURE = 0;
	public static final int RESULT_LOAD_IMAGE = 1;
	public static final int CUT_PHOTO_REQUEST_CODE = 2;
	public static final int SELECTIMG_SEARCH = 3;
	
	/**
	 * 加载数据----第一次加载完成（初始化数据）
	 */
	public static final int LOADING_INIT = 1000;
	/**
	 * 加载数据----数据加载失败
	 */
	public static final int LOADING_ERROR = 1001;
	/**
	 * 加载数据----多次加载数据
	 */
	public static final int LOADING_MORE = 1002;
	/**
	 * 加载数据----加载数据完成（数据返回空）
	 */
	public static final int LOADING_FINISH = 1003;
	
}
