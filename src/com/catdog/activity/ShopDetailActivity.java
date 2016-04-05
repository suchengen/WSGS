package com.catdog.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.catdog.activity.constant.Constant;
import com.catdog.adapter.ClassifyListAdapter;
import com.catdog.adapter.ImageBannerDetailAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.ClassifyListBean;
import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.bean.ShopCartBean;
import com.catdog.bean.ShopDetailBean;
import com.catdog.helper.LoadImageHelper;
import com.catdog.helper.MySqliteHelper;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.ToastUtil;
import com.catdog.util.URLConstant;
import com.catdog.view.MyGridView;
import com.catdog.view.MyViewPager;
import com.catdog.view.Options;
import com.catdog.wsgs.R;
import com.catdog.wsgs.R.drawable;
import com.catdog.wsgs.R.id;
import com.catdog.wsgs.R.layout;
import com.catdog.wsgs.wxapi.AffirmOrderActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ShopDetailActivity extends BaseActivity implements OnClickListener{
	private String shopId;
	private TextView tv_title,tv_price,tv_guige,tv_xiaoliang,tv_pingfen;
	private TextView tv_kucun,tv_jiedantime,tv_fahuotime,tv_collect;
	private ImageView img_reduce,img_add;
	private TextView tv_num;
	private LinearLayout ll_collect,ll_comment;
	private Button btn_add,btn_buy;
	private List<ShopCartBean> shopCartBeans = new ArrayList<ShopCartBean>();
	private MySqliteHelper helper;
	private SQLiteDatabase db;
	private String[] imagepaths;
	private List<String> imagepath = new ArrayList<String>();
	private LinearLayout ll_pic;
	private MyViewPager main_myviewpager;
	private List<View> imageList = new ArrayList<View>();
	private List<ImageView> icons=new ArrayList<ImageView>();
	private Handler handler = new Handler();
	private LoadImageHelper imageHelper;
	private ImageLoader imageLoader;
	private int currentItem;
	private MyGridView mGridView;
	private ClassifyListAdapter mAdapter;
	private ImageBannerDetailAdapter imageComicAdapter;//首页banner的滑动
	private int currenpage = 1;
	private TextView tv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		initView();
		initListener();
		shopId = (String) getIntent().getStringExtra("shopId");
		if(!isEmpty(shopId))
		{
			requestShopDeatil();
			requestGanxingqu(currenpage);
		}
	}

	private void requestGanxingqu(int currenpage) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", "126");
		map.put("tuijian", "0");
		map.put("row", "10");
		map.put("currenpage", ""+currenpage);
		HttpUtils.getDataFromVolley(mContext, URLConstant.TWO_CLASSIFY, null, map, twoListener);

	}

	private ResponseListener twoListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			ClassifyListBean bean = gson.fromJson(str, ClassifyListBean.class);
			List<Recordsets> list = bean.getRecordset();
			mGridView.setNumColumns(2);
			mAdapter = new ClassifyListAdapter(mContext, list);
			mGridView.setAdapter(mAdapter);
			mGridView.setOnItemClickListener(gridviewClickListener);
		}
	};

	private OnItemClickListener gridviewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext,ShopDetailActivity.class);
			Recordsets bean = (Recordsets) mAdapter.getItem(position);
			intent.putExtra("shopId", bean.getN_id());
			startActivity(intent);
		}
	};

	private void initListener() {
		// TODO Auto-generated method stub
		ll_collect.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		btn_buy.setOnClickListener(this);
		img_add.setOnClickListener(this);
		img_reduce.setOnClickListener(this);
		ll_comment.setOnClickListener(this);
		ll_pic.setOnClickListener(this);
		tv_back.setOnClickListener(this);

	}

	private void initView() {
		tv_back = (TextView) findViewById(R.id.shopDetail_title_text);
		imageHelper = new LoadImageHelper(ShopDetailActivity.this);
		imageLoader = ImageLoader.getInstance();
		ll_pic = (LinearLayout) findViewById(R.id.shopDetail_ll_pic);
		img_reduce = (ImageView) findViewById(R.id.detail_img_reduce);
		img_add = (ImageView) findViewById(R.id.detail_img_add);
		tv_title = (TextView) findViewById(R.id.detail_tv_title);
		tv_price = (TextView) findViewById(R.id.detail_tv_price);
		tv_kucun = (TextView) findViewById(R.id.detail_tv_kucun);
		tv_xiaoliang = (TextView) findViewById(R.id.detail_tv_xiaoliang);
		tv_jiedantime = (TextView) findViewById(R.id.detail_tv_jiedantime);
		tv_fahuotime = (TextView) findViewById(R.id.detail_tv_fahuotime);
		tv_guige = (TextView) findViewById(R.id.detail_tv_guige);
		tv_pingfen = (TextView) findViewById(R.id.detail_tv_pingfen);
		ll_collect = (LinearLayout) findViewById(R.id.detail_ll_collect);
		btn_add = (Button) findViewById(R.id.detail_btn_add);
		btn_buy = (Button) findViewById(R.id.detail_btn_buy);
		tv_collect = (TextView) findViewById(R.id.detail_tv_collect);
		tv_num = (TextView) findViewById(R.id.detail_tv_num);
		ll_comment = (LinearLayout) findViewById(R.id.detail_ll_comment);
		mGridView = (MyGridView) findViewById(R.id.shopDetail_gv);
	}

	private void requestShopDeatil() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n_id", shopId);
		HttpUtils.getDataFromVolley(mContext, URLConstant.SHOP_DETAIL, null, map, detailListener);
	}
	private ShopDetailBean bean;
	private ResponseListener detailListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			String str = (String) object;
			Gson gson = new Gson();
			bean = gson.fromJson(str, ShopDetailBean.class);
			tv_title.setText(bean.getN_title());
			tv_price.setText(Constant.MONEY + bean.getN_zkj());
			tv_guige.setText(bean.getN_zhongliang());
			tv_xiaoliang.setText(bean.getN_xiaoliang());
			String xing = bean.getN_xing();
			float xings = Float.valueOf(xing);
			DecimalFormat df = new DecimalFormat("#.00");
			tv_pingfen.setText(df.format(xings));
			tv_kucun.setText(bean.getN_ding());
			tv_jiedantime.setText(bean.getN_jiedan());
			tv_fahuotime.setText(bean.getN_fahuo());

			String n_fous = bean.getN_focus_key().toString();
			imagepaths = n_fous.split(",");

			for (int i = 0; i < imagepaths.length; i++) {
				if (!imagepaths[i].equals("null")) {
					imagepath.add(imagepaths[i]);
				}
			}

			initHeadView();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(tv_num.getText().toString().trim());
		switch (v.getId()) {
		case R.id.detail_btn_add:
			if (!bean.getN_ding().equals("0")) {

				helper = new MySqliteHelper(ShopDetailActivity.this);
				db = helper.getWritableDatabase();
				shopCartBeans.clear();
				shopCartBeans = getTotalist();

				ContentValues values = new ContentValues();//相当于key为string；类型的map集合
				try {
					//存入数据库
					values.put("goods_id", bean.getN_id());
					values.put("shopcart_code", bean.getShopid());
					values.put("shopcart_name", bean.getN_title());
					values.put("shopcart_scj", bean.getN_scj());
					values.put("shopcart_zkj", bean.getN_zkj());
					values.put("shopcart_zhekou", bean.getN_zhekou());
					values.put("shopcart_number", num+"");
					values.put("shopcart_type", "3");
					values.put("shopcart_zhongliang", bean.getN_zhongliang());
					values.put("shopcart_pinglun", bean.getN_pinglun());
					values.put("shopcart_xing", bean.getN_xing());
					values.put("shopcart_xiaoliang", bean.getN_xiaoliang());
					values.put("shopcart_fahuo", bean.getN_fahuo());
					values.put("shopcart_jiedan", bean.getN_jiedan());
					values.put("shopcart_content", bean.getN_content());
					values.put("shopcart_pic", "http://www.wssxls.com:80/shop/data/images/" + bean.getN_pic());
					values.put("shopcart_focus", bean.getN_focus_key().toString());
					values.put("shopcart_pagetitle", bean.getN_pagetitle());
					values.put("shopcart_keywords", bean.getN_keywords());
					values.put("shopcart_miaoshu", bean.getN_miaoshu());
					values.put("shopcart_parentid", bean.getN_parentid());
					values.put("shopcart_site", bean.getN_site());
					values.put("shopcart_jifen", bean.getN_jifen());
					values.put("shopcart_area", bean.getN_area());
					values.put("shopcart_url", bean.getN_url());
					values.put("shopcart_file", bean.getN_file());
					values.put("shopcart_tuijian", bean.getN_tuijian());
					values.put("shopcart_hits", bean.getN_hits());
					values.put("shopcart_count", bean.getN_count());
					values.put("shopcart_sorting", bean.getN_sorting());
					values.put("shopcart_pass", bean.getN_pass());
					values.put("shopcart_addtime", bean.getN_addtime());
					values.put("shopcart_metype", bean.getN_metype());
					values.put("shopcart_jibie", bean.getN_jibie());
					values.put("shopcart_unit", bean.getN_unit());
					values.put("type", "2008");
					//执行插入语句
					long count = db.insert("cart", null, values);
					int shopcart_number = 0;
					if (count > 0) {
						Toast.makeText(ShopDetailActivity.this, "添加至购物车成功", Toast.LENGTH_SHORT).show();
					}else{
						for (int j = 0; j < shopCartBeans.size(); j++) {
							if (shopCartBeans.get(j).getGoods_id().equals(bean.getN_id())) {
								shopcart_number = Integer.valueOf(shopCartBeans.get(j).getShopcart_number());
							}
						}
						String sql ="update cart set shopcart_number = " + (shopcart_number + num) + " where goods_id = " + bean.getN_id();
						db.execSQL(sql);//表示执行除了查询之外的sql语句
						Toast.makeText(ShopDetailActivity.this, "添加至购物车成功", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {

				}
			}else {
				showToast("库存不足");
			}
			break;
		case R.id.detail_btn_buy://立即购买
			if (!bean.getN_ding().equals("0")) {

				Intent intent = new Intent(ShopDetailActivity.this, AffirmOrderActivity.class);
				intent.putExtra("tag", 2);
				intent.putExtra("or_jiage", bean.getN_zkj());
				intent.putExtra("n_id", bean.getN_id());
				intent.putExtra("n_title", bean.getN_title());
				intent.putExtra("count", num + "");
				intent.putExtra("n_scj", bean.getN_scj());
				intent.putExtra("n_zkj", bean.getN_zkj());
				intent.putExtra("n_pic", "http://www.wssxls.com:80/shop/data/images/" + bean.getN_pic());
				startActivity(intent);
			}else{
				showToast("库存不足");
			}
			break;
		case R.id.detail_ll_collect:
			if(isCollect)
			{
				requestDeleteCollect();
			}else
			{
				requestAddCollect();
			}

			break;

		case R.id.detail_img_add:
			num++;
			tv_num.setText(""+num);
			break;
		case R.id.detail_img_reduce:
			if(num != 1)
			{
				num--;
				tv_num.setText(""+num);
			}else
			{
				Toast toast = Toast.makeText(mContext, "受不了了,宝贝不能再减少了哦!", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			break;

		case R.id.detail_ll_comment:
			Intent intent2 = new Intent(mContext,CommentActivity.class);
			if(bean!=null)
			{
				intent2.putExtra("shop_id", shopId);
				intent2.putExtra("score", bean.getN_xing());
				startActivity(intent2);
			}
			break;

		case R.id.shopDetail_ll_pic:
			Intent in = new Intent(mContext,ShoPicActivity.class);
			in.putExtra("pic", bean.getN_detailurl());
			startActivity(in);
			break;

		case R.id.shopDetail_title_text:
			finish();
			break;
		default:
			break;
		}
	}

	private void requestDeleteCollect() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sc_meid", getUserId());
		map.put("sc_nid",shopId);
		HttpUtils.getDataFromVolley(mContext, URLConstant.DELETE_COLLECT, null, map, deleteListener);
	}

	private ResponseListener deleteListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String msg = object2.getString("msg");
			if(!isEmpty(msg))
			{
				if(msg.equals("success"))
				{
					showToast("取消收藏！");
					isCollect = false;
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sc_item, 0, 0, 0);
				}
			}else
			{
				showToast("取消失败，请稍后再试！");
				isCollect = true;
			}
		}
	};

	private void requestAddCollect() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sc_meid", getUserId());
		map.put("sc_nid",shopId);
		HttpUtils.getDataFromVolley(mContext, URLConstant.ADD_COLLECT, null, map, addCollectListener);
	}
	private boolean isCollect = false;
	private ResponseListener addCollectListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub


			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String msg = object2.getString("msg");
			if(!isEmpty(msg))
			{
				if(msg.equals("success"))
				{
					showToast("收藏成功！");
					isCollect = true;
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sc_item1, 0, 0, 0);
				}
			}else
			{
				showToast("添加失败，请稍后再试！");
				isCollect = false;
			}
		}
	};
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
			shopCartBean.setShopcart_number(cursor.getString(cursor.getColumnIndex("shopcart_number")));

			beans.add(shopCartBean);
		}
		return beans;
	}
	//首页头部  banner  listview的头部
	private void initHeadView(){
		main_myviewpager = (MyViewPager) findViewById(R.id.detail_viewpager_image);
		LinearLayout linear = (LinearLayout) findViewById(R.id.lin_layout_icon);
		imageList.clear();
		imageLoader.init(ImageLoaderConfiguration.createDefault(ShopDetailActivity.this));
		for(int i = 0;i < imagepath.size() - 1;i++){
			ImageView imageview = new ImageView(ShopDetailActivity.this);

			String path = imagepath.get(i);

			ImageLoader.getInstance().displayImage(path, imageview,Options.getListOptions());

			imageview.setScaleType(ScaleType.CENTER_INSIDE);
			imageview.setTag(i);
			imageList.add(imageview);
			ImageView icon = new ImageView(ShopDetailActivity.this);
			icon.setImageResource(R.drawable.icon_gray);
			icon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			icon.setAdjustViewBounds(true);
			linear.addView(icon);
			icons.add(icon);
		}
		icons.get(0).setImageResource(R.drawable.icon_red);

		//main_myviewpager的自动轮询
		handler=new Handler(){//在initView方法中


			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0://main_myviewpager的自动滑动
					currentItem++;//首先当前页加1
					if(currentItem>=imageComicAdapter.getCount()){//如果加1之后大于等于总页数
						currentItem=0;//置0  也就是当main_myviewpager是最后一页时  再次滚动滚动到第0页
					}
					main_myviewpager.setCurrentItem(currentItem);//
					handler.sendEmptyMessageDelayed(0, 5000);//隔5s发送空消息  msg的what为0  所以这是一个死循环
					//从而实现自动滑动
					break;
				case 1:
					ToastUtil.showSortToast(ShopDetailActivity.this,"没有更多数据");
					break;

				default:
					break;
				}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
			}
		};
		imageComicAdapter=new ImageBannerDetailAdapter(imageList,ShopDetailActivity.this);
		imageComicAdapter.setCarousels(imagepath);
		main_myviewpager.setOnPageChangeListener(new OnPageChangeListener() {//轮播图点击事件

			@Override
			public void onPageSelected(int arg0) {
				//main_myviewpager图片底部点的设置和切换
				for (int i = 0; i < icons.size(); i++) {
					icons.get(i).setImageResource(R.drawable.icon_gray);
				}
				icons.get(arg0).setImageResource(R.drawable.icon_red);
				currentItem=arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				main_myviewpager.getParent().requestDisallowInterceptTouchEvent(true);
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		main_myviewpager.setAdapter(imageComicAdapter);
		handler.sendEmptyMessageDelayed(0, 5000);

	}
}
