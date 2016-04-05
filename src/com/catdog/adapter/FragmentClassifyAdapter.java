package com.catdog.adapter;

import java.util.List;

import com.catdog.bean.OneClassifyBean.Recordset;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class FragmentClassifyAdapter extends ListAdapter<Recordset> {

	private BitmapUtils bitmapUtils;

	public FragmentClassifyAdapter(Context context, List<Recordset> mList, BitmapUtils bitmapUtils) {
		super(context, mList);
		this.bitmapUtils = bitmapUtils;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
