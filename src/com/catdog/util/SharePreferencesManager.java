package com.catdog.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferencesManager {
	private SharedPreferences sharedPreferences;
	@SuppressWarnings("unused")
	private Context context;
	public SharePreferencesManager(Context context, String preferenceName) {
		sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		this.context = context;
	}
	
	public SharePreferencesManager(Context context, String preferenceName, int MODE) {
		sharedPreferences = context.getSharedPreferences(preferenceName, MODE);
	}
	
	public Map<String, ?> getAll() {
		return sharedPreferences.getAll();
	}
	
	public float getFloat(String key, float defValue) {
		return sharedPreferences.getFloat(key, defValue);
	}
	
	public int getInt(String key, int defValue) {
		return sharedPreferences.getInt(key, defValue);
	}
	
	public boolean getBoolean(String key, boolean defValue) {
		return sharedPreferences.getBoolean(key, defValue);
	}
	
	public long getLong(String key, long defValue) {
		return sharedPreferences.getLong(key, defValue);
	}
	
	public String getString(String key, String defValue) {
		return sharedPreferences.getString(key, defValue);
	}
	
	public void setBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void setFloat(String key, float value) {
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public void setInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setLong(String key, long value) {
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public void setString(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 清空所有的参数
	 */
	public void clearAll() {
		sharedPreferences.edit().clear().commit();
	}
	
	/**
	 * 删除某一个值
	 * @param key
	 */
	public void remove(String key){
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
