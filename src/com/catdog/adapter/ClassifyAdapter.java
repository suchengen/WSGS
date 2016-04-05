package com.catdog.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catdog.activity.ClassifyActivity;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.bean.OneClassifyBean.Recordset.Classlist;
import com.catdog.helper.LoadImageHelper;
import com.catdog.view.MyGridView;
import com.catdog.wsgs.R;

public class ClassifyAdapter extends BaseAdapter implements OnClickListener{
	private Context mContext;
	private List<Recordset> mData;
	private LoadImageHelper imageHelper;
	private List<MyGridView> list = new ArrayList<MyGridView>();

	
	public ClassifyAdapter(Context mContext,List<Recordset> mData){
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder ;
		if(null == convertView)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_classify, null);
			holder.gv_class = (MyGridView) convertView.findViewById(R.id.item_class_two);
			holder.tv_name = (TextView) convertView.findViewById(R.id.item_class_title);
			holder.img_pic = (ImageView) convertView.findViewById(R.id.item_class_pic);
			holder.ll_one = (LinearLayout) convertView.findViewById(R.id.class_ll_one);
			holder.img_color = (ImageView) convertView.findViewById(R.id.item_class_color);
			list.add(holder.gv_class);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		imageHelper.loadImage(mData.get(position).getPicr(), holder.img_pic);
		holder.ll_one.setOnClickListener(this);
		imageHelper.loadImage(mData.get(position).getPic(), holder.img_color);
		holder.ll_one.setTag(position);
	    holder.tv_name.setVisibility(View.GONE);
		holder.tv_name.setText(mData.get(position).getClassname());
		holder.gv_class.setAdapter(new GridViewAdapter(mData.get(position).getClasslist()));
		holder.gv_class.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,ClassifyActivity.class);
				intent.putExtra("classify", mData.get(position).getClasslist().get(arg2));
				intent.putExtra("tag", 1000);
				mContext.startActivity(intent);
			}
		});
		return convertView;

	}

	class ViewHolder{

		TextView tv_name;
		ImageView img_pic,img_color;
		MyGridView gv_class;
		LinearLayout ll_one;
	}

	
	class GridViewAdapter extends BaseAdapter{
		private String[] texts ;
		private List<Classlist> classlist;
		
		public GridViewAdapter(List<Classlist> classlist)
		{
			this.classlist = classlist;
			texts = new String[classlist.size()];
			for(int i=0;i<classlist.size();i++)
			{
				texts[i] = classlist.get(i).getClassname();
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return texts.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return classlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
	        TextView tv;  
	        if (convertView == null) {  
	            tv = new TextView(mContext);  
	            tv.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, 80)); 
	            tv.setGravity(Gravity.CENTER);
	        }  
	        else {  
	            tv = (TextView) convertView;  
	        }  
	  
	        tv.setText(texts[position]);  
	        return tv;  
	  
		}
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.class_ll_one:
			int position = (Integer) v.getTag();
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setVisibility(View.GONE);
				if(i==position)
				{
					list.get(i).setVisibility(View.VISIBLE);
				}
			}
			break;

		default:
			break;
		}
	}

}
