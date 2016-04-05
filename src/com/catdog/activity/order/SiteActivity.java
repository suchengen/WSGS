package com.catdog.activity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.base.BaseActivity;
import com.catdog.util.MyProgressDialog;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;

public class SiteActivity extends BaseActivity {
	private ListView site_listview_list;
	private TextView site_textview_back;
	private MyProgressDialog pDialog;
	private List<String> strings = new ArrayList<String>();
	private List<String> stringss = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site);

		String lng = getIntent().getStringExtra("lng");
		String lat = getIntent().getStringExtra("lat");
		
		
		initView();
		initData(lng,lat);
	}
	private void initView() {
		site_listview_list = (ListView) findViewById(R.id.site_listview_list);
		site_textview_back = (TextView) findViewById(R.id.site_textview_back);
		site_textview_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private void initData(String lng,String lat) {
		load(lng,lat);
		site_listview_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String site = strings.get(position);
				String site_id = stringss.get(position);
				Intent intent=new Intent();  
                intent.putExtra("text", site);
                intent.putExtra("text_id", site_id);
                setResult(2003, intent);  
                finish();  
			}
		});
	}
	private void load(String lng,String lat) {
		pDialog = new MyProgressDialog(SiteActivity.this);
		pDialog.setMessage("正在加载");
		pDialog.show();
		String url = "http://www.wssxls.com/shopn/app.php?act=shoplist";
		Map<String, String> params = new HashMap<String, String>();
		params.put("lat", lat);
		params.put("lng", lng);
		params.put("juli", "3000");
		params.put("jibie", "A");
		VolleyHttpClient.getInstance(SiteActivity.this).post(pDialog, url, params, null,null, new Listener<String>() {
			@Override
			public void onResponse(String res) {
				pDialog.dismiss();
				try {
					strings.clear();
					JSONObject obj = new JSONObject(res);
					JSONArray jsonArray = obj.getJSONArray("recordset");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = (JSONObject) jsonArray.get(i);
						strings.add(jsonObject.getString("sp_name"));
						stringss.add(jsonObject.getString("sp_id"));
					}
					site_Adapter time_Adapter = new site_Adapter();
					time_Adapter.notifyDataSetChanged();
					site_listview_list.setAdapter(time_Adapter);
				} catch (JSONException e) {
					showToast("当前订单地址周围暂无门店数据");
				}

			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				pDialog.dismiss();
			}
		});
	}

	private class site_Adapter extends BaseAdapter{

		@Override
		public int getCount() {
			return strings.size();
		}

		@Override
		public Object getItem(int position) {
			return strings.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(SiteActivity.this).inflate(R.layout.listview_site_list, null);
			TextView site_textview_ps = (TextView) convertView.findViewById(R.id.site_textview_ps);
			site_textview_ps.setText(strings.get(position));
			return convertView;
		}
	}
}
