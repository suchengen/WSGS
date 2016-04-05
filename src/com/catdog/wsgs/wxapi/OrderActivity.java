package com.catdog.wsgs.wxapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;//ke neng you tui huo de gong neng
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.catdog.activity.order.OrderCommentActivity;
import com.catdog.alipay.PayResult;
import com.catdog.alipay.SignUtils;
import com.catdog.base.BaseActivity;
import com.catdog.bean.OrderBean;
import com.catdog.bean.OrderBean.OrderInfoBean;
import com.catdog.bean.OrderDetailBean;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.StringUtils;
import com.catdog.volly.VolleyHepler;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class OrderActivity extends BaseActivity implements OnClickListener,OnRefreshListener2<ListView>{
	private Button myorder_button_1,myorder_button_2,myorder_button_3,myorder_button_4,myorder_button_5,myorder_button_6;
	private Button button[];
	private PullToRefreshListView myorder_listview_orderinfo;
	private int tag = 1;
	private TextView order_textview_back;
	private MyProgressDialog pDialog;
	private Order_Adapter order_Adapter;
	private OrderInfo_Adapter orderInfo_Adapter;
	private OrderBean orderBeans;
	private List<OrderInfoBean> orderInfoBeans = new ArrayList<OrderInfoBean>();
	private OrderDetailBean orderDetailBean;
	private int page = 1;
	private String order_id;

	//商户PID
	public static final String PARTNER = "2088121427796470";
	//商户收款账号
	public static final String SELLER = "915433888@qq.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALBcIxZIjMnCkPBNboCOa/QJ+UW4dWTZyCcr+giIeiuxKtmY8M9fV71y3owgqyw/h3nUtnUR7c3YHTdchZYdiJnBg9BrS7TAEuhIOIeWruDgDwtZSHWTXZm1UuTU94rdE5uNZTL6nwv75V8qsIiaPWnMuxgCcRYx3d4jESler/mFAgMBAAECgYBAToh4wqGbnDevd/yYIdaZWN2tH5Km4IaMSgvU+rbZobOHPq5oX1KmAPHHVM41x0CGGxsg2xXEXk5g9nq7fy9kjgxN5taI3fWzlVU9nUsigJ3vMMNGj3jTgn6T7AmCmLiS+u9gBNe8C+iZD5uGAjul35k3tCfQnsUeojT2EzdCrQJBAONg3/VdIxdUxYMigUqPkDZY5yDgNrbbSWgBYAPNsAtdw1sLTBARcCquAthL1molFkehlTXM2HS88hPlP1k4nxsCQQDGjzkk+MgCgjFZcoHk4yT1QJq8z3rVXAxepopkSUohu2Ac/IJ7nZ1SItBpnLH0beXPIj0OW6pZFgXlCEfnsTPfAkEA4vD2twudkZTPieHvj36YnlW/YkXG9l0J+B+fOEycT8vmxsEQytzMyhxhxIZ02/5mLVpt9YOd624GY6skviVQTwJASqP0W02aAjdmzbUZ1yz766XJhUiJaEMd80avp1tVnrCNRNg3RLjPNt0Xc1wQGU5moSLByO/0bWC1/o7KWPJoCQJBAKFHxNSZV574BOj2A8+LPSoCvaFeio34MF+L0DCFe+MuX/kJxHWhfP/CzVAwuMha8PE+YfQnMdC47SIT7310Cws=";
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwXCMWSIzJwpDwTW6Ajmv0CflFuHVk2cgnK/oIiHorsSrZmPDPX1e9ct6MIKssP4d51LZ1Ee3N2B03XIWWHYiZwYPQa0u0wBLoSDiHlq7g4A8LWUh1k12ZtVLk1PeK3RObjWUy+p8L++VfKrCImj1pzLsYAnEWMd3eIxEpXq/5hQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(OrderActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
					int position = msg.arg1;//点击的listview的下标position
					int status = msg.arg2;
					page = 1;
					orderInfoBeans.clear();
					order_list();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OrderActivity.this, "支付结果确认中",Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(OrderActivity.this, "支付失败",Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(OrderActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		initView();
		order_Adapter = new Order_Adapter();
		myorder_listview_orderinfo.setAdapter(order_Adapter);

		button = new Button[] {myorder_button_1,myorder_button_2,myorder_button_3,myorder_button_4,myorder_button_5,myorder_button_6};
		for (int i = 0; i < button.length; i++) {
			button[i].setTextColor(this.getResources().getColor(R.color.textColor));
			button[i].setBackgroundResource(R.drawable.bkg_click);
		}
		button[0].setTextColor(this.getResources().getColor(R.color.base_green_color));
		button[0].setBackgroundResource(R.drawable.bkg_unclick);

	}
	@Override
	protected void onResume() {
		super.onResume();
		page = 1;
		orderInfoBeans.clear();
		order_list();
	}
	private void initView() {
		myorder_button_1 = (Button) findViewById(R.id.myorder_button_1);
		myorder_button_1.setOnClickListener(this);

		myorder_button_2 = (Button) findViewById(R.id.myorder_button_2);
		myorder_button_2.setOnClickListener(this);

		myorder_button_3 = (Button) findViewById(R.id.myorder_button_3);
		myorder_button_3.setOnClickListener(this);

		myorder_button_4 = (Button) findViewById(R.id.myorder_button_4);
		myorder_button_4.setOnClickListener(this);

		myorder_button_5 = (Button) findViewById(R.id.myorder_button_5);
		myorder_button_5.setOnClickListener(this);

		myorder_button_6 = (Button) findViewById(R.id.myorder_button_6);
		myorder_button_6.setOnClickListener(this);

		order_textview_back = (TextView) findViewById(R.id.order_textview_back);
		order_textview_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		myorder_listview_orderinfo = (PullToRefreshListView) findViewById(R.id.myorder_listview_orderinfo);
		myorder_listview_orderinfo.setMode(Mode.BOTH);
		myorder_listview_orderinfo.setOnRefreshListener(this);
	}
	@Override
	public void onClick(View v) {
		for (int i = 0; i < button.length; i++) {
			button[i].setTextColor(this.getResources().getColor(R.color.textColor));
			button[i].setBackgroundResource(R.drawable.bkg_click);
		}
		switch (v.getId()) {
		case R.id.myorder_button_1://全部
			tag = 1;
			button[0].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[0].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		case R.id.myorder_button_2:
			tag = 2;
			button[1].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[1].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		case R.id.myorder_button_3:
			tag = 3;
			button[2].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[2].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		case R.id.myorder_button_4:
			tag = 4;
			button[3].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[3].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		case R.id.myorder_button_5:
			tag = 5;
			button[4].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[4].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		case R.id.myorder_button_6:
			tag = 6;
			button[5].setTextColor(this.getResources().getColor(R.color.base_green_color));
			button[5].setBackgroundResource(R.drawable.bkg_unclick);
			page = 1;
			orderInfoBeans.clear();
			order_list();
			break;
		}
	}

	private void order_list() {
		pDialog = new MyProgressDialog(OrderActivity.this);
		pDialog.setMessage("正在加载订单数据");
		pDialog.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=orderlist";
		Map<String, String> params = new HashMap<String, String>();
		params.put("or_meid", getUserId());
		params.put("row", "10");
		params.put("page", page + "");
		params.put("or_status", tag + "");
		VolleyHttpClient.getInstance(OrderActivity.this).post(pDialog,url, params, null, null, new Listener<String>() {

			@Override
			public void onResponse(String res) {
				pDialog.dismiss();
				orderBeans = JSONObject.parseObject(res,OrderBean.class);
				if (orderBeans.getRecordset().size() != 0) {
					orderInfoBeans.addAll(orderBeans.getRecordset());
				}else{
					Toast.makeText(OrderActivity.this, "暂无订单", 3000).show();
					myorder_listview_orderinfo.onRefreshComplete();
				}
				order_Adapter.notifyDataSetChanged();
				myorder_listview_orderinfo.onRefreshComplete();
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				pDialog.dismiss();
				Toast.makeText(OrderActivity.this, "暂无订单", 3000).show();
				myorder_listview_orderinfo.onRefreshComplete();
			}
		});
	}
	public class Order_Adapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return orderInfoBeans.size();
		}

		@Override
		public Object getItem(int position) {
			return orderInfoBeans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.listview_order_list, null);
			final ListView order_listview_info= (ListView) convertView.findViewById(R.id.order_listview_info);
			TextView order_textview_addtime = (TextView) convertView.findViewById(R.id.order_textview_addtime);
			TextView order_textview_site = (TextView) convertView.findViewById(R.id.order_textview_site);
			TextView order_textview_ticket = (TextView) convertView.findViewById(R.id.order_textview_ticket);//礼券
			TextView order_textview_price = (TextView) convertView.findViewById(R.id.order_textview_price);
			TextView order_textview_status1 = (TextView) convertView.findViewById(R.id.order_textview_status1);
			TextView order_textview_status2 = (TextView) convertView.findViewById(R.id.order_textview_status2);
			TextView order_textview_status3 = (TextView) convertView.findViewById(R.id.order_textview_status3);
			TextView order_textview_status4 = (TextView) convertView.findViewById(R.id.order_textview_status4);


			order_textview_addtime.setText(orderInfoBeans.get(position).getOr_addtime());
			order_textview_site.setText(orderInfoBeans.get(position).getOr_site().getSp_name());
			//order_textview_ticket.setText();
			order_textview_price.setText("￥" + orderInfoBeans.get(position).getOr_pay());

			orderInfo_Adapter = new OrderInfo_Adapter(position);
			orderInfo_Adapter.notifyDataSetChanged();
			order_listview_info.setAdapter(orderInfo_Adapter);
			setListViewHeightBasedOnChildren(order_listview_info);

			final int i = Integer.valueOf(orderInfoBeans.get(position).getOr_status());
			switch (i) {
			case 1://全部
				order_textview_status1.setVisibility(View.GONE);
				order_textview_status2.setVisibility(View.GONE);
				order_textview_status3.setVisibility(View.GONE);
				order_textview_status4.setVisibility(View.GONE);
				break;
			case 2://未支付
				order_textview_status1.setVisibility(View.VISIBLE);//灰字
				order_textview_status1.setText("未付款");

				order_textview_status2.setVisibility(View.GONE);//黄字

				order_textview_status3.setVisibility(View.VISIBLE);//黄框
				order_textview_status3.setText("立即付款");

				order_textview_status4.setVisibility(View.VISIBLE);//灰框
				order_textview_status4.setText("删除订单");//you tui huo,mei tui kuan gong neng 
				break;
			case 3://未发货
				order_textview_status1.setVisibility(View.VISIBLE);
				order_textview_status1.setText("未发货");

				order_textview_status2.setVisibility(View.VISIBLE);
				order_textview_status2.setText("请稍等...");

				order_textview_status3.setVisibility(View.GONE);

				order_textview_status4.setVisibility(View.VISIBLE);
				order_textview_status4.setText("申请退/换货");
				break;
			case 4://未收货
				order_textview_status1.setVisibility(View.VISIBLE);
				order_textview_status1.setText("未收货");

				order_textview_status2.setVisibility(View.GONE);

				order_textview_status3.setVisibility(View.VISIBLE);
				order_textview_status3.setText("确认收货");

				order_textview_status4.setVisibility(View.VISIBLE);
				order_textview_status4.setText("申请退/换货");
				break;
			case 5://未评价
				order_textview_status1.setVisibility(View.VISIBLE);
				order_textview_status1.setText("未评价");

				order_textview_status2.setVisibility(View.GONE);

				order_textview_status3.setVisibility(View.GONE);

				order_textview_status4.setVisibility(View.GONE);
				break;
			case 7://交易结束
				order_textview_status1.setVisibility(View.VISIBLE);
				order_textview_status1.setText("交易结束");

				order_textview_status2.setVisibility(View.GONE);

				order_textview_status3.setVisibility(View.GONE);

				order_textview_status4.setVisibility(View.GONE);
				break;
			case 6://退换货
				order_textview_status1.setVisibility(View.VISIBLE);
				order_textview_status1.setText("退/换申请成功");

				order_textview_status2.setVisibility(View.VISIBLE);
				order_textview_status2.setText("请稍等...");

				order_textview_status3.setVisibility(View.GONE);

				order_textview_status4.setVisibility(View.GONE);
				break;
			}
			
			order_textview_status3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					order_id = orderInfoBeans.get(position).getOr_id();
					
					switch (i) {
					case 2://未支付
//						showToast(position + "");
//						showToast("支付方式："+orderInfoBeans.get(position).getOr_paytype() + "");
						if (orderInfoBeans.get(position).getOr_paytype().equals("1")) {//支付宝
							pay(orderInfoBeans.get(position).getOr_pay(),orderInfoBeans.get(position).getOr_danhao());
						}else if(orderInfoBeans.get(position).getOr_paytype().equals("2")){//微信
							setpay();
						}
						break;
					case 4://确认收货
						String url = "http://www.wssxls.com/shopn/app.php?act=shouhuo";
						Map<String, String> params = new HashMap<String, String>();
						params.put("or_id ", order_id);
						params.put("or_meid ", getUserId());
						VolleyHttpClient.getInstance(OrderActivity.this).post(url, params, null, new Listener<String>() {

							@Override
							public void onResponse(String res) {
								try {
									org.json.JSONObject jsonObject = new  org.json.JSONObject(res);
									String msg = jsonObject.getString("msg");
									if("success".equals(msg)){
										showToast("确认收货成功！");
										page = 1;
										orderInfoBeans.clear();
										order_list();
									}else{
										showToast(msg);
									}


								} catch (Exception e) {
									// TODO: handle exception
								}

							}
						}, new ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
								pDialog.dismiss();
								Toast.makeText(OrderActivity.this, "确认收货失败", 3000).show();
							}
						});
						break;
					}
				}
			});
			order_textview_status4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String order_id = orderInfoBeans.get(position).getOr_id();
					switch (i) {
					case 2://未收货
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setTitle("提示");
						builder.setMessage("确定要删除此订单么?");// 未支付可以退货功能已经实现
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								String order_id = orderInfoBeans.get(position).getOr_id();
								Map<String, String> params = new HashMap<String, String>();
								params.put("or_meid", getUserId());
								params.put("or_id", order_id);
								VolleyHttpClient.getInstance(OrderActivity.this).post("http://www.wssxls.com/shopn/app.php?act=orderdel", params, null, new Listener<String>() {

									@Override
									public void onResponse(String res) {
										try {
											org.json.JSONObject jsonObject = new  org.json.JSONObject(res);
											String msg = jsonObject.getString("msg");
											if("success".equals(msg)){
												showToast("删除成功！");
												page = 1;
												orderInfoBeans.clear();
												order_list();
											}else{
												showToast(msg);
											}
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
								}, new ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError arg0) {
										Toast.makeText(OrderActivity.this, "删除失败", 3000).show();
									}
								});
							}
						});
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
						builder.show();
						break;
					case 3://未收货
					case 4://确认收货   申请退换货接口
						final EditText editText = new EditText(mContext);
						AlertDialog.Builder builders = new AlertDialog.Builder(mContext);
						builders.setTitle("请输入申请理由");
						builders.setIcon(android.R.drawable.ic_dialog_info);
						builders.setView(editText);
						builders.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								String content = editText.getText().toString().trim();
								if (StringUtils.isEmpty(content)) {
									showToast("请输入申请理由");
								}else{
									String order_id = orderInfoBeans.get(position).getOr_id();
									Map<String, String> params = new HashMap<String, String>();
									params.put("th_meid", getUserId());
									params.put("th_orid", order_id);
									params.put("th_text", content);
									VolleyHttpClient.getInstance(OrderActivity.this).post("http://www.wssxls.com/shopn/app.php?act=tuihuo", params, null, new Listener<String>() {

										@Override
										public void onResponse(String res) {
											try {
												org.json.JSONObject jsonObject = new  org.json.JSONObject(res);
												String msg = jsonObject.getString("msg");
												if("success".equals(msg)){
													showToast("申请成功！");
													page = 1;
													orderInfoBeans.clear();
													order_list();
												}else{
													showToast(msg);
												}
											} catch (Exception e) {
												// TODO: handle exception
											}
										}
									}, new ErrorListener() {
										@Override
										public void onErrorResponse(VolleyError arg0) {
											Toast.makeText(OrderActivity.this, "申请退换货失败", 3000).show();
										}
									});
								}
							}
						});
						builders.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
						builders.show();

						break;
					}
				}
			});

			return convertView;
		}

	}
	private class OrderInfo_Adapter extends BaseAdapter{
		private ImageLoader loader;
		private int index;
		public OrderInfo_Adapter(int index) {
			this.index = index;
			loader = VolleyHepler.getInstance().getImageLoader();
		}
		@Override
		public int getCount() {
			return orderInfoBeans.get(index).getOr_detail().size();
		}

		@Override
		public Object getItem(int position) {
			return orderInfoBeans.get(index).getOr_detail().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(OrderActivity.this).inflate(R.layout.listview_orderinfo_list, null);
			NetworkImageView orderinfo_imageview_img = (NetworkImageView) convertView.findViewById(R.id.orderinfo_imageview_img);
			TextView orderinfo_textview_name = (TextView) convertView.findViewById(R.id.orderinfo_textview_name);
			TextView orderinfo_textview_number = (TextView) convertView.findViewById(R.id.orderinfo_textview_number);
			TextView orderinfo_textview_price = (TextView) convertView.findViewById(R.id.orderinfo_textview_price);
			TextView order_textview_delete = (TextView) convertView.findViewById(R.id.order_textview_delete);

			String pathimg = orderInfoBeans.get(index).getOr_detail().get(position).getOrd_pic();
			if(pathimg!=null&&!"".equals(pathimg)){
				orderinfo_imageview_img.setImageUrl(pathimg, loader);
			}

			orderinfo_textview_name.setText(orderInfoBeans.get(index).getOr_detail().get(position).getOrd_title());
			orderinfo_textview_number.setText("数量：×" + orderInfoBeans.get(index).getOr_detail().get(position).getOrd_num());
			double price = Double.valueOf(orderInfoBeans.get(index).getOr_detail().get(position).getOrd_zkj());
			
			DecimalFormat df = new DecimalFormat("######0.00");   
			orderinfo_textview_price.setText("￥" + df.format(price));
			
			if (orderInfoBeans.get(index).getOr_status().equals("5")) {
				order_textview_delete.setVisibility(View.VISIBLE);
			}
			order_textview_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (orderInfoBeans.get(index).getOr_status().equals("5")) {
						Intent intent = new Intent(OrderActivity.this, OrderCommentActivity.class);
						intent.putExtra("or_pic", orderInfoBeans.get(index).getOr_detail().get(position).getOrd_pic());
						intent.putExtra("or_name", orderInfoBeans.get(index).getOr_detail().get(position).getOrd_title());
						intent.putExtra("or_zkj", orderInfoBeans.get(index).getOr_detail().get(position).getOrd_zkj());
						intent.putExtra("goods_id", orderInfoBeans.get(index).getOr_detail().get(position).getOrd_dhid());
						intent.putExtra("goods_code", orderInfoBeans.get(index).getOr_detail().get(position).getOrd_ordanhao());
						startActivity(intent);
					}					
				}
			});


			return convertView;
		}
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
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		orderInfoBeans.clear();
		order_list();
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		page++;
		order_list();
	}
	/**
	 * 微信
	 */
	private void setpay() {
		pDialog = new MyProgressDialog(OrderActivity.this);
		pDialog.setMessage("正在获取微信支付信息");
		pDialog.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=wxpay";
		Map<String, String> params = new HashMap<String, String>();
		params.put("or_id", order_id);
		VolleyHttpClient.getInstance(OrderActivity.this).post(pDialog, url, params, null,null, new Listener<String>() {

			@Override
			public void onResponse(String res) {
				pDialog.dismiss();
				try {
					org.json.JSONObject jsonObject = new org.json.JSONObject(res);

					String appid = jsonObject.getString("appid");//"appid":"wxb641bcc3f9497097"
					String nonceStr = jsonObject.getString("noncestr");//": "a462b76e7436e98e0ed6e13c64b4fd1c",
					String packages = jsonObject.getString("package");//": "Sign=WXPay",
					String partnerId = jsonObject.getString("partnerid");//": "10000100",
					String prepayId = jsonObject.getString("prepayid");//": "1101000000140415649af9fc314aa427",
					String timeStamp = jsonObject.getString("timestamp");//": "1397527777",
					String sign = jsonObject.getString("sign");//": "582282D72DD2B03AD892830965F428CB16E7A256"
					

					PayReq payreq;
					final IWXAPI msgApi = WXAPIFactory.createWXAPI(OrderActivity.this, null);
					msgApi.registerApp(appid);
					payreq = new PayReq();
					payreq.appId = appid;
					payreq.nonceStr = nonceStr;
					payreq.packageValue = packages;
					payreq.partnerId = partnerId;
					payreq.prepayId = prepayId;
					payreq.timeStamp = timeStamp;
					payreq.sign = sign;
					payreq.extData = "app data"; //
					msgApi.sendReq(payreq);
				} catch (org.json.JSONException e) {
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "支付成功activity！";
		} else if (str.equalsIgnoreCase("faiel")) {
			finish();
			msg = "支付失败！";
		} else if (str.equalsIgnoreCase("cancel")) {
			finish();
			msg = "用户取消了支付";
		}
		showToast(msg);
		page = 1;
		orderInfoBeans.clear();
		order_list();
	}
	//---------++++++++++++-----------支         付++++++++++++++++++---------------+++++++++++++
	private void pay(String 应付金额,String 订单号) {
		String orderInfo = getOrderInfo("闻氏果蔬", "  ", 应付金额+"",订单号);
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
				PayTask alipay = new PayTask(OrderActivity.this);
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
	public String getOrderInfo(String subject, String body, String price,String order_num) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + order_num + "\"";

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
	//	public String getOutTradeNo() {
	//		//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
	//		//				Locale.getDefault());
	//		//		Date date = new Date();
	//		//		String key = format.format(date);
	//		//
	//		//		Random r = new Random();
	//		//		key = key + r.nextInt();
	//		//		key = key.substring(0, 15);
	//		return 订单号;
	//	}

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
}