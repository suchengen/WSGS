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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.catdog.adapter.AddressAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.AddressBean;
import com.catdog.bean.AddressBean.Address;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.google.gson.Gson;

public class AddressListActivity extends BaseActivity implements OnClickListener{
	private ListView mListView;
	private AddressAdapter mAdapter;
	private TextView tv_title,tv_right;
	private ImageView img_left;
	private TextView tv_add;
	private String style;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_list);
		initView();
		initListener();
		style = getIntent().getStringExtra("style");

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Address bean = (Address) mAdapter.getItem(position);

				if(!isEmpty(style) && style.equals("list")){
					Intent intent = new Intent(mContext, AddAddressActivity.class);
					intent.putExtra("tag", 1);
					intent.putExtra("add_id", bean.getDz_id());
					intent.putExtra("add_name", bean.getDz_uname());
					intent.putExtra("add_address", bean.getDz_address());
					intent.putExtra("add_tel", bean.getDz_tel());
					intent.putExtra("add_sheng", bean.getDz_sheng());
					intent.putExtra("add_shi", bean.getDz_shi());
					intent.putExtra("add_qu", bean.getDz_qu());
					startActivity(intent);
				}else{
					Intent intent = new Intent();
					intent.putExtra("add_id", bean.getDz_id());
					intent.putExtra("add_name", bean.getDz_uname());
					intent.putExtra("add_sheng", bean.getDz_sheng());
					intent.putExtra("add_shi", bean.getDz_shi());
					intent.putExtra("add_qu", bean.getDz_qu());
					intent.putExtra("add_address", bean.getDz_address());
					intent.putExtra("add_tel", bean.getDz_tel());
					intent.putExtra("add_lng", bean.getDz_lng());
					intent.putExtra("add_lat", bean.getDz_lat());
					setResult(2002,intent);
					finish();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestAddress();
	}
	private void initListener() {
		// TODO 自动生成的方法存根
		tv_right.setOnClickListener(this);
		tv_add.setOnClickListener(this);
		img_left.setOnClickListener(this);

	}

	private void initView() {
		// TODO 自动生成的方法存根
		tv_title = (TextView) findViewById(R.id.add_title_text);
		tv_title.setText("地址列表");
		img_left = (ImageView) findViewById(R.id.add_title_img_left);
		tv_right = (TextView) findViewById(R.id.add_title_tv_right);
		mListView = (ListView) findViewById(R.id.add_lv);
		tv_add = (TextView) findViewById(R.id.tv_add);
	}

	private void requestAddress() {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dz_meid", getUserId());
		HttpUtils.getDataFromVolley(mContext, URLConstant.ADDRESS_LIST, null, map, listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.address_list, menu);
		return true;
	}

	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) {
			// TODO 自动生成的方法存根
			String str = (String) object;
			try {
				JSONObject object2 = new JSONObject(str);
				String msg = object2.getString("msg");
				if(!isEmpty(msg))
				{
					showToast(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Gson gson = new Gson();
				AddressBean bean = gson.fromJson(str, AddressBean.class);
				if(bean.getRecordset()!=null)
				{
					mAdapter = new AddressAdapter(mContext,bean.getRecordset());
					mAdapter.notifyDataSetChanged();
					mListView.setAdapter(mAdapter);
				}else{
					showToast("暂无数据！");
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.add_title_img_left:
			finish();
			break;
		case R.id.add_title_tv_right:
			requestDeleteAllAdress();
			break;
		case R.id.tv_add:
			Intent intent  = new Intent(mContext,AddAddressActivity.class);
			startActivityForResult(intent, 10000);
			break;
		default:
			break;
		}
	}

	private void requestDeleteAllAdress() {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dz_meid", getUserId());
		HttpUtils.getDataFromVolley(mContext, URLConstant.DELETE_ALL_ADDRESS, null, map, deletAllResponseListener);
	}

	private ResponseListener deletAllResponseListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String msg = object2.getString("msg");
			if(!isEmpty(msg))
			{
				if(msg.equals("success")){
					showToast("清空成功！");
					requestAddress();

				}else{
					showToast(msg);
				}
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 100000:
			requestAddress();
			break;
		}
	}

}
