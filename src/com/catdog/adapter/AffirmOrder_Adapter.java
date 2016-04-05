
package com.catdog.adapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.catdog.bean.ShopCartBean;
import com.catdog.helper.LoadImageHelper;
import com.catdog.volly.VolleyHepler;
import com.catdog.wsgs.R;

public class AffirmOrder_Adapter extends BaseAdapter{

	//	private OneClick oneClick;
	//	public void setOneClick(OneClick oneClick) {
	//		this.oneClick = oneClick;
	//	}
	//	public interface OneClick {
	//		public void onelistener(int position,List<OrderListItem> listItems);
	//	}
	private List<ShopCartBean> shopCartBeans;
	private LayoutInflater mInflater;
	private Context context;
	private ImageLoader loader;
	private LoadImageHelper imageHelper;
	public AffirmOrder_Adapter(Context context,List<ShopCartBean> shopCartBeans){
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.shopCartBeans = shopCartBeans;
		loader = VolleyHepler.getInstance().getImageLoader();
		imageHelper = new LoadImageHelper(context);
	}
	@Override
	public int getCount() {
		return shopCartBeans.size();
	}
	@Override
	public Object getItem(int position) {
		return shopCartBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.listview_affirmorder_list, null);
		TextView affirmorder_textview_number = (TextView) convertView.findViewById(R.id.affirmorder_textview_number);
		TextView affirmorder_textview_type = (TextView) convertView.findViewById(R.id.affirmorder_textview_type);
		TextView affirmorder_textview_price = (TextView) convertView.findViewById(R.id.affirmorder_textview_price);
		
		ImageView affirmorder_nImage = (ImageView) convertView.findViewById(R.id.affirmorder_nImage);
		imageHelper.loadImage(shopCartBeans.get(position).getShopcart_pic(), affirmorder_nImage);
		
		affirmorder_textview_number.setText("×" + shopCartBeans.get(position).getShopcart_number());
		affirmorder_textview_price.setText("￥" + shopCartBeans.get(position).getShopcart_zkj());
		if (shopCartBeans.get(position).getShopcart_type().equals("1")) {
			affirmorder_textview_type.setText("库存紧张");
		}else if (shopCartBeans.get(position).getShopcart_type().equals("2")) {
			affirmorder_textview_type.setText("下架");
		}else if (shopCartBeans.get(position).getShopcart_type().equals("3")) {
			affirmorder_textview_type.setText("有货");
		}
		return convertView;
	}

}