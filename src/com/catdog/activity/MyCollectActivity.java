package com.catdog.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.catdog.adapter.CollectAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.CollectBean;
import com.catdog.bean.CollectBean.Recordset;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.google.gson.Gson;

public class MyCollectActivity extends BaseActivity implements OnClickListener{
	private TextView img_left;
	private TextView tv_right;
	private GridView mGridView;
	private CollectAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collect);
		loadTitle();
		initView();
		requestCollect();
	}

	private void requestCollect() {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sc_meid", getUserId());
		HttpUtils.getDataFromVolley(mContext, URLConstant.MY_COLLECT, null, map, collectListener);
	}

	private ResponseListener collectListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			String str = (String) object;
			Gson gson = new Gson();
			CollectBean bean = gson.fromJson(str, CollectBean.class);
			mGridView.setNumColumns(2);
			if(null != bean.getRecordset() && bean.getRecordset().size() != 0)
			{
				mAdapter = new CollectAdapter(mContext, bean.getRecordset());
				mGridView.setAdapter(mAdapter);
				mGridView.setOnItemClickListener(itemClickListener);
			}else
			{
				showToast("暂无数据");
			}
		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent in = new Intent(mContext,ShopDetailActivity.class);
			Recordset bean = (Recordset) mAdapter.getItem(position);
			in.putExtra("shopId", bean.getSc_nid());
			startActivity(in);
		}

	};

	private void initView() {
		// TODO 自动生成的方法存根
		mGridView = (GridView) findViewById(R.id.collect_gv);
	}

	private void loadTitle() {
		// TODO 自动生成的方法存根
		img_left = (TextView) findViewById(R.id.collect_tv_left);
		tv_right = (TextView) findViewById(R.id.collect_tv_right);

		img_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});

		tv_right.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_collect, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.collect_tv_right:
			if(mAdapter != null)
				requestDeletAll();
			break;

		default:
			break;
		}
	}

	private void requestDeletAll() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sc_meid", getUserId());
		HttpUtils.getDataFromVolley(mContext, URLConstant.DELET_ALL_COLLET, null, map, deletListener);
	}

	private ResponseListener deletListener = new ResponseListener() {

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
					showToast("清空成功！");
				}
			}else
			{
				showToast("清空失败，请稍后再试！");
			}
		}
	};
}
