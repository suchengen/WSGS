package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catdog.bean.AddressBean.Address;
import com.catdog.wsgs.R;

public class AddressAdapter extends BaseAdapter {
	private Context mContext;
	private List<Address> mData;
	
	public AddressAdapter(Context mContext,List<Address> mData){
		this.mContext = mContext;
		this.mData = mData;
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
	public View getView(int position, View view, ViewGroup parent) {
		// TODO 自动生成的方法存根'
		ViewHolder holder ;
		if(null == view)
		{
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.list_item_ads, null);
			holder.tv_name = (TextView) view.findViewById(R.id.add_tv_name);
			holder.tv_phone = (TextView) view.findViewById(R.id.add_tv_phone);
			holder.tv_add = (TextView) view.findViewById(R.id.add_tv_add);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_name.setText(mData.get(position).getDz_uname());
		holder.tv_phone.setText(mData.get(position).getDz_tel());
		holder.tv_add.setText(mData.get(position).getDz_sheng() + "省" + mData.get(position).getDz_shi() + "市" + mData.get(position).getDz_qu() + "区" + mData.get(position).getDz_address());
		
		return view;
	}
	
	class ViewHolder{
		TextView tv_name,tv_phone,tv_add;
	}

}
