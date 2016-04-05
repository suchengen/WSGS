package com.catdog.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catdog.bean.ClassListBean;
import com.catdog.wsgs.R;
import com.lidroid.xutils.BitmapUtils;

public class MainFragmentClassListAdapter extends ListAdapter<ClassListBean> {

	private BitmapUtils bitmapUtils;

	public MainFragmentClassListAdapter(Context context,
			ArrayList<ClassListBean> mList, BitmapUtils bitmapUtils) {
		super(context, mList);
		this.bitmapUtils = bitmapUtils;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (null == convertView) {
			holder = new Holder();
			if (position % 2 == 0) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.item_main_fragment_classify_left, null);
			} else {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.item_main_fragment_classify_right, null);
			}

			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_title);
			holder.tvFirstName = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_name1);
			holder.tvSecondName = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_name2);
			holder.tvFirstPrice = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_price1);
			holder.tvSecondPrice = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_price2);
			holder.tvBottomOne = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_buttom1);
			holder.tvBottomTwo = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_buttom2);
			holder.tvBottomThree = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_buttom3);
			holder.tvBottomFour = (TextView) convertView
					.findViewById(R.id.tv_main_fragment_buttom4);
			holder.ivFirstImg = (ImageView) convertView
					.findViewById(R.id.iv_main_fragment_second_image1);
			holder.ivSecondImg = (ImageView) convertView
					.findViewById(R.id.iv_main_fragment_second_image2);
			holder.ivAdImg = (ImageView) convertView
					.findViewById(R.id.iv_main_fragment_ad);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		ClassListBean bean = getList().get(position);
		holder.tvTitle.setText(bean.getClassname());
		holder.tvFirstName.setText(bean.getNewslist().get(0).getN_title());
		holder.tvSecondName.setText(bean.getNewslist().get(1).getN_title());
		holder.tvFirstPrice.setText("￥" + bean.getNewslist().get(0).getN_zkj());
		holder.tvSecondPrice
				.setText("￥" + bean.getNewslist().get(1).getN_zkj());
		int size = bean.getClasslist().size();
		if (size < 4) {
			holder.tvBottomFour.setVisibility(View.INVISIBLE);
		} else {
			holder.tvBottomFour.setVisibility(View.VISIBLE);
			holder.tvBottomFour.setText(R.string.more);
		}
		if (size < 3) {
			holder.tvBottomThree.setVisibility(View.INVISIBLE);
		} else {
			holder.tvBottomThree.setVisibility(View.VISIBLE);
			holder.tvBottomThree.setText(bean.getClasslist().get(2)
					.getClassname());
		}
		if (size < 2) {
			holder.tvBottomTwo.setVisibility(View.INVISIBLE);
		} else {
			holder.tvBottomTwo.setVisibility(View.VISIBLE);
			holder.tvBottomTwo.setText(bean.getClasslist().get(1)
					.getClassname());
		}
		if (size < 1) {
			holder.tvBottomOne.setVisibility(View.INVISIBLE);
		} else {
			holder.tvBottomOne.setVisibility(View.VISIBLE);
			holder.tvBottomOne.setText(bean.getClasslist().get(0)
					.getClassname());
		}
		if (null != bean.getNewslist().get(0).getN_pic()) {
			bitmapUtils.display(holder.ivFirstImg, bean.getNewslist().get(0)
					.getN_pic());
		}
		if (null != bean.getNewslist().get(1).getN_pic()) {
			bitmapUtils.display(holder.ivSecondImg, bean.getNewslist().get(1)
					.getN_pic());
		}
		return convertView;
	}

	public class Holder {
		TextView tvTitle;
		TextView tvFirstName;
		TextView tvSecondName;
		TextView tvFirstPrice;
		TextView tvSecondPrice;
		TextView tvBottomOne;
		TextView tvBottomTwo;
		TextView tvBottomThree;
		TextView tvBottomFour;
		ImageView ivFirstImg;
		ImageView ivSecondImg;
		ImageView ivAdImg;
	}

}
