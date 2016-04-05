package com.catdog.view;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Options {
	
	private static DisplayImageOptions options;//不缓存到sd中
	private static DisplayImageOptions optionImageList;//缓存到sd中
	private static DisplayImageOptions optionsCircle;//圆形用户头像图片
	private static DisplayImageOptions optionsComment;//不缓存到sd中，默认图片评论头像
	
	public static DisplayImageOptions getListOptions() {
		if(options == null){
			options = new DisplayImageOptions.Builder()
//			// 设置图片在下载期间显示的图片
//			.showImageOnLoading(R.drawable.pic)
//			// 设置图片Uri为空或是错误的时候显示的图片
//			.showImageForEmptyUri(R.drawable.small_image_holder_listpage)
//			// 设置图片加载/解码过程中错误时候显示的图片
//			.showImageOnFail(R.drawable.small_image_holder_listpage)
			.cacheInMemory(true)
			// 设置下载的图片是否缓存在内存中
			.cacheOnDisc(true)
			// 设置下载的图片是否缓存在SD卡中
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
			// .decodingOptions(android.graphics.BitmapFactory.Options
			// decodingOptions)//设置图片的解码配置
			// 设置图片下载前的延迟
			// .delayBeforeLoading(int delayInMillis)//int
			// delayInMillis为你设置的延迟时间
			// 设置图片加入缓存前，对bitmap进行设置
			// 。preProcessor(BitmapProcessor preProcessor)
			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//			 .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
			.displayer(new FadeInBitmapDisplayer(500))// 淡入
			.build();
		}
		return options;
	}
	
}
