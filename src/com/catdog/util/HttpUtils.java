package com.catdog.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.catdog.base.MyApplication;
/**
 * @author lxm
 */
public class HttpUtils {
	private static RequestQueue requestQueue;
	private static ProgressDialog pdDialog;
	
	public static void getDataFromVolley(Context context,String url,final Type type,final Map<String,Object> map,final ResponseListener listener){
		requestQueue = MyApplication.getInstance().getRequestQueue();
		StringRequest postRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// 处理返回的JSON数据
				Log.i("HttpUtils","--------------------获取到的json字符串--------------");
				Log.i("HttpUtils",response);
				Log.i("HttpUtils","--------------------获取到的json字符串--------------");
				if(null != type)
					try {
						listener.getResponse(JsonParseUtil.getJsonData(response, type));
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				else
					try {
						listener.getResponse(response);
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				// error
				if(null != pdDialog && pdDialog.isShowing())
				{
					pdDialog.dismiss();
				}
				LogUtil.i("tag", "erro = "+error.toString());
			}
		}) {
			protected Map<String, String> getParams() {
				// POST 参数
				if(null != map)
				{
					Log.i("HttpUtils", "-------------放入的参数-------------");
					Map<String, String> params = new HashMap<String, String>();
					Set<Map.Entry<String, Object>> set = map.entrySet();
					for(Iterator<Map.Entry<String, Object>> it=set.iterator();it.hasNext();)
					{

						Map.Entry<String, Object> entry = it.next();
						params.put(entry.getKey(), (String) entry.getValue());
						LogUtil.i("HttpUtils", entry.getKey() + " = "+(String)entry.getValue());
					}
					Log.i("HttpUtils", "-------------放入的参数-------------");
					return params;
				}
				return null;

			}
		};
		requestQueue.add(postRequest);

	}

	public void setDialog(ProgressDialog pd){
		this.pdDialog = pd;
	}


}
