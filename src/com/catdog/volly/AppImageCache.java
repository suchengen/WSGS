package com.catdog.volly;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * @author: 张唯
 * @类 说 明:
 * @version 1.0
 * @创建时间：2014-10-21 上午11:22:41
 * 
 */
public class AppImageCache implements ImageCache {
	private static AppImageCache imageCache;

	static LruCache<String, Bitmap> lruCache;
	static HashMap<String, SoftReference<Bitmap>> hashMap;

	static {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int maxSize = maxMemory / 8;
		hashMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
		lruCache = new LruCache<String, Bitmap>(maxSize) {

			/**
			 * 图片数量
			 */
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};

	}

	private AppImageCache() {

	}

	public static AppImageCache getInstance() {
		if (imageCache == null) {
			imageCache = new AppImageCache();
		}
		return imageCache;
	}

	@Override
	public Bitmap getBitmap(String url) {
		return lruCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		lruCache.put(url, bitmap);
	}
}
