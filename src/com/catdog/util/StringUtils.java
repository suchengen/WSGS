package com.catdog.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;

public class StringUtils {

	@SuppressLint("NewApi")
	public static boolean isEmpty(String str) {
		return (str == null) || (str.equals(""));
	}

	/**
	 * 验证码是否有效(6位数字)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValideVerify(String s) {
		return s.matches("^\\d{6}$");
	}

	/**
	 * 验证密码是否有效(6-12位字符)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isValidePwd(String s) {
		return s.matches("^\\w{6,12}$");
	}

	
	/**
	 * 判断手机号码是否合法
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或4或5或7或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][34578]\\d{9}";
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
		{
			if(TextUtils.isDigitsOnly(mobiles) && mobiles.length() == 11)
			{
				return mobiles.matches(telRegex);
			}else
			{
				return false;
			}
		}
	}



	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * Double类型保留一位小数，返回String类型（注意四舍五入的影响）
	 */
	public static String formatDoubleToString(Double d) {
		if (d == null) {
			return "0.0";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(d);
	}

	/**
	 * Double类型保留一位小数，返回double类型（四舍五入）
	 */
	public static double formatDouble(double d) {
		return formatDouble(d, 1);
	}

	/**
	 * Double类型保留指定位数的小数，返回double类型（四舍五入） newScale 为指定的位数
	 */
	public static double formatDouble(double d, int newScale) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String formatString(String d1, int newScale) {
		DecimalFormat f = null;
		double d = Double.valueOf(d1);
		switch (newScale) {
		case 0:
			f = new DecimalFormat("###0");
			break;
		case 1:
			d = formatDouble(d, 1);
			f = new DecimalFormat("###0.0");
			break;
		case 2:
			d = formatDouble(d, 2);
			f = new DecimalFormat("###0.00");
			break;
		default:
			break;
		}
		return (f.format(d));
	}

	/**
	 * 保留小数（注意四舍五入的影响）
	 * 
	 * @param d
	 *            , decimal
	 * @return
	 */
	public static String getDecimal(double d, int decimal) {
		DecimalFormat f = null;
		switch (decimal) {
		case 0:
			f = new DecimalFormat("###0");
			break;
		case 1:
			d = formatDouble(d, 1);
			f = new DecimalFormat("###0.0");
			break;
		case 2:
			d = formatDouble(d, 2);
			f = new DecimalFormat("###0.00");
			break;
		default:
			break;
		}
		return (f.format(d));
	}


	/**
	 * 获取AppKey
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}





	public static String replaceString(String s) {
		String temp = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((c >= 0x4e00) && (c <= 0x9fbb)) {
				temp += c;
			}
		}
		return temp;
	}
}
