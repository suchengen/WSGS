package com.catdog.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * This class wraps around Android Log class to add additional thread name/id
 * information is desirable.
 */
public class LogUtil {

	public static boolean DEBUG_MODE = true;

	public static String getPrefix() {
		// Show thread info if in debugging mode
		if (DEBUG_MODE)
			return "[" + Thread.currentThread().getName() + "-"
					+ Thread.currentThread().getId() + "] ";
		else
			return "";
	}

	public static void e(String tag, String message) {
		if (DEBUG_MODE)
			Log.e(tag, getPrefix() + message);
	}

	public static void e(String tag, String message, Exception e) {
		if (DEBUG_MODE)
			Log.e(tag, getPrefix() + message, e);
	}

	public static void w(String tag, String message) {
		if (DEBUG_MODE)
			Log.w(tag, getPrefix() + message);
	}

	public static void i(String tag, String message) {
		if (DEBUG_MODE)
			Log.i(tag, getPrefix() + message);
	}

	public static void d(String tag, String message) {
		if (DEBUG_MODE)
			Log.d(tag, getPrefix() + message);
	}

	public static void v(String tag, String message) {
		if (DEBUG_MODE)
			Log.v(tag, getPrefix() + message);
	}

	public static Toast mToast;

	public static void showInstanceToast(int resId, Context context) {
		if (null == mToast || mToast.getView().getContext() != context) {
			mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}

	public static void showInstanceToast(String message, Context context) {
		if (null == mToast || mToast.getView().getContext() != context) {
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(message);
		}
		mToast.show();
	}
}
