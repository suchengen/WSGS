package com.catdog.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * 作者：Lyman
 * 时间：2014-12-27下午4:13:32
 * 作用：字符串处理工具
 */
public class MyTextUtils {
	private static final String TAG = "MyTextUtils";
	private static Pattern mPattern;
	/**
	 * 手机号码的正则表达式
	 */
	public static final String REG_PHONE = "^(\\+)?(86)?0?(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9]|16[0-9])[0-9]{8}$";
	
	public static final String REG_EMAIL = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
	
	public static final String REG_POSTALCODE = "[1-9]\\d{5}(?!\\d)";
	
	public static final String REG_NICKNAME = "^[\u4e00-\u9fa5_0-9A-Za-z]{2,12}$";
	
	/**
	 * 作用：判断是否是手机号码
	 * @param phoneNum
	 * @return
	 */
	public static boolean isPhoneNum(String phoneNum){
		mPattern = Pattern.compile(REG_PHONE);
		Matcher matcher = mPattern.matcher(phoneNum);
		return matcher.find();
	}	
	
	/**
	 * 作用：验证是否是邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(isEmpty(email)){
			return false;
		}
		mPattern = Pattern.compile(REG_EMAIL);
		Matcher matcher = mPattern.matcher(email);
		return matcher.find();
	}
	
	/**
	 * 作用：判断字符串是否为空 （null、“null”、“”、空白行）
	 * @param str 需要做判断的字符串
	 * @return
	 */
	public static boolean isEmpty(String str){
		return (str == null || "null".equalsIgnoreCase(str) || "".equals(str) || str.length() <= 0 || str.matches("^\\s*$"));
	}
	
	public static boolean arrayIsEmpty(byte[] pByte){
		return pByte == null || pByte.length <= 0;
	}
	
	/**
	 * 作用：子线程中Toast
	 * @param context
	 * @param msg
	 */
	public static void postMsg(final Context context,Handler handler, final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, msg + "", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	/**
	 * 作用：子线程中Toast
	 * @param context
	 * @param msg
	 */
	public static void postMsg(final Context context,Handler handler, final int msgResId) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
			}
		});
	}

	/**
	 * 作用：数组中是否有空值
	 * @return
	 */
	public static boolean arrayHasNull(Object[] data) {
		for (int i = 0; i < data.length; i++) {
			Log.d("", "---" + data[i]);
			if(data[i] != null){
				return false;
			}
		}
		return true;
	}
	/**
	 * 作用：根据生日 时间戳 获取 年龄
	 * @param birthDate
	 * @return 年龄
	 */
	public static int getAge(long birthDate) {
		Calendar calendar = Calendar.getInstance();
		int now = calendar.get(Calendar.YEAR);
		calendar.setTime(new Date(709040083L * 1000));
		return now - calendar.get(Calendar.YEAR);
	}

	/**
	 * 作用：是否是 邮政编码
	 * @param postalcode
	 * @return
	 */
	public static boolean isPostalcode(String postalcode) {
		mPattern = Pattern.compile(REG_POSTALCODE);
		Matcher matcher = mPattern.matcher(postalcode);
		return matcher.find();
	}

	/**
	 * 作用：将String集合 拼接到一起
	 * @param ids
	 * @return
	 */
	public static String linkStrings(List<String> strings, char spliter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.size(); i++) {
			sb.append(strings.get(i) + spliter);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 作用：
	 * @param string
	 * @return
	 */
	public static String notShowNull(String string) {
		if(isEmpty(string)){
			return "";
		}
		return string;
	}

	/**
	 * 作用：不使用 科学计数法来 显示大数
	 * @param doubleStr
	 * @return
	 */
	public static String getBigDouble(double doubleStr) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(doubleStr);
	}

	/**
	 * 作用：验证昵称是否合法
	 * @param nickname
	 * @return
	 */
	public static boolean validateNickname(String nickname){
		mPattern = Pattern.compile(REG_NICKNAME);
		Matcher matcher = mPattern.matcher(nickname);
		return matcher.find();
	}	
}
