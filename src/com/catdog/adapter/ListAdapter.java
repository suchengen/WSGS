package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter {

	private List<T> mList;

	private Context context;

	public ListAdapter(Context context, List<T> mList) {
		this.context = context;
		this.mList = mList;
	}

	public Context getContext() {
		return this.context;
	}

	public List<T> getList() {
		return this.mList;
	}

	@Override
	public int getCount() {
		return null == mList ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null == mList ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
