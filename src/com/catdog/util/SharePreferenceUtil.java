package com.catdog.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtil {
	private Editor editor;
	private SharedPreferences sharedPreferences;

	public Editor getEditor() {
		return editor;
	}
	
	public SharePreferenceUtil(Context context,String fileName){
		sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	public String getString(String string){
		return sharedPreferences.getString(string, null);
	}
	
	
	
	public boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, false);
	}
	
	public int getInt(String key){
		return sharedPreferences.getInt(key, 0);
	}
	
	/**
	 * 作用：清除数据
	 * @param eception 不想清除的数据
	 */
	public void clear(List<String> eception){
		Map<String, ?> map = sharedPreferences.getAll();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			if(eception != null && eception.contains(key)){
				continue;	
			}
			editor.remove(key);
		}
		editor.commit();
	}
	/**
	 * 清楚share
	 * @param context
	 * @param key
	 */
	public void remove(Context context, String key) {
		SharedPreferences sPreferences = context.getSharedPreferences(
				StaticParams.PREFS_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static SharePreferenceUtil getDefaultSharedPrefernces() {
		// TODO Auto-generated method stub
		return null;
	}
}
