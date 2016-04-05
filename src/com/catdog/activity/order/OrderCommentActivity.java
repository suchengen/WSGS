package com.catdog.activity.order;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.base.BaseActivity;
import com.catdog.bean.ShopDetailBean;
import com.catdog.helper.LoadImageHelper;
import com.catdog.util.HttpUtils;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.StringUtils;
import com.catdog.util.URLConstant;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.google.gson.Gson;

/**
 * 1.需要获取商品详情,没有商品id,没办法拿到
 * 2.评论提交的时候没办法传商品id
 */
public class OrderCommentActivity extends BaseActivity {
	private TextView comment_textview_back,estimate_textview_name,estimate_textview_price,comment_textview_submit;
	private ImageView estimate_networkimageview_img;
	private RatingBar estimate_ratingBar_xing;
	private EditText estimate_edittext_estimate;
	private LoadImageHelper imageHelper;
	private MyProgressDialog pDialog;
	private String goods_code;
	private String goods_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_activity_comment);
		initView();

		Intent intent = getIntent();
		goods_id = intent.getStringExtra("goods_id");
		goods_code = intent.getStringExtra("goods_code");
		String or_pic = intent.getStringExtra("or_pic");
		String or_name = intent.getStringExtra("or_name");
		String or_zkj = intent.getStringExtra("or_zkj");

		imageHelper = new LoadImageHelper(mContext);
		imageHelper.loadImage(or_pic, estimate_networkimageview_img);

		estimate_textview_name.setText(or_name);
		estimate_textview_price.setText("￥" + or_zkj);
		
	}
	private void initView() {
		comment_textview_back = (TextView) findViewById(R.id.comment_textview_back);
		comment_textview_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		estimate_networkimageview_img = (ImageView) findViewById(R.id.estimate_networkimageview_img);
		estimate_textview_name = (TextView) findViewById(R.id.estimate_textview_name);
		estimate_textview_price = (TextView) findViewById(R.id.estimate_textview_price);
		comment_textview_submit = (TextView) findViewById(R.id.comment_textview_submit);
		estimate_ratingBar_xing = (RatingBar) findViewById(R.id.estimate_ratingBar_xing);
		estimate_edittext_estimate = (EditText) findViewById(R.id.estimate_edittext_estimate);

		comment_textview_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				float f = estimate_ratingBar_xing.getRating();
				String text = estimate_edittext_estimate.getText().toString().trim();
				if (f != 0) {
					if (StringUtils.isEmpty(text)) {
						showToast("请输入评论内容");
					}else{
						load(f,text);
					}
				}else{
					showToast("请选择星级");
				}
			}
		});
	}
	
	private void load(float f,String text) {
		pDialog = new MyProgressDialog(OrderCommentActivity.this);
		pDialog.setMessage("正在提交");
		pDialog.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=newsdetailpinglun_save";
		Map<String, String> params = new HashMap<String, String>();
		params.put("pl_bianhao", goods_code);//订单号
		params.put("pl_meid", getUserId());//用户id
		params.put("pl_nid", goods_id);//商品id
		params.put("pl_xing", f + "");//星
		params.put("pl_content", text);//评论文字
		VolleyHttpClient.getInstance(OrderCommentActivity.this).post(pDialog, url, params, null,null, new Listener<String>() {
			@Override
			public void onResponse(String res) {
				pDialog.dismiss();
				try {
					org.json.JSONObject jsonObject = new  org.json.JSONObject(res);
					String msg = jsonObject.getString("msg");
					if("success".equals(msg)){
						showToast("评价成功！");
						OrderCommentActivity.this.finish();
					}else{
						showToast(msg);
					}
				} catch (Exception e) {
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				pDialog.dismiss();
			}
		});
	}
}
