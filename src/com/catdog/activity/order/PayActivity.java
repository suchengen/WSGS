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

public class PayActivity extends BaseActivity {
	private ListView pstime_listview_list;
	private MyProgressDialog pDialog;
	private List<String> strings = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);

		initView();
		initData();
	}
	private void initView() {
		pstime_listview_list = (ListView) findViewById(R.id.pstime_listview_list);
	}
	private void initData() {
//		strings.add("环讯支付");
		strings.add("支付宝");
		strings.add("微信支付");
		pstime_listview_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pstime = strings.get(position);
				Intent intent=new Intent();  
                intent.putExtra("text", pstime);
                intent.putExtra("text_postion", position + 1);
                setResult(2001, intent);  
                finish();  
			}
		});
		PSTime_Adapter time_Adapter = new PSTime_Adapter();
		time_Adapter.notifyDataSetChanged();
		pstime_listview_list.setAdapter(time_Adapter);
	}

	private class PSTime_Adapter extends BaseAdapter{

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
			convertView = LayoutInflater.from(PayActivity.this).inflate(R.layout.listview_pstime_list, null);
			TextView pstime_textview_ps = (TextView) convertView.findViewById(R.id.pstime_textview_ps);
			pstime_textview_ps.setText(strings.get(position));
			return convertView;
		}
	}
}	
