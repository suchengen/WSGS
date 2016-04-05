package com.catdog.adapter;

import java.util.List;

import com.catdog.activity.constant.Constant;
import com.catdog.adapter.ClassifyListAdapter.ViewHolder;
import com.catdog.bean.CollectBean.Recordset;
import com.catdog.helper.LoadImageHelper;
import com.catdog.wsgs.R;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectAdapter extends BaseAdapter {

	private List<Recordset> mData;
	private Context mContext;
	private LoadImageHelper imageHelper;
	
	public CollectAdapter(Context mContext,List<Recordset> mData)
	{
		this.mContext = mContext;
		this.mData = mData;
		imageHelper = new LoadImageHelper(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(null == view)
		{
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_classify_list, null);
			holder.img_pic = (ImageView) view.findViewById(R.id.two_class_img_pic);
			holder.tv_title = (TextView) view.findViewById(R.id.two_class_tv_title);
			holder.tv_price = (TextView) view.findViewById(R.id.two_class_tv_privce);
			holder.tv_comment = (TextView) view.findViewById(R.id.two_class_tv_comment);
			view.setTag(holder);
		}else
		{
			holder = (ViewHolder) view.getTag();
		}
		imageHelper.loadImage(mData.get(position).getSc_npic(), holder.img_pic);
		holder.tv_price.setText(Constant.MONEY+mData.get(position).getSc_nzkj());
		holder.tv_comment.setText("评价("+mData.get(position).getSc_pinglun()+")");
		holder.tv_title.setText(mData.get(position).getSc_ntitle());
		return view;
	}

	class ViewHolder{
		ImageView img_pic;
		TextView tv_title,tv_price,tv_comment;
	}

}
