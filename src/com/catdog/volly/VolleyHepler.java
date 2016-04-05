package com.catdog.volly;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.catdog.base.MyApplication;

/**
 * @author: 张唯
 * @类 说 明:
 * @version 1.0
 * @创建时间：2014-10-21 上午11:10:49
 * 
 */
public class VolleyHepler {
	private static VolleyHepler hepler;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	private VolleyHepler() {
		queue = getRequestQueue();
		imageLoader = new ImageLoader(queue, AppImageCache.getInstance());
	}

	public static VolleyHepler getInstance() {
		if (hepler == null) {
			hepler = new VolleyHepler();
		}
		return hepler;
	}

	public RequestQueue getRequestQueue() {
		if (queue == null) {
			queue = Volley.newRequestQueue(MyApplication.getAppliction());
		}
		return queue;
	}

	public <T> void addRequest(Request<T> request) {
		getRequestQueue().add(request);
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}
}
