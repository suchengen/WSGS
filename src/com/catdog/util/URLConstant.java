package com.catdog.util;

public class URLConstant {

	public static final String BASE = "http://www.wssxls.com/shop/app.php?";
	/**
	 * 首页类别接口
	 * http://www.wssxls.com/shop/app.php?act=newsclass&parentid=&appindex
	 * =1&sp_id=52
	 */
	public static final String NEWSCLASS = "act=newsclass";
	/**
	 * 注册
	 */
	public static final String REGISTER = BASE + "act=reg";
	/**
	 * 登录
	 */
	public static final String LOGIN = BASE + "act=login";
	/**
	 * 获取用户头像
	 */
	public static final String USER_HEAD = BASE + "act=getpic";
	/**
	 * 忘记密码
	 */
	public static final String FORT_PWD = BASE + "act=pwd_edit_tel";
	/**
	 * 修改用户头像
	 */
	public static final String USER_CHANGE_HEAD = BASE + "act=editpic";
	/**
	 * 修改用户名
	 */
	public static final String CHAGE_NAME = BASE + "act=uid_edit";
	/**
	 * 修改手机
	 */
	public static final String CHAGE_PHONE = BASE + "act=tel_edit";
	/**
	 * 获取地址列表
	 */
	public static final String ADDRESS_LIST = BASE + "act=addresslist";
	/**
	 * 添加地址列表
	 */
	public static final String ADD_ADDRESS = BASE + "act=addressadd";

	/**
	 * 删除所有地址
	 */
	public static final String DELETE_ALL_ADDRESS = BASE + "act=addressclear";

	/**
	 * 获取短信
	 */
	public static final String GET_PHONE_CODE = BASE + "act=sms_code";

	/**
	 * 添加收藏
	 */
	public static final String ADD_COLLECT = BASE + "act=shoucangadd";

	/**
	 * 删除收藏
	 */
	public static final String DELETE_COLLECT = BASE + "act=shoucangdel";

	/**
	 * 我的收藏
	 */
	public static final String MY_COLLECT = BASE + "act=shoucanglist";

	/**
	 * 删除所有收藏
	 */
	public static final String DELET_ALL_COLLET = BASE + "act=shoucangclear";

	/**
	 * 修改密码
	 */
	public static final String CHANGE_PWD = BASE + "act=pwd_edit";

	/**
	 * 修改邮箱
	 */
	public static final String CHANGE_EMAIL = BASE + "act=email_edit";

	/**
	 * 获取商品一级分类
	 */
	public static final String ONE_CLASSIFY = BASE + "act=newsclass";

	/**
	 * 获取商品二级分类
	 */
	public static final String TWO_CLASSIFY = BASE + "act=newslist";

	/**
	 * 商品详情
	 */
	public static final String SHOP_DETAIL = BASE + "act=newsdetail";

	/**
	 * 商品评论
	 */
	public static final String SHOP_COMMENT = BASE + "act=newsdetailpinglun";

	public static final int CONNECT_TIME_OUT = 6000; // 连接超时时间

	public static final String CLASS_ID = "CLASS_ID";
	public static final int CLASS_FRUIT_ID = 142; // 水果类
	public static final int CLASS_VEGETABLES_ID = 143; // 蔬菜类
	public static final int CLASS_EGG_ID = 241; // 肉蛋禽
	public static final int CLASS_FROZEN_ID = 297; // 冻品类
	public static final int CLASS_WATER_ID = 146; // 水产类
	public static final int CLASS_FRAIN_ID = 149; // 粮油类
	public static final int CLASS_MACHINING_ID = 185; // 加工类
	public static final int CLASS_GIFT_ID = 293; // 礼盒类
	public static final int CLASS_DRY_ID = 251; // 干果
	public static final int CLASS_MARKET_ID = 254; // 商超

}
