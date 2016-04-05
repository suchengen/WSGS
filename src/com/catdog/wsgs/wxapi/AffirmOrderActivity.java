package com.catdog.wsgs.wxapi;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.activity.AddressListActivity;
import com.catdog.activity.order.PSTimeActivity;
import com.catdog.activity.order.PayActivity;
import com.catdog.activity.order.SiteActivity;
import com.catdog.adapter.AffirmOrder_Adapter;
import com.catdog.alipay.PayResult;
import com.catdog.alipay.SignUtils;
import com.catdog.base.BaseActivity;
import com.catdog.bean.AddressBean;
import com.catdog.bean.ShopCartBean;
import com.catdog.helper.MySqliteHelper;
import com.catdog.util.HttpUtils;
import com.catdog.util.LogUtil;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 订单确认
 * 1.地址选择
 */
public class AffirmOrderActivity extends BaseActivity implements OnClickListener,IWXAPIEventHandler{
	private ListView order_listview_list;
	private TextView tv_shopping;
	private TextView affirmorder_textview_back;
	private TextView affirmorder_textview_name,affirmorder_textview_phone,affirmorder_textview_address;
	private TextView affirmorder_textview_allprice,affirmorder_textview_discount,affirmorder_textview_money;
	private LinearLayout affirmorder_ll_pstime,affirmorder_ll_pay,affirmorder_ll_address,affrimeorder_ll_site;
	private TextView affirmorder_textview_pstime,affirmorder_textview_pay,affirmorder_textview_site;
	private Button affirmorder_button_okorder;
	private SQLiteDatabase db;
	private MySqliteHelper helper;
	private List<ShopCartBean> shopCartBeans = new ArrayList<ShopCartBean>();
	private AffirmOrder_Adapter affirmOrder_Adapter;
	private double 商品总额 = 0.00;
	private double 优惠金额 = 0.00;
	private double 应付金额 = 0.00;
	private String 配送时间 = "null";//
	private String 地址id = "null";
	private int 支付方式id = 100;//
	private int 配送分店id = 0;//
	private String 订单号;
	private String 订单id;
	private MyProgressDialog pDialog,pDialog1;
	private String 当前地址经度lat;
	private String 当前地址纬度lng;
	private boolean weixinboolean = false;
	private Boolean 返回值 = false;//true订单生成成功,false失败
	private int tag = 1;//判断是不是立即下单过来的，立即下单过来是2;

	final IWXAPI wxapi = WXAPIFactory.createWXAPI(this, null);
	PayReq req;
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private GetPrepayIdTask getPrepayId;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;

