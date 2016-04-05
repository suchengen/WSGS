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

public class ImageBannerAdapter extends PagerAdapter  implements OnClickListener{

	private List<View> list;
	private List<Flash_info> flash_infos;
	public List<Flash_info> getCarousels() {
		return flash_infos;
	}

	public void setCarousels(List<Flash_info> flash_infos) {
		this.flash_infos = flash_infos;
	}

	private Context context;

	public ImageBannerAdapter(List<View> list,Context context) {
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
		Uri uri = Uri.parse(flash_infos.get(position).getAd_url()); 
		Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
		context.startActivity(intent);
//		int flash_id = flash_infos.get(position).getFlash_id();
//		int flash_type = flash_infos.get(position).getFlash_type();
//		int goods_id = flash_infos.get(position).getGoods_id();
//		if (flash_type == 1) {//商品详情
//			Intent intent = new Intent(context, GoodsInfoActivity.class);
//			intent.putExtra("goods_id", goods_id);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//			context.startActivity(intent);
//		}else if (flash_type == 2) {//活动详情
//			Intent intent = new Intent(context, ActionActivity.class);
//			intent.putExtra("flash_id", flash_id);
//			intent.putExtra("flash_type", flash_type);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//			context.startActivity(intent);
//		}else if (flash_type == 3) {//B2C商品首页
//			Intent intent = new Intent(context, B2CMainActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//			intent.putExtra("flash_id", flash_id);
//			intent.putExtra("flash_type", flash_type);
//			context.startActivity(intent);
//		}
	}
}
