package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.catdog.bean.CommentBean.Recordset;
import com.catdog.helper.LoadImageHelper;
import com.catdog.view.RoundImageView;
import com.catdog.wsgs.R;

public class CommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<Recordset> mData;
	private LoadImageHelper imageHelper;

	public CommentAdapter(Context mContext,List<Recordset> mData)
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
			view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_comment, null);

			holder.tv_name = (TextView) view.findViewById(R.id.comment_tv_user);
			holder.tv_content = (TextView) view.findViewById(R.id.comment_tv_content);
			holder.tv_time = (TextView) view.findViewById(R.id.comment_tv_time);
			holder.rimg_pic = (RoundImageView) view.findViewById(R.id.comment_rimg_head);
			holder.comment_ratingbar_star = (RatingBar) view.findViewById(R.id.comment_ratingbar_star);
			view.setTag(holder);
		}else
		{
			holder = (ViewHolder) view.getTag();
		}
		imageHelper.loadImage(mData.get(position).getPl_mepic(), holder.rimg_pic);
		holder.tv_name.setText(mData.get(position).getPl_meuid());
		holder.tv_content.setText(mData.get(position).getPl_content());
		holder.tv_time.setText(mData.get(position).getPl_addtime());
		holder.comment_ratingbar_star.setRating(Float.valueOf(mData.get(position).getPl_xing()));
		return view;
	}

	public class ViewHolder{
		TextView tv_name,tv_content,tv_time;
		RoundImageView rimg_pic;
		RatingBar comment_ratingbar_star;
	}

}