	//商户PID
	public static final String PARTNER = "2088121427796470";
	//商户收款账号
	public static final String SELLER = "915433888@qq.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALBcIxZIjMnCkPBNboCOa/QJ+UW4dWTZyCcr+giIeiuxKtmY8M9fV71y3owgqyw/h3nUtnUR7c3YHTdchZYdiJnBg9BrS7TAEuhIOIeWruDgDwtZSHWTXZm1UuTU94rdE5uNZTL6nwv75V8qsIiaPWnMuxgCcRYx3d4jESler/mFAgMBAAECgYBAToh4wqGbnDevd/yYIdaZWN2tH5Km4IaMSgvU+rbZobOHPq5oX1KmAPHHVM41x0CGGxsg2xXEXk5g9nq7fy9kjgxN5taI3fWzlVU9nUsigJ3vMMNGj3jTgn6T7AmCmLiS+u9gBNe8C+iZD5uGAjul35k3tCfQnsUeojT2EzdCrQJBAONg3/VdIxdUxYMigUqPkDZY5yDgNrbbSWgBYAPNsAtdw1sLTBARcCquAthL1molFkehlTXM2HS88hPlP1k4nxsCQQDGjzkk+MgCgjFZcoHk4yT1QJq8z3rVXAxepopkSUohu2Ac/IJ7nZ1SItBpnLH0beXPIj0OW6pZFgXlCEfnsTPfAkEA4vD2twudkZTPieHvj36YnlW/YkXG9l0J+B+fOEycT8vmxsEQytzMyhxhxIZ02/5mLVpt9YOd624GY6skviVQTwJASqP0W02aAjdmzbUZ1yz766XJhUiJaEMd80avp1tVnrCNRNg3RLjPNt0Xc1wQGU5moSLByO/0bWC1/o7KWPJoCQJBAKFHxNSZV574BOj2A8+LPSoCvaFeio34MF+L0DCFe+MuX/kJxHWhfP/CzVAwuMha8PE+YfQnMdC47SIT7310Cws=";
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwXCMWSIzJwpDwTW6Ajmv0CflFuHVk2cgnK/oIiHorsSrZmPDPX1e9ct6MIKssP4d51LZ1Ee3N2B03XIWWHYiZwYPQa0u0wBLoSDiHlq7g4A8LWUh1k12ZtVLk1PeK3RObjWUy+p8L++VfKrCImj1pzLsYAnEWMd3eIxEpXq/5hQIDAQAB";
	//	public static final String PARTNER = "2088021645203733";
	//	//商户收款账号
	//	public static final String SELLER = "sun_sun7@aliyun.com";
	//	//商户私钥，pkcs8格式
	//	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALgtoTi5V6lsObK1+8k13JW0aY9RbDXWJeqv+gLHEL8XDks/CTOzS1j6IX4xIPnhXF8OAB2xAkwI0C/hb7jgXKw9HX+3CWralG63rqJRRWSXT1A2bZ3hop0jrH5yf4NbndMC8woFE2+peHpPuGyvy+4Mg7dv5w6WOIo0QDdOYKkLAgMBAAECgYEAtt/P0lPuH/KvA6zA+hP6F8eQfNa7HGWrNnFPTEy+aw8IiVxo1aX0bb/HW9aKSLMlFdj/8ntcrYSeurwcQ7S7Ljc0oLAY6ZSc3U8Ibdqg9+kfrHcRTb69BW3Gjy6+4cJIjBS0TmjQycIJzr58J1F0NSlRuvDlDrTI2kOqu6ZvobECQQDad+xm96X43y7JAgMnw4MMpcXFHxFvU6C65e2W+3GeqBux+PCVWmczoRQCs8AehvFT7lUMGowA4d8DP6ySi1BZAkEA19GmIWC4/7EIYlAGzucxO7MlOYDanaSYEoKO5qwuyFL2mS1K6RRR/P9Go3Yd77M/hd/Hrl5gzGSufHD7xx14AwJAY9NMaDhc1H1WqW6L9o11wj+1QL9TfiUeWohZqhW28yIplYcEl5o5cfP2as2ryccrWoiav+8ZbgXHm3pq5oNV8QJAErHhbzC+tb9zF3kRLekO9bnt9o434cH5Plk64bZ2kvnEzL4EqC4PcBf42X4I19OyI/mfhXY9clciWjs7RCNEWwJBALosRtmHKWeglWoHcMMJYQ699YI5mH46CdXS5qUgGxCKy0J0YSwLPb2mOUTISPHNszf0zvMZLv1FH/9hpfDd+oE=";
	//	//支付宝公钥
	//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4LaE4uVepbDmytfvJNdyVtGmPUWw11iXqr/oCxxC/Fw5LPwkzs0tY+iF+MSD54VxfDgAdsQJMCNAv4W+44FysPR1/twlq2pRut66iUUVkl09QNm2d4aKdI6x+cn+DW53TAvMKBRNvqXh6T7hsr8vuDIO3b+cOljiKNEA3TmCpCwIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				返回值 = true;
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(AffirmOrderActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
					int position = msg.arg1;//点击的listview的下标position
					int status = msg.arg2;
					Intent intent = new Intent(AffirmOrderActivity.this, OrderActivity.class);
					startActivity(intent);
					AffirmOrderActivity.this.finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(AffirmOrderActivity.this, "支付结果确认中",Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(AffirmOrderActivity.this, "支付失败",Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(AffirmOrderActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_affirmorder);
		initView();
		Intent intent = getIntent();
		tag = intent.getIntExtra("tag",1);
		helper = new MySqliteHelper(AffirmOrderActivity.this);
		db = helper.getWritableDatabase();
		if (tag == 2) {
			String or_jiage = intent.getStringExtra("or_jiage");
			String n_id = intent.getStringExtra("n_id");
			String n_title = intent.getStringExtra("n_title");
			String count = intent.getStringExtra("count");
			String n_scj = intent.getStringExtra("n_scj");
			String n_zkj = intent.getStringExtra("n_zkj");
			String n_pic = intent.getStringExtra("n_pic");

			shopCartBeans.clear();

			ShopCartBean shopCartBean = new ShopCartBean();
			shopCartBean.setGoods_id(n_id);
			shopCartBean.setShopcart_name(n_title);
			shopCartBean.setShopcart_zkj(n_zkj);
			shopCartBean.setShopcart_scj(n_scj);
			shopCartBean.setShopcart_number(count);
			shopCartBean.setShopcart_type(3+"");
			shopCartBean.setShopcart_pic(n_pic);
			shopCartBean.setType(2008+"");

			shopCartBeans.add(shopCartBean);

			if (shopCartBeans.size() != 0) {
				for (int i = 0; i < shopCartBeans.size(); i++) {
					商品总额 = 商品总额 + Double.valueOf(shopCartBeans.get(i).getShopcart_zkj()) * Double.valueOf(shopCartBeans.get(i).getShopcart_number());

				}
			}
			//商品总额 截取小数点后两位
			DecimalFormat df = new DecimalFormat("#.00");
			affirmorder_textview_allprice.setText(df.format(商品总额) + "");
			应付金额 = 商品总额 - 优惠金额;

			//应付金额截取小数点后两位
			BigDecimal c = new BigDecimal(应付金额);
			affirmorder_textview_money.setText(df.format(应付金额));

			affirmOrder_Adapter = new AffirmOrder_Adapter(AffirmOrderActivity.this,shopCartBeans);
			affirmOrder_Adapter.notifyDataSetChanged();
			order_listview_list.setAdapter(affirmOrder_Adapter);
			setListViewHeightBasedOnChildren(order_listview_list);

		}else{
			initData();
		}
		requestAddress();


		req = new PayReq();
		sb = new StringBuffer();
		initWXSdk();

	}

	private void initView() {
		order_listview_list = (ListView) findViewById(R.id.affirmorder_listview_list);
		affirmorder_textview_allprice = (TextView) findViewById(R.id.affirmorder_textview_allprice);
		affirmorder_textview_discount = (TextView) findViewById(R.id.affirmorder_textview_discount);
		affirmorder_textview_money = (TextView) findViewById(R.id.affirmorder_textview_money);

		affirmorder_textview_back = (TextView) findViewById(R.id.affirmorder_textview_back);
		affirmorder_textview_back.setOnClickListener(this);

		tv_shopping = (TextView)findViewById(R.id.confirm_tv_shopping);
		tv_shopping.setOnClickListener(this);

		affirmorder_ll_pstime = (LinearLayout) findViewById(R.id.affirmorder_ll_pstime);
		affirmorder_ll_pstime.setOnClickListener(this);
		affirmorder_textview_pstime = (TextView) findViewById(R.id.affirmorder_textview_pstime);

		affirmorder_ll_pay = (LinearLayout) findViewById(R.id.affirmorder_ll_pay);
		affirmorder_ll_pay.setOnClickListener(this);
		affirmorder_textview_pay = (TextView) findViewById(R.id.affirmorder_textview_pay);

		affrimeorder_ll_site = (LinearLayout) findViewById(R.id.affrimeorder_ll_site);
		affrimeorder_ll_site.setOnClickListener(this);
		affirmorder_textview_site = (TextView) findViewById(R.id.affirmorder_textview_site);

		affirmorder_ll_address = (LinearLayout) findViewById(R.id.affirmorder_ll_address);
		affirmorder_ll_address.setOnClickListener(this);

		affirmorder_button_okorder = (Button) findViewById(R.id.affirmorder_button_okorder);
		affirmorder_button_okorder.setOnClickListener(this);

		affirmorder_textview_name = (TextView) findViewById(R.id.affirmorder_textview_name);
		affirmorder_textview_phone = (TextView) findViewById(R.id.affirmorder_textview_phone);
		affirmorder_textview_address = (TextView) findViewById(R.id.affirmorder_textview_address);
	}
	private void initData() {
		loadSQLdata();
	}

	/**
	 * 加载数据库数据
	 */
	private void loadSQLdata() {
		shopCartBeans.clear();
		shopCartBeans = getTotalist();
		if (shopCartBeans.size() != 0) {
			for (int i = 0; i < shopCartBeans.size(); i++) {
				商品总额 = 商品总额 + Double.valueOf(shopCartBeans.get(i).getShopcart_zkj()) * Double.valueOf(shopCartBeans.get(i).getShopcart_number());
			}
		}
		DecimalFormat df1 = new DecimalFormat("#.00");
		affirmorder_textview_allprice.setText(df1.format(商品总额) + "");
		应付金额 = 商品总额 - 优惠金额;
		DecimalFormat    df   = new DecimalFormat("######0.00");  
		LogUtil.i("taga", "money = "+ df.format(应付金额));
		affirmorder_textview_money.setText(df.format(应付金额) + "");

		affirmOrder_Adapter = new AffirmOrder_Adapter(AffirmOrderActivity.this,shopCartBeans);
		affirmOrder_Adapter.notifyDataSetChanged();
		order_listview_list.setAdapter(affirmOrder_Adapter);
		setListViewHeightBasedOnChildren(order_listview_list);
	}

	/**
	 * 获取地址
	 */
	private void requestAddress() {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dz_meid", getUserId());
		HttpUtils.getDataFromVolley(mContext, URLConstant.ADDRESS_LIST, null, map, listener);
	}
	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) {
			// TODO 自动生成的方法存根
			String str = (String) object;
			try {
				JSONObject object2 = new JSONObject(str);
				String msg = object2.getString("msg");
				if(!isEmpty(msg)){
					showToast(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Gson gson = new Gson();
				AddressBean bean = gson.fromJson(str, AddressBean.class);
				if(bean.getRecordset()!=null && bean.getRecordset().size() != 0){
					for (int i = 0; i < bean.getRecordset().size(); i++) {
						if (bean.getRecordset().get(i).getDz_default().equals("1")) {
							当前地址经度lat = bean.getRecordset().get(i).getDz_lat();
							当前地址纬度lng = bean.getRecordset().get(i).getDz_lng();
							地址id = bean.getRecordset().get(i).getDz_id();
							affirmorder_textview_name.setText(bean.getRecordset().get(i).getDz_uname());
							affirmorder_textview_phone.setText(bean.getRecordset().get(i).getDz_tel());
							affirmorder_textview_address.setText(bean.getRecordset().get(i).getDz_sheng() + bean.getRecordset().get(i).getDz_shi() + bean.getRecordset().get(i).getDz_qu() + bean.getRecordset().get(i).getDz_address());
						}
					}
				}else{

				}
			}
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		Bundle datas = data.getExtras();
		if (datas == null) {
			return;
		}
		if (data.equals(null)) {
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);  
		switch (requestCode) {
		case 2000:
			配送时间 = data.getStringExtra("text");
			affirmorder_textview_pstime.setText(配送时间);
			break;
		case 2001:
			String 支付方式 = data.getStringExtra("text");
			支付方式id = data.getIntExtra("text_postion",10);
			LogUtil.i("tag", "支付方式=" +支付方式id);
			affirmorder_textview_pay.setText(支付方式);
			break;
		case 2002:
			地址id = data.getStringExtra("add_id");
			String add_name = data.getStringExtra("add_name");
			String add_sheng = data.getStringExtra("add_sheng");
			String add_shi = data.getStringExtra("add_shi");
			String add_qu = data.getStringExtra("add_qu");
			String add_address = data.getStringExtra("add_address");
			String add_tel = data.getStringExtra("add_tel");
			当前地址纬度lng = data.getStringExtra("add_lng");
			当前地址经度lat = data.getStringExtra("add_lat");
			affirmorder_textview_name.setText(add_name);
			affirmorder_textview_phone.setText(add_tel);
			affirmorder_textview_address.setText(add_sheng + add_shi + add_qu + add_address);
			break;
		case 2003:
			String 商店 = data.getStringExtra("text");
			配送分店id = Integer.valueOf(data.getStringExtra("text_id"));
			affirmorder_textview_site.setText(商店);
			break;
		}
		if (weixinboolean) {
			String msg = "";
			/*
			 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
			 */
			String str = data.getExtras().getString("pay_result");
			if (str.equalsIgnoreCase("success")) {
				msg = "支付成功activity！";
			} else if (str.equalsIgnoreCase("fail")) {
				finish();
				msg = "支付失败！";
			} else if (str.equalsIgnoreCase("cancel")) {
				finish();
				msg = "用户取消了支付";
			}
			showToast(msg);
			Intent intent = new Intent(AffirmOrderActivity.this, OrderActivity.class);
			startActivity(intent);
			AffirmOrderActivity.this.finish();
		} 
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.affirmorder_ll_pstime:
			intent = new Intent(AffirmOrderActivity.this, PSTimeActivity.class);
			startActivityForResult(intent, 2000);
			break;
		case R.id.affirmorder_ll_pay:
			intent = new Intent(AffirmOrderActivity.this, PayActivity.class);
			startActivityForResult(intent, 2001);
			break;
		case R.id.affirmorder_ll_address:
			intent = new Intent(AffirmOrderActivity.this, AddressListActivity.class);
			startActivityForResult(intent, 2002);
			break;
		case R.id.affrimeorder_ll_site:
			intent = new Intent(AffirmOrderActivity.this, SiteActivity.class);
			intent.putExtra("lng", 当前地址纬度lng);
			intent.putExtra("lat", 当前地址经度lat);
			startActivityForResult(intent, 2003);
			break;
		case R.id.affirmorder_button_okorder:
			//			setpay();
			//			submitorder();	
			if (配送时间.equals("null")) {
				showToast("请选择配送时间");
				break;
			}else if (配送分店id == 0) {
				showToast("请选择配送分店");
				break;
			}else if (支付方式id == 100) {
				showToast("请选择支付方式");
				break;
			}else{
				submitorder();	
			}
			break;
		case R.id.affirmorder_textview_back:
			finish();
			break;

		case R.id.confirm_tv_shopping:
			//			intent = new Intent(mContext,ClassifyActivity.class);
			//			intent.putExtra("tag", 1000);
			//			startActivity(intent);
			AffirmOrderActivity.this.finish();
			break;
		}
	}

	/**
	 * 读数据库
	 */
	public List<ShopCartBean> getTotalist(){
		List<ShopCartBean> beans = new ArrayList<ShopCartBean>(); 
		db = helper.getWritableDatabase();
		Cursor cursor = db.query("cart", null, null, null, null, null, null);
		while(cursor.moveToNext()){
			if (!cursor.getString(cursor.getColumnIndex("shopcart_type")).equals("2")) {
				ShopCartBean shopCartBean = new ShopCartBean();

				shopCartBean.setGoods_id(cursor.getString(cursor.getColumnIndex("goods_id")));
				shopCartBean.setShopcart_name(cursor.getString(cursor.getColumnIndex("shopcart_name")));
				shopCartBean.setShopcart_zkj(cursor.getString(cursor.getColumnIndex("shopcart_zkj")));
				shopCartBean.setShopcart_scj(cursor.getString(cursor.getColumnIndex("shopcart_scj")));
				shopCartBean.setShopcart_number(cursor.getString(cursor.getColumnIndex("shopcart_number")));
				shopCartBean.setShopcart_type(cursor.getString(cursor.getColumnIndex("shopcart_type")));
				shopCartBean.setShopcart_pic(cursor.getString(cursor.getColumnIndex("shopcart_pic")));
				shopCartBean.setType(cursor.getString(cursor.getColumnIndex("type")));

				beans.add(shopCartBean);
			}
		}
		return beans;
	}

	private void submitorder() {
		pDialog = new MyProgressDialog(AffirmOrderActivity.this);
		pDialog.setMessage("正在加载");
		pDialog.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=ordersave";
		Map<String, String> params = new HashMap<String, String>();
		params.put("me_id", getUserId());
		params.put("or_jiage", 应付金额 + "");
		params.put("me_dzid", 地址id + "");
		params.put("me_payid", 支付方式id + "");
		params.put("or_peisong", 配送时间);
		params.put("or_site", 配送分店id + "");
		params.put("or_payos", "2");//下单的手机
		params.put("orderdetail", arrayToJsonStr(shopCartBeans));
		params.put("or_content", " ");

		VolleyHttpClient.getInstance(AffirmOrderActivity.this).post(pDialog, url, params, null,null, new Listener<String>() {

			@Override
			public void onResponse(String res) {
				pDialog.dismiss();
				try {
					LogUtil.i("tag", "res = "+res);
					JSONObject obj = new JSONObject(res);
					订单号 = obj.getString("or_trade_no");
					订单id = obj.getString("or_id");//订单号
					String sql ="delete from cart where type = 2008";
					db.execSQL(sql);//表示执行除了查询之外的sql语句
					loadSQLdata();
					if (支付方式id == 0) {//环信支付

					}else if (支付方式id == 1) {//支付宝
						pay();
					}else if (支付方式id == 2) {//微信
						setpay();
						//						getPrepayId.execute();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				pDialog.dismiss();
			}
		});
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	private String arrayToJsonStr(List<ShopCartBean> shopCartBeans) {
		// TODO 自动生成的方法存根
		try {
			JSONArray array = new JSONArray();
			for(int i = 0;i < shopCartBeans.size();i++){
				JSONObject object = new JSONObject();
				object.put("n_id", shopCartBeans.get(i).getGoods_id());
				object.put("n_title", shopCartBeans.get(i).getShopcart_name());
				object.put("count", shopCartBeans.get(i).getShopcart_number());
				object.put("n_scj", shopCartBeans.get(i).getShopcart_scj());
				object.put("n_zkj", shopCartBeans.get(i).getShopcart_zkj());
				array.put(object);
			}
			return array.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	//---------++++++++++++-----------微信++++++++++++++++++---------------+++++++++++++
	private void setpay() {
		pDialog1 = new MyProgressDialog(AffirmOrderActivity.this);
		pDialog1.setMessage("正在获取微信支付信息");
		pDialog1.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=wxpay";
		Map<String, String> params = new HashMap<String, String>();
		params.put("or_id", 订单id);
		VolleyHttpClient.getInstance(AffirmOrderActivity.this).post(pDialog1, url, params, null,null, new Listener<String>() {

			@Override
			public void onResponse(String res) {
				pDialog1.dismiss();
				try {
					LogUtil.i("wx", "wx = "+res);
					JSONObject jsonObject = new JSONObject(res);
					//"wx4d1b505d6020d737
					// wx4d1b505d6020d737
					String appid = jsonObject.getString("appid");//"appid":"wxb641bcc3f9497097"
					String nonceStr = jsonObject.getString("noncestr");//": "a462b76e7436e98e0ed6e13c64b4fd1c",
					String packages = jsonObject.getString("package");//": "Sign=WXPay",
					String partnerId = jsonObject.getString("partnerid");//": "10000100",
					String prepayId = jsonObject.getString("prepayid");//": "1101000000140415649af9fc314aa427",
					String timeStamp = jsonObject.getString("timestamp");//": "1397527777",
					String sign = jsonObject.getString("sign");//": "582282D72DD2B03AD892830965F428CB16E7A256"


					PayReq payreq;
					final IWXAPI msgApi = WXAPIFactory.createWXAPI(AffirmOrderActivity.this, null);
					msgApi.registerApp("wx4d1b505d6020d737");
					payreq = new PayReq();
					payreq.appId = "wx4d1b505d6020d737";
					payreq.nonceStr = nonceStr;
					payreq.packageValue = packages;
					payreq.partnerId = partnerId;
					payreq.prepayId = prepayId;
					payreq.timeStamp = timeStamp;
					payreq.sign = sign;
					payreq.extData = "app data"; //
					msgApi.sendReq(payreq);

					weixinboolean = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				pDialog1.dismiss();
			}
		});
	}
	//---------++++++++++++-----------支         付++++++++++++++++++---------------+++++++++++++
	private void pay() {
		String orderInfo = getOrderInfo("闻氏果蔬", "  ", 应付金额+"");
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(AffirmOrderActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";//不显示

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://www.wssxls.com:80/shop/alipay/notify_url.php" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的余额进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
		//				Locale.getDefault());
		//		Date date = new Date();
		//		String key = format.format(date);
		//
		//		Random r = new Random();
		//		key = key + r.nextInt();
		//		key = key.substring(0, 15);
		return 订单号;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (返回值) {
				Intent intent = new Intent(AffirmOrderActivity.this, OrderActivity.class);
				startActivity(intent);
				this.finish();
			}else{
				this.finish();
			}
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}




	private void initWXSdk() {
		wxapi.registerApp("wx4d1b505d6020d737");
		wxapi.handleIntent(getIntent(), this);
		getPrepayId = new GetPrepayIdTask();
		// getPrepayId.execute();
	}






	/**
	 * 微信支付
	 */
	private class GetPrepayIdTask extends
	AsyncTask<Void, Void, Map<String, String>> {

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			LogUtil.i(TAG, "异步结束");
			LogUtil.i(TAG, "prepay_id = "+result.get("prepay_id"));
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			for (String key : result.keySet()) {
				System.out.println("key= "+ key + " and value= " + result.get(key));
			}
			resultunifiedorder = result;
			genPayReq();

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {
			LogUtil.i(TAG, "异步开始");
			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Map<String, String> xml = decodeXml(content);
			return xml;
		}
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
		}
		return null;

	}

	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", WXConfig.APP_ID));
			packageParams.add(new BasicNameValuePair("body", "玩具租赁"));
			packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
			packageParams.add(new BasicNameValuePair("mch_id",
					WXConfig.Parter_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams
			.add(new BasicNameValuePair("notify_url",
					"http://fz009.gotoip2.com/WxpayAPI_php_v3/example/notify.php"));
			// packageParams.add(new BasicNameValuePair("notify_url",
			// "http://xingtw.com/app/api.php"));
			LogUtil.i(TAG, "支付订单:"+getOutTradeNo1());
			packageParams.add(new BasicNameValuePair("out_trade_no",getOutTradeNo1()));// 支付订单
			// packageParams.add(new BasicNameValuePair("out_trade_no",
			// genOutTradNo()+"_1"));//支付订单
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", ""+应付金额));
			// packageParams.add(new BasicNameValuePair("total_fee", "1"));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);
			return new String(xmlstring.toString().getBytes(), "ISO8859-1");

		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 生成签名
	 */

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXConfig.APP_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		return packageSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		return sb.toString();
	}
	private void genPayReq() {
		LogUtil.i(TAG, "------1");

		req.appId = WXConfig.APP_ID;
		req.partnerId = WXConfig.Parter_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", WXConfig.Parter_ID));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		req.sign = genAppSign(signParams);
		LogUtil.i(TAG, "----sign = "+genAppSign(signParams));
		LogUtil.i(TAG, "timesstamp"+String.valueOf(genTimeStamp()));
		sb.append("sign\n" + req.sign + "\n\n");
		sendPayReq();
	}
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXConfig.APP_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();

		return appSign;
	}


	private String genNonceStr() {
		LogUtil.i(TAG, "2");
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo1() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 10);
		return key;
	}

	private void sendPayReq() {
		wxapi.registerApp(WXConfig.APP_ID);
		LogUtil.i(TAG, "发送");
		wxapi.sendReq(req);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		int IS_SUCCESS = resp.errCode;
		if(IS_SUCCESS == 0){
			LogUtil.i(TAG, "ZHIFU CHENGGONG ");
			finish();
		}
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//			builder.setTitle(R.string.app_tip);
			//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			//			builder.show();
			//			Intent intent = new Intent(mContext,);
			//			startActivity(intent);

		}
	}

}
