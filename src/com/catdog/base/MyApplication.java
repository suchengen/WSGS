package com.catdog.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.catdog.util.LogUtil;

public class MyApplication extends Application {
	private static MyApplication appliction;
	private List<Activity> activities = new ArrayList<Activity>();
	private RequestQueue mRequestQueue;
	@Override
	public void onCreate() {
		super.onCreate();
		appliction = this;

	}

	public static synchronized MyApplication getInstance() {
		return appliction;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public static MyApplication getAppliction() {
		return appliction;
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = newRequestQueue(getApplicationContext(), null);
		}
		return mRequestQueue;
	}

	private void clear() {
		File cacheDir = new File(this.getCacheDir(), "volley");
		LogUtil.i("application", "--------------------"+cacheDir.getAbsolutePath());
		DiskBasedCache cache = new DiskBasedCache(cacheDir);
		mRequestQueue.start();

		// clear all volley caches.
		mRequestQueue.add(new ClearCacheRequest(cache, null));
	}
	

   public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), "volley");

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);            userAgent = packageName + "/" + info.versionCode;
       } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
               stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }





}
