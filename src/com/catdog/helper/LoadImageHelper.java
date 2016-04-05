package com.catdog.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
import com.catdog.util.MyTextUtils;
import com.catdog.wsgs.R;

public class LoadImageHelper {
	private static final String TAG = "LoadImageHelper";
	private ImageLoader imageLoader;
	private RequestQueue queue;
	private Context context;
	
	public LoadImageHelper(Context context) {
		this.context = context;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue, MyBitmapCache.getInstance());
	}
	
	public void loadImage(String requestUrl, ImageView imageView){
//		//文件中加载
//		String picFileName = requestUrl.substring(requestUrl.lastIndexOf(File.separator));//图片文件名
//		String picPath = SDCardHelper.getSDCardFilePath(context.getApplicationContext(), Environment.DIRECTORY_DCIM) + picFileName;//本地文件路径
//		Bitmap bmRound = BitmapFactory.decodeFile(picPath);
//		if(bmRound != null){
//			imageView.setImageBitmap(bmRound);
//			return;
//		}
		//网络加载，本地存储一份
		if(!MyTextUtils.isEmpty(requestUrl) && imageView != null){
			imageLoader.get(requestUrl + "", ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher),500,500);
		}
//		SDCardHelper.saveFile(picPath);
	}
	
	static class MyBitmapCache implements ImageCache {
		private static MyBitmapCache imageCache = null;
		private static LruCache<String, Bitmap> lruCache = null;
		
		private MyBitmapCache() {
			int memoryCount = (int) Runtime.getRuntime().maxMemory();
			// 获取剩余内存的8分之一作为缓存
			int cacheSize = memoryCount / 8;
			lruCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		public static MyBitmapCache getInstance() {
			if (imageCache == null) {
				imageCache = new MyBitmapCache();
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
}
