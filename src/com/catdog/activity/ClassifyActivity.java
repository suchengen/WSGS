package com.catdog.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.adapter.ClassifyListAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.ClassifyListBean;
import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.bean.OneClassifyBean.Recordset.Classlist;
import com.catdog.util.HttpUtils;
import com.catdog.util.LogUtil;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ClassifyActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = ClassifyActivity.class.getSimpleName();
	private PullToRefreshListView mGridView;
	private ImageView img_left;
	private TextView tv_title;
	private Button classify_button_1, classify_button_2, classify_button_3;
	private Intent intent;
	private Classlist bean;
	private Recordset bean_Recordset;
	private int row = 10;
	private int currenpage = 1;
	private String id;
	private ClassifyListAdapter mAdapter;
	private List<Recordsets> list_data = new ArrayList<ClassifyListBean.Recordsets>();
	private MyProgressDialog dialog;
	private Button btnBack;

	private int currentPage = 0;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			switch (msg.what) {
			case Constant.LOADING_INIT:
				LogUtil.i(TAG, "------LOADING INIT----");
				mAdapter = new ClassifyListAdapter(mContext, list_data);
				mGridView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				mGridView.setOnItemClickListener(gridviewClickListener);
				mGridView.setMode(Mode.BOTH);
				mGridView.setOnRefreshListener(refreshListener);
				break;

			case Constant.LOADING_MORE:
				LogUtil.i(TAG, "------LOADING MORE----");
				mAdapter.notifyDataSetChanged();
				break;

			case Constant.LOADING_FINISH:
				LogUtil.i(TAG, "------LOADING FINISH----");
				showToast("没有更多了");
				break;
			default:
				break;
			}
			if (null != mGridView && mGridView.isRefreshing()) {
				mGridView.onRefreshComplete();
			}

		};

	};
	private int tag = 0;
	private int int_tag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);
		initView();
		intent = getIntent();
		tag = intent.getIntExtra("tag", 0);
		if (tag == 1000) {
			// 从分类跳转过来
			if (null != intent) {
				int_tag = 0;
				bean = (Classlist) intent.getSerializableExtra("classify");
				tv_title.setText(bean.getClassname());
				list_data.clear();
				id = bean.getClassid();
				requestClass(currenpage, int_tag, bean.getClassid());
			}
		} else if (tag == 2000) {
			// 从首页分类跳转过来
			if (null != intent) {
				int_tag = 0;
				bean_Recordset = (Recordset) intent
						.getSerializableExtra("classify");
				tv_title.setText(bean_Recordset.getClassname());
				list_data.clear();
				id = bean_Recordset.getClassid();
				LogUtil.i("ClassifyActivity", "首页分类传过来的id = " + id);
				requestClass(currenpage, int_tag, id);
			}
		} else {
			// 首页活动
			int_tag = tag;
			int classID = intent.getIntExtra(URLConstant.CLASS_ID, 0);
			id = String.valueOf(classID);
			requestClass(currenpage, int_tag, String.valueOf(id));
		}

		classify_button_1.setSelected(true);

	}

	private String orderby;

	private void requestClass(final int currenpage, int tags, String id) {
		// TODO Auto-generated method stub
		dialog = new MyProgressDialog(mContext);
		dialog.setMessage("正在加载");
		dialog.show();
		HttpUtils httpUtils = new HttpUtils();

		httpUtils.setDialog(dialog);
		Map<String, String> map = new HashMap<String, String>();
		if (!isEmpty(id)) {
			map.put("parentid", id);
		}
		if (!isEmpty(orderby))
			map.put("orderby", orderby);
		map.put("tuijian", "" + tags);
		map.put("row", "10");
		map.put("currenpage", "" + currenpage);
		VolleyHttpClient.getInstance(mContext).post(URLConstant.TWO_CLASSIFY,
				map, dialog, new Listener<String>() {

					@Override
					public void onResponse(String res) {
						dialog.dismiss();
						LogUtil.i("tag", "res = " + res);
						Gson gson = new Gson();
						ClassifyListBean bean = gson.fromJson(res,
								ClassifyListBean.class);
						List<Recordsets> list = bean.getRecordset();
						if (null != list)
							list_data.addAll(list);
						if (null != list && list.size() != 0 && currenpage == 1) {
							handler.sendEmptyMessage(Constant.LOADING_INIT);
						} else if (null != list_data && list.size() == 0) {
							handler.sendEmptyMessage(Constant.LOADING_FINISH);
						} else if (null != list_data && list.size() != 0
								&& currenpage != 1) {
							handler.sendEmptyMessage(Constant.LOADING_MORE);
						}

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						dialog.dismiss();
					}
				});

	}

	private ResponseListener twoListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			String str = (String) object;
			Gson gson = new Gson();
			ClassifyListBean bean = gson.fromJson(str, ClassifyListBean.class);
			List<Recordsets> list = bean.getRecordset();
			if (null != list)
				list_data.addAll(list);
			if (null != list && list.size() != 0 && currenpage == 1) {
				handler.sendEmptyMessage(Constant.LOADING_INIT);
			} else if (null != list_data && list.size() == 0) {
				handler.sendEmptyMessage(Constant.LOADING_FINISH);
			} else if (null != list_data && list.size() != 0 && currenpage != 1) {
				handler.sendEmptyMessage(Constant.LOADING_MORE);
			}
		}
	};

	private OnItemClickListener gridviewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, ShopDetailActivity.class);
			Recordsets bean = (Recordsets) mAdapter.getItem(position);
			intent.putExtra("shopId", bean.getN_id());
			startActivity(intent);
		}
	};

	private void initView() {
		img_left = (ImageView) findViewById(R.id.class_title_img_left);
		img_left.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.class_title_text);
		mGridView = (PullToRefreshListView) findViewById(R.id.class_gv);
		classify_button_1 = (Button) findViewById(R.id.classify_button_1);
		classify_button_1.setOnClickListener(this);
		classify_button_2 = (Button) findViewById(R.id.classify_button_2);
		classify_button_2.setOnClickListener(this);
		classify_button_3 = (Button) findViewById(R.id.classify_button_3);
		classify_button_3.setOnClickListener(this);
		btnBack = (Button) findViewById(R.id.btn_classify_back);
		btnBack.setOnClickListener(this);
	}

	OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO 自动生成的方法存根
			if (((PullToRefreshAdapterViewBase<ListView>) refreshView)
					.isFooterShown()) {

				currenpage += 1;
				requestClass(currenpage, int_tag, id);
			} else if (((PullToRefreshAdapterViewBase<ListView>) refreshView)
					.isHeaderShown()) {
				currenpage = 1;
				list_data.clear();
				if (null != mAdapter) {
					mAdapter.notifyDataSetChanged();
				}
				requestClass(currenpage, int_tag, id);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_classify_back:
			this.finish();
			break;
		case R.id.class_title_img_left:
			finish();
			break;
		case R.id.classify_button_1:
			if (0 == currentPage) {
				return;
			}
			currentPage = 0;
			classify_button_1.setSelected(true);
			classify_button_2.setSelected(false);
			classify_button_3.setSelected(false);
			list_data.clear();
			orderby = "";
			requestClass(currenpage, int_tag, id);
			break;
		case R.id.classify_button_2:
			if (1 == currentPage) {
				return;
			}
			currentPage = 1;
			classify_button_1.setSelected(false);
			classify_button_2.setSelected(true);
			classify_button_3.setSelected(false);
			list_data.clear();
			orderby = "n_zkj";
			requestClass(currenpage, int_tag, id);
			break;
		case R.id.classify_button_3:
			if (2 == currentPage) {
				return;
			}
			currentPage = 2;
			classify_button_1.setSelected(false);
			classify_button_2.setSelected(false);
			classify_button_3.setSelected(true);
			list_data.clear();
			orderby = "n_xiaoliang";
			requestClass(currenpage, int_tag, id);
			break;
		}
	}

}
