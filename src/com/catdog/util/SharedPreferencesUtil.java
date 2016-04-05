package com.catdog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** 对程序的一些配置参数进行操作的工具类 */
public class SharedPreferencesUtil {
	/** ProconfigUtil==>SharedPreferencesName */
	public static String CommSharedPreferencesName = "comm_main";

	public static SharedPreferences getSharePreferences(Context context) {
		return context.getSharedPreferences(CommSharedPreferencesName,
				Context.MODE_PRIVATE);
	}

	public static Editor getEdit(Context context) {
		return getSharePreferences(context).edit();
	}

	public static boolean save(Context context, String key, String value) {
		return getSharePreferences(context).edit().putString(key, value)
				.commit();
	}

	public static boolean save(Context context, String key, Long value) {
		return getSharePreferences(context).edit().putLong(key, value).commit();
	}

	public static boolean save(Context context, String key, Integer value) {
		return getSharePreferences(context).edit().putInt(key, value).commit();
	}

	public static boolean save(Context context, String key, Boolean value) {
		return getSharePreferences(context).edit().putBoolean(key, value)
				.commit();
	}

	public static boolean save(Context context, String key, Float value) {
		return getSharePreferences(context).edit().putFloat(key, value)
				.commit();
	}

	public static Object read(Context context, String key, Object defValue) {
		SharedPreferences mPreferences = getSharePreferences(context);
		if (defValue instanceof String) {
			return mPreferences.getString(key, (String) defValue);
		} else if (defValue instanceof Boolean) {
			return mPreferences.getBoolean(key, (Boolean) defValue);
		} else if (defValue instanceof Integer) {
			return mPreferences.getInt(key, (Integer) defValue);
		} else if (defValue instanceof Long) {
			return mPreferences.getLong(key, (Long) defValue);
		} else if (defValue instanceof Float) {
			return mPreferences.getFloat(key, (Float) defValue);
		}
		return defValue;
	}

	public static int getInt(Context context, String key, int defValue) {
		return getSharePreferences(context).getInt(key, defValue);
	}

	public static String getString(Context context, String key, String defValue) {
		return getSharePreferences(context).getString(key, defValue);
	}

	public static boolean getBoolean(Context context, String key,
			Boolean defValue) {
		return getSharePreferences(context).getBoolean(key, defValue);
	}

	public static float getFloat(Context context, String key, Float defValue) {
		return getSharePreferences(context).getFloat(key, defValue);
	}

	public static long getLong(Context context, String key, Long defValue) {
		return getSharePreferences(context).getLong(key, defValue);
	}
}
