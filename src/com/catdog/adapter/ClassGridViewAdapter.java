package com.catdog.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ClassGridViewAdapter<ClassBean> extends ListAdapter<ClassBean> {

	public ClassGridViewAdapter(Context context, List<ClassBean> mList) {
		super(context, mList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
