package com.catdog.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * Gson类库的封装工具类，专门负责解析json数据</br> 内部实现了Gson对象的单例
 */
public class GsonUtil {

	private static Gson gson = null;

	static {
		if (gson == null) {
			gson = new Gson();
			// gson= new GsonBuilder().serializeNulls().create();
		}
	}

	private GsonUtil() {

	}

	public static Gson getGson() {
		return gson;
	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(Date.class,
						new JsonSerializer<Date>() {
							public JsonElement serialize(Date src,
									Type typeOfSrc,
									JsonSerializationContext context) {
								SimpleDateFormat format = new SimpleDateFormat(
										dateformat);
								return new JsonPrimitive(format.format(src));
							}
						}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	// com.google.gson.reflect.TypeToken<List<?>>() {

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {
		List<?> objList = null;
		if (gson != null) {
			try {
				objList = gson.fromJson(jsonStr, type);
			} catch (Exception e) {
				objList = null;
			}
		}
		return objList;
	}

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> ArrayList<T> jsonToList(String jsonStr) {
		ArrayList<T> objList = null;
		if (gson != null) {
			try {
				objList = gson.fromJson(jsonStr, new TypeToken<ArrayList<T>>() {
				}.getType());
			} catch (Exception e) {
				objList = null;
			}
		}
		return objList;
	}

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> LinkedHashSet<T> jsonToLinkHashSet(String jsonStr) {
		LinkedHashSet<T> objList = null;
		if (gson != null) {
			try {
				objList = gson.fromJson(jsonStr,
						new TypeToken<LinkedHashSet<T>>() {
						}.getType());
			} catch (Exception e) {
				objList = null;
			}
		}
		return objList;
	}

	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr,
			java.lang.reflect.Type type) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static <T> T jsonToBean(String jsonStr, Class<T> cl) {
		T obj = null;
		if (gson != null) {
			try {
				obj = gson.fromJson(jsonStr, cl);
			} catch (Exception e) {
				obj = null;
			}
		}
		return obj;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static <T> T jsonToBean(String jsonStr, Type cl) {
		T obj = null;
		if (gson != null) {
			try {
				obj = gson.fromJson(jsonStr, cl);
			} catch (Exception e) {
				obj = null;
			}
		}
		return obj;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl,
			final String pattern) {
		Object obj = null;
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

					public Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String dateStr = json.getAsString();
						try {
							return format.parse(dateStr);
						} catch (ParseException e) {

						}
						return null;
					}
				}).setDateFormat(pattern).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}

}
