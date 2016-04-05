package com.catdog.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonParseUtil {


	public static Object getJsonData(String jsonStr, Type type) {
		Gson gson = new Gson();
		Object list = gson.fromJson(jsonStr, type);
		return list;
	}

}
