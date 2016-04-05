
package com.catdog.adapter;

import java.sql.Date;
import java.text.DecimalFormat;
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

public class Fragment4_Adapter extends BaseAdapter{

	private DeleteClick deleteClick;
	public void setDeleteClick(DeleteClick deleteClick) {
		this.deleteClick = deleteClick;
	}
	public interface DeleteClick {
		public void Deletelistener(int position);
	}

	private AddClick addClick;
	public void setAddClick(AddClick addClick) {
		this.addClick = addClick;
	}
	public interface AddClick {
		public void Addlistener(int position,EditText editText);
	}

	private CutClick cutClick;
	public void setCutClick(CutClick cutClick) {
		this.cutClick = cutClick;
	}
	public interface CutClick {
		public void Cutlistener(int position,EditText editText);
	}

	private List<ShopCartBean> shopCartBeans;
	private LayoutInflater mInflater;
	private Context context;
	private ImageLoader loader;
	private LoadImageHelper imageHelper;
	public Fragment4_Adapter(Context context,List<ShopCartBean> shopCartBeans){
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
		convertView = mInflater.inflate(R.layout.listview_fragment4_list, null);

		TextView fragment4_textview_name = (TextView) convertView.findViewById(R.id.fragment4_textview_name);
		TextView fragment4_textview_price = (TextView) convertView.findViewById(R.id.fragment4_textview_price);
		EditText fragment4_edittext_number = (EditText) convertView.findViewById(R.id.fragment4_edittext_number);
		TextView fragment4_textview_type = (TextView) convertView.findViewById(R.id.fragment4_textview_type);
		ImageView fragment4_imageview_delete = (ImageView) convertView.findViewById(R.id.fragment4_imageview_delete);
		ImageView fragment4_imageview_cut = (ImageView) convertView.findViewById(R.id.fragment4_imageview_cut);
		ImageView fragment4_imageview_add = (ImageView) convertView.findViewById(R.id.fragment4_imageview_add);
		ImageView fragment4_imageview_pic = (ImageView) convertView.findViewById(R.id.fragment4_imageview_pic);
		fragment4_textview_name.setText(shopCartBeans.get(position).getShopcart_name());
		fragment4_edittext_number.setText(shopCartBeans.get(position).getShopcart_number());
		
		double price = Double.valueOf(shopCartBeans.get(position).getShopcart_zkj());
		DecimalFormat df = new DecimalFormat("######0.00");   
		fragment4_textview_price.setText("￥" + df.format(price));
		
		if (shopCartBeans.get(position).getShopcart_type().equals("1")) {
			fragment4_textview_type.setText("库存紧张");
		}else if (shopCartBeans.get(position).getShopcart_type().equals("2")) {
			fragment4_textview_type.setText("下架");
		}else if (shopCartBeans.get(position).getShopcart_type().equals("3")) {
			fragment4_textview_type.setText("有货");
		}
		imageHelper.loadImage(shopCartBeans.get(position).getShopcart_pic(), fragment4_imageview_pic);
		fragment4_imageview_delete.setOnClickListener(new DeleteClickListener(position));
		fragment4_imageview_cut.setOnClickListener(new CutClickListener(position,fragment4_edittext_number));
		fragment4_imageview_add.setOnClickListener(new AddClickListener(position,fragment4_edittext_number));
		
		return convertView;
	}

	public class DeleteClickListener implements OnClickListener {
		private int position;
		public DeleteClickListener(int position){
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			deleteClick.Deletelistener(position);
		}
	}
	
	public class CutClickListener implements OnClickListener {
		private int position;
		private EditText fragment4_edittext_number;
		public CutClickListener(int position,EditText fragment4_edittext_number){
			super();
			this.position = position;
			this.fragment4_edittext_number = fragment4_edittext_number;
		}

		@Override
		public void onClick(View v) {
			cutClick.Cutlistener(position,fragment4_edittext_number);
		}
	}
	
	public class AddClickListener implements OnClickListener {
		private int position;
		private EditText fragment4_edittext_number;
		public AddClickListener(int position,EditText fragment4_edittext_number){
			super();
			this.position = position;
			this.fragment4_edittext_number = fragment4_edittext_number;
		}

		@Override
		public void onClick(View v) {
			addClick.Addlistener(position,fragment4_edittext_number);
		}
	}

}