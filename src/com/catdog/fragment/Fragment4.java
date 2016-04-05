package com.catdog.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.catdog.adapter.Fragment4_Adapter;
import com.catdog.adapter.Fragment4_Adapter.AddClick;
import com.catdog.adapter.Fragment4_Adapter.CutClick;
import com.catdog.adapter.Fragment4_Adapter.DeleteClick;
import com.catdog.bean.ShopCartBean;
import com.catdog.helper.MySqliteHelper;
import com.catdog.wsgs.R;
import com.catdog.wsgs.wxapi.AffirmOrderActivity;


public class Fragment4 extends Fragment implements OnClickListener {
	private View tab4;
	private ListView fragment4_listview_shopcart;
	private TextView fragment4_textview_clean,fragment4_textview_continue,fragment4_listview_number,fragment4_listview_price,fragment4_listview_goto;
	private Fragment4_Adapter fragment4_Adapter;
	private SQLiteDatabase db;
	private MySqliteHelper helper;
	private List<ShopCartBean> shopCartBeans = new ArrayList<ShopCartBean>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tab4 = inflater.inflate(R.layout.main_fragment4, container,false);
		initView();
		initData();
		return tab4;
	}
	@Override
	public void onStart() {
		super.onStart();
		loadSQLdata();
	}

	private void initView() {
		fragment4_listview_shopcart = (ListView) tab4.findViewById(R.id.fragment4_listview_shopcart);

		fragment4_textview_clean = (TextView) tab4.findViewById(R.id.fragment4_textview_clean);
		fragment4_textview_clean.setOnClickListener(this);

		fragment4_textview_continue = (TextView) tab4.findViewById(R.id.fragment4_textview_continue); 
		fragment4_textview_continue.setOnClickListener(this);

		fragment4_listview_number = (TextView) tab4.findViewById(R.id.fragment4_listview_number); 

		fragment4_listview_price = (TextView) tab4.findViewById(R.id.fragment4_listview_price); 

		fragment4_listview_goto = (TextView) tab4.findViewById(R.id.fragment4_listview_goto); 
		fragment4_listview_goto.setOnClickListener(this);
	}

	private void initData() {
		helper = new MySqliteHelper(getActivity());
		db = helper.getWritableDatabase();
		loadSQLdata();
//		for (int i = 1; i < 4; i++) {
//			
////			helper = new MySqliteHelper(getActivity());
////			SQLiteDatabase db = helper.getWritableDatabase();
//			ContentValues values = new ContentValues();//相当于key为string；类型的map集合
//			try {
//				//存入数据库
//				values.put("goods_id", i + "");
//				values.put("shopcart_name", i + "" +i + ""+i+i+i+i+i+i);
//				values.put("shopcart_zkj", i + "0.0");
//				values.put("shopcart_number", i + "");
//				values.put("shopcart_type", 3 + "");
//				values.put("type", 2008 + "");
//				//执行插入语句
//				long count = db.insert("cart", null, values);
//				if (count > 0) {
//					Toast.makeText(getActivity(), "添加数据库成功", Toast.LENGTH_SHORT).show();
//				}else{
//					int shopcart_number = Integer.valueOf(shopCartBeans.get(i-1).getShopcart_number());
//					String sql ="update cart set shopcart_number = " + (shopcart_number + i) + " where goods_id = " + i;
//					db.execSQL(sql);//表示执行除了查询之外的sql语句
////					loadSQLdata();
//					Toast.makeText(getActivity(), "添加数据库成功++++", Toast.LENGTH_SHORT).show();
//				}
//			} catch (Exception e) {
////				int shopcart_number = Integer.valueOf(shopCartBeans.get(i-1).getShopcart_number());
////				String sql ="update cart set shopcart_number = " + (shopcart_number + i) + " where goods_id = " + i;
////				db.execSQL(sql);//表示执行除了查询之外的sql语句
//////				loadSQLdata();
////				Toast.makeText(getActivity(), "添加数据库成功-----", Toast.LENGTH_SHORT).show();
//			}
//			
////			String sql1 ="insert into cart (goods_id,shopcart_name,shopcart_price,shopcart_number,shopcart_type,type) values " +
////					"(" + i + "," + i + i + i + i + i + "," + i + "0.0," + i + ",3,2008)";
////			db.execSQL(sql1);//表示执行除了查询之外的sql语句
//		}
//
//		loadSQLdata();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment4_textview_clean://清空
			if (shopCartBeans.size() != 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("提示");
				builder.setMessage("确定要清空购物车么?");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {
								String sql ="delete from cart where type = 2008";
								db.execSQL(sql);//表示执行除了查询之外的sql语句
								Toast.makeText(getActivity(), "清空成功", Toast.LENGTH_SHORT).show();
								loadSQLdata();
							}

						});
				builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			}else{
				Toast.makeText(getActivity(), "购物车无数据", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.fragment4_textview_continue://继续购物

			break;
		case R.id.fragment4_listview_goto://去结算
			if (shopCartBeans.size() != 0) {
				Intent intent = new Intent(getActivity(),AffirmOrderActivity.class);
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "购物车无数据", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	/**
	 * 加载数据库数据
	 */
	
	private void loadSQLdata() {
		shopCartBeans.clear();
		shopCartBeans = getTotalist();
		fragment4_listview_number.setText("总共" + shopCartBeans.size() + "件商品");
		double price = 0.0;
		if (shopCartBeans.size() != 0) {
			for (int i = 0; i < shopCartBeans.size(); i++) {
				if (!shopCartBeans.get(i).getShopcart_type().equals("2")) {
					price = price + Double.valueOf(shopCartBeans.get(i).getShopcart_zkj()) * Double.valueOf(shopCartBeans.get(i).getShopcart_number());
				}else{
					fragment4_listview_number.setText("总共" + (shopCartBeans.size() - i) + "件商品");
				}
			}
		}
		DecimalFormat df = new DecimalFormat("######0.00");   
		fragment4_listview_price.setText(df.format(price) + "");

		fragment4_Adapter = new Fragment4_Adapter(getActivity(),shopCartBeans);
		fragment4_Adapter.setDeleteClick(new DeleteClick() {
			
			@Override
			public void Deletelistener(final int position) {
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("提示");
				builder.setMessage("确定要删除此商品么?");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {
								String goods_id = shopCartBeans.get(position).getGoods_id();
								String sql ="delete from cart where goods_id = " + goods_id;
								db.execSQL(sql);//表示执行除了查询之外的sql语句
								loadSQLdata();
							}

						});
				builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			}
		});
		fragment4_Adapter.setAddClick(new AddClick() {
			
			@Override
			public void Addlistener(int position, EditText editText) {
				String goods_id = shopCartBeans.get(position).getGoods_id();
				int shopcart_number = Integer.valueOf(shopCartBeans.get(position).getShopcart_number());
				shopcart_number++;
				String sql ="update cart set shopcart_number = " + shopcart_number + " where goods_id = " + goods_id;
				db.execSQL(sql);//表示执行除了查询之外的sql语句
				loadSQLdata();
			}
		});
		fragment4_Adapter.setCutClick(new CutClick() {
			
			@Override
			public void Cutlistener(int position, EditText editText) {
				int shopcart_number = Integer.valueOf(shopCartBeans.get(position).getShopcart_number());
				if (shopcart_number == 1) {
					Toast toast = Toast.makeText(getActivity(), "受不了了,宝贝不能再减少了哦!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else if (shopcart_number > 0) {
					String goods_id = shopCartBeans.get(position).getGoods_id();
					shopcart_number--;
					String sql ="update cart set shopcart_number = " + shopcart_number + " where goods_id = " + goods_id;
					db.execSQL(sql);//表示执行除了查询之外的sql语句
					loadSQLdata();
				}
			}
		});
		fragment4_Adapter.notifyDataSetChanged();
		fragment4_listview_shopcart.setAdapter(fragment4_Adapter);
	}

	/**
	 * 读数据库
	 */
	public List<ShopCartBean> getTotalist(){
		List<ShopCartBean> beans = new ArrayList<ShopCartBean>(); 
		db = helper.getWritableDatabase();
		Cursor cursor = db.query("cart", null, null, null, null, null, null);
		while(cursor.moveToNext()){
			ShopCartBean shopCartBean = new ShopCartBean();

			shopCartBean.setGoods_id(cursor.getString(cursor.getColumnIndex("goods_id")));
			shopCartBean.setShopcart_name(cursor.getString(cursor.getColumnIndex("shopcart_name")));
			shopCartBean.setShopcart_zkj(cursor.getString(cursor.getColumnIndex("shopcart_zkj")));
			shopCartBean.setShopcart_number(cursor.getString(cursor.getColumnIndex("shopcart_number")));
			shopCartBean.setShopcart_type(cursor.getString(cursor.getColumnIndex("shopcart_type")));
			shopCartBean.setShopcart_pic(cursor.getString(cursor.getColumnIndex("shopcart_pic")));
			shopCartBean.setType(cursor.getString(cursor.getColumnIndex("type")));

			beans.add(shopCartBean);
		}
		return beans;
	}

}
