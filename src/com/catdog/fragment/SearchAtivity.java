package com.catdog.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.activity.ShopDetailActivity;
import com.catdog.adapter.ClassifyListAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.ClassifyListBean;
import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.bean.OneClassifyBean.Recordset.Classlist;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferenceUtil;
import com.catdog.util.StringUtils;
import com.catdog.util.URLConstant;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;


public class SearchAtivity extends BaseActivity implements OnRefreshListener2<GridView>{
	private PullToRefreshGridView fragment3_class_gv;
	private ImageView imageView2;
	private EditText editText1;
	private GridView fragment3_gridview_history,fragment3_gridview_hotsearch;
	private LinearLayout fragment3_ll_search;
	private ClassifyListAdapter mAdapter;
	private Classlist bean;
	private int row = 10;
	private Button search_delete;
	private int currenpage;
	private List<Recordsets> list_data = new ArrayList<ClassifyListBean.Recordsets>();
	private SharePreferenceUtil sharePreferenceUtil;
	private String search;
	private String[] 历史记录;
	private List<String> 历史记录2 = new LinkedList<String>();
	private List<String> 热门搜索 = new LinkedList<String>();
	private String text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment3);
		initView();
		hotsearch();
		imageView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!StringUtils.isEmpty(editText1.getText().toString().trim())) {
					text = editText1.getText().toString().trim();
					fragment3_ll_search.setVisibility(View.GONE);
					fragment3_class_gv.setVisibility(View.VISIBLE);
					currenpage = 1;
					list_data.clear();
					requestClass(row,currenpage,text);
					imageView2.setClickable(false);
				}
			}
		});
		fragment3_gridview_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				text = 历史记录2.get(arg2);
				editText1.setText(text);
				fragment3_ll_search.setVisibility(View.GONE);
				fragment3_class_gv.setVisibility(View.VISIBLE);
				currenpage = 1;
				list_data.clear();
				requestClass(row,currenpage,text);
				imageView2.setClickable(false);
			}
		});
		fragment3_gridview_hotsearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				text = 热门搜索.get(arg2);
				editText1.setText(text);
				fragment3_ll_search.setVisibility(View.GONE);
				fragment3_class_gv.setVisibility(View.VISIBLE);
				currenpage = 1;
				list_data.clear();
				requestClass(row,currenpage,text);
				imageView2.setClickable(false);
			}
		});

	}
	private void hotsearch() {
		String url = "http://www.wssxls.com/shopn/app.php?act=newsclassad";
		Map<String, String> params = new HashMap<String, String>();
		params.put("ad_parentid", 70+"");//订单号
		VolleyHttpClient.getInstance(SearchAtivity.this).post(url, params, null, new Listener<String>() {
			@Override
			public void onResponse(String res) {
				try {
					org.json.JSONObject jsonObject = new  org.json.JSONObject(res);
					JSONArray array = jsonObject.getJSONArray("recordset");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = (JSONObject) array.get(i);
						热门搜索.add(object.getString("ad_title"));
					}
					HotSearchadapter adapter = new HotSearchadapter();
					adapter.notifyDataSetChanged();
					fragment3_gridview_hotsearch.setAdapter(adapter);
					setListViewHeightBasedOnChildren(fragment3_gridview_hotsearch);
				} catch (Exception e) {

				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		sharePreferenceUtil = new SharePreferenceUtil(mContext, "users");
		search = sharePreferenceUtil.getString("search");
		final Historyadapter historyadapter = new Historyadapter();
		if (search == null) {
			//			sharePreferenceUtil.getEditor().putString("search", "").commit();
		}else{
			历史记录 = search.trim().split(",");
			for (int i = 0; i < 历史记录.length; i++) {
				历史记录2.add(历史记录[i]);
			}
			for (int i = 0; i < 历史记录2.size(); i++) {
				for (int j = 历史记录2.size() - 1; j > i; j--) {
					if (历史记录2.get(i).equals(历史记录2.get(j))) {
						历史记录2.remove(j);
					}
				}
			}
			fragment3_gridview_history.setAdapter(historyadapter);
			setListViewHeightBasedOnChildren(fragment3_gridview_history);
			search_delete.setVisibility(View.VISIBLE);
		}
		search_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sharePreferenceUtil.remove(mContext, "search");
				fragment3_gridview_history.setVisibility(View.GONE);
				search_delete.setVisibility(View.GONE);
			}
		});
	}
	private void initView() {
		fragment3_ll_search = (LinearLayout) findViewById(R.id.fragment3_ll_search);
		fragment3_class_gv = (PullToRefreshGridView) findViewById(R.id.fragment3_class_gv);
		fragment3_class_gv.getRefreshableView().setNumColumns(2);
		fragment3_class_gv.setMode(Mode.BOTH);
		fragment3_class_gv.setOnRefreshListener(this);
		fragment3_class_gv.setVisibility(View.GONE);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		editText1 = (EditText) findViewById(R.id.editText1);
		fragment3_gridview_history = (GridView) findViewById(R.id.fragment3_gridview_history);
		fragment3_gridview_hotsearch = (GridView) findViewById(R.id.fragment3_gridview_hotsearch);
		search_delete = (Button) findViewById(R.id.search_delete);
	}
	private void requestClass(int row,int currentpage,String text) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("row", String.valueOf(row));
		map.put("title", text);
		map.put("currenpage", String.valueOf(currentpage));
		HttpUtils.getDataFromVolley(SearchAtivity.this, URLConstant.TWO_CLASSIFY, null, map, twoListener);
	}

	private ResponseListener twoListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			imageView2.setClickable(true);
			search = sharePreferenceUtil.getString("search");
			boolean 历史记录boolean = true;
			if (search != null) {
				for (int i = 0; i < 历史记录.length; i++) {
					if (历史记录[i].equals(editText1.getText().toString().trim())) {
						历史记录boolean = false;
					}
				}
				if (历史记录boolean) {
					sharePreferenceUtil.getEditor().putString("search", search + "," + editText1.getText().toString().trim()).commit();
				}
			}else{
				sharePreferenceUtil.getEditor().putString("search", editText1.getText().toString().trim()).commit();
			}
			String str = (String) object;
			if (StringUtils.isEmpty(str)) {
				showToast("暂无此商品");
			}
			Gson gson = new Gson();
			ClassifyListBean bean = gson.fromJson(str, ClassifyListBean.class);
			list_data.addAll(bean.getRecordset());
			mAdapter = new ClassifyListAdapter(SearchAtivity.this, list_data);
			fragment3_class_gv.setAdapter(mAdapter);
			fragment3_class_gv.setOnItemClickListener(gridviewClickListener);
			fragment3_class_gv.onRefreshComplete();
		}
	};
	private OnItemClickListener gridviewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = new Intent(SearchAtivity.this,ShopDetailActivity.class);
			Recordsets bean = (Recordsets) mAdapter.getItem(position);
			intent.putExtra("shopId", bean.getN_id());
			startActivity(intent);
		}
	};
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		currenpage = 1;
		list_data.clear();
		requestClass(row,currenpage,text);
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		currenpage++;
		requestClass(row,currenpage,text);
	}
	public class Historyadapter extends BaseAdapter{
		private int size;
		public Historyadapter() {

		}

		@Override
		public int getCount() {
			if (历史记录2.size() > 12) {
				size = 历史记录2.size() - 12;
				return 12;
			}else{
				return 历史记录2.size();
			}
		}

		@Override
		public Object getItem(int arg0) {
			return 历史记录2.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(历史记录2.get(position + size));
			return convertView;
		}

	}
	public class HotSearchadapter extends BaseAdapter{
		private int size;
		public HotSearchadapter() {

		}

		@Override
		public int getCount() {
			if (热门搜索.size() > 12) {
				size = 热门搜索.size() - 12;
				return 12;
			}else{
				return 热门搜索.size();
			}
		}

		@Override
		public Object getItem(int arg0) {
			return 热门搜索.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(热门搜索.get(position + size));
			return convertView;
		}
	}
	public static void setListViewHeightBasedOnChildren(GridView gridView) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		int itmeheight = listAdapter.getCount() / 4;
		if (listAdapter.getCount() % 4 != 0) {
			itmeheight = itmeheight + 1;
		}
		for (int i = 0; i < itmeheight; i++) {
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + 80;
		gridView.setLayoutParams(params);
	}
}
