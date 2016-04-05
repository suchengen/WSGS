package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catdog.adapter.ClassifyAdapter.ViewHolder;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.helper.LoadImageHelper;
import com.catdog.view.MyGridView;
import com.catdog.wsgs.R;

public class MainClassifyAdapter extends BaseAdapter {
	private Context mContext;
	private List<Recordset> mData;
	private LoadImageHelper imageHelper;
	
	public MainClassifyAdapter(Context mContext,List<Recordset> mData){
		this.mContext = mContext;
		this.mData = mData;
		imageHelper = new LoadImageHelper(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		final ViewHolder holder ;
		if(null == convertView)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_main_classify, null);
			holder.tv_title = (TextView) convertView.findViewById(R.id.mainItem_tv);
			holder.img_pic = (ImageView) convertView.findViewById(R.id.mainItem_img);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_title.setText(mData.get(position).getClassname());
		imageHelper.loadImage(mData.get(position).getPicr(), holder.img_pic);
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_title;
		ImageView img_pic;
	}

}
