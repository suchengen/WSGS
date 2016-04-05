package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.catdog.bean.Flash_info;

public class ImageBannerDetailAdapter extends PagerAdapter  implements OnClickListener{

	private List<View> list;
	private List<String> flash_infos;
	public List<String> getCarousels() {
		return flash_infos;
	}

	public void setCarousels(List<String> flash_infos) {
		this.flash_infos = flash_infos;
	}

	private Context context;

	public ImageBannerDetailAdapter(List<View> list,Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null&&!list.isEmpty()?list.size():0;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		container.addView(list.get(position));
		if(flash_infos!=null){
			list.get(position).setOnClickListener(this);
		}
		return list.get(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	@Override
	public void onClick(View arg0) {
		int position=(Integer) arg0.getTag();
	}
}
