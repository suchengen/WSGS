package com.catdog.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {
	private static final String DB_NAME="ShopCart_db1";//数据库的名称
	private static final int VERSION = 1;//数据库的版本
	
	public MySqliteHelper(Context context) {
		super(context,DB_NAME,null,1);
	}
	
	/**
	 * 表示当数据库创建的时候回调的方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql ="create table cart(goods_id varchar(30) primary key," +
				"shopcart_code varchar(30)," + 			//shop_id										暂且不用
				"shopcart_name varchar(30)," + 			//n_title
				"shopcart_scj varchar(30)," + 			//n_scj:			市场价 						暂且不用
				"shopcart_zkj varchar(30)," + 			//n_zkj:			折扣价
				"shopcart_zhekou varchar(30)," + 		//n_zhekou:			折扣
				"shopcart_number varchar(30)," + 		//自填				商品数量
				"shopcart_type varchar(30)," + 			//n_ding:			1:库存紧张 2:下架 3:有货
				"shopcart_zhongliang varchar(30)," +	//n_zhongliang:		规格
				"shopcart_pinglun varchar(30)," + 		//n_pinglun:		评论数量
				"shopcart_xing varchar(30)," + 			//n_xing:			商品评分
				"shopcart_xiaoliang varchar(30)," + 	//n_xiaoliang:		销量
				"shopcart_fahuo varchar(30)," + 		//n_fahuo:			发货时间
				"shopcart_jiedan varchar(30)," + 		//n_jiedan:			结单时间
				"shopcart_content varchar(1000)," + 	//n_content:		介绍 	一坨一坨的
				"shopcart_pic varchar(300)," + 			//n_pic:			缩略图   	1448353075192.jpg
				"shopcart_focus varchar(3000)," + 		//n_focus:			焦点图 	多个时用逗号隔开
				"shopcart_pagetitle varchar(30)," +
				"shopcart_keywords varchar(30)," +
				"shopcart_miaoshu varchar(30)," +
				"shopcart_parentid varchar(30)," +
				"shopcart_site varchar(30)," +
				"shopcart_jifen varchar(30)," +
				"shopcart_area varchar(30)," +
				"shopcart_url varchar(30)," +
				"shopcart_file varchar(30)," +
				"shopcart_tuijian varchar(30)," +
				"shopcart_hits varchar(30)," +
				"shopcart_count varchar(30)," +
				"shopcart_sorting varchar(30)," +
				"shopcart_pass varchar(30)," +
				"shopcart_addtime varchar(30)," +
				"shopcart_metype varchar(30)," +
				"shopcart_jibie varchar(30)," + 		//n_jibie:			A
				"shopcart_unit varchar(30)," + 			//n_unit:			份
				"type varchar(5))";						//自填				默认为2008
		
//		
//		    "shopid": "10000078",
		    
		db.execSQL(sql);//表示执行除了查询之外的sql语句
	}
	/**
	 * 表示当数据库版本更新时回调的方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion != oldVersion){//说明数据库版本发生变化
			System.out.println("数据库版本变化了！");
		}
	}
	/**
	 * 当数据库打开的时候回调的方法
	 */
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
		System.out.println("数据库打开了");
	}
}
