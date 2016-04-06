package com.catdog.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.wsgs.R;
import com.lidroid.xutils.BitmapUtils;

public class ProductDetailGridAdapter extends ListAdapter<Recordsets> {

	private BitmapUtils bitmapUtils;

	public ProductDetailGridAdapter(Context context, List<Recordsets> mList,
			BitmapUtils bitmapUtils) {
		super(context, mList);
		this.bitmapUtils = bitmapUtils;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (null == convertView) {
			holder = new Holder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_product_detail_grid, null);
			holder.ivIntro = (ImageView) convertView
					.findViewById(R.id.iv_product_detail_grid_intro);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_product_detail_grid_name);
			holder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_product_detail_grid_price);
			holder.ibAddCart = (ImageView) convertView
					.findViewById(R.id.ib_product_detail_grid_add_cart);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Recordsets bean = getList().get(position);
		holder.tvName.setText(bean.getN_title());
		holder.tvPrice.setText("￥" + bean.getN_zkj());
		bitmapUtils.display(holder.ivIntro, bean.getN_pic());

		holder.ibAddCart.setOnClickListener(new ClickListener());

		return convertView;
	}

	public class ClickListener implements OnClickListener {

		public ClickListener() {
		}

		@Override
		public void onClick(View v) {
			System.out.println("666");
		}
	}

	public class Holder {
		ImageView ivIntro;
		TextView tvName;
		TextView tvPrice;
		ImageView ibAddCart;
	}

}
