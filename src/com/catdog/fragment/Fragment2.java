package com.catdog.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.catdog.adapter.ClassifyAdapter;
import com.catdog.bean.OneClassifyBean;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class Fragment2 extends Fragment implements OnClickListener, OnItemClickListener {

	private View tab2;
	private ListView mListView;
	private ClassifyAdapter mAdapter;
	private ImageView search2;
	private List<Recordset> mList;

	private HttpUtils httpUtils;
	private BitmapUtils bitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		httpUtils = new HttpUtils(URLConstant.CONNECT_TIME_OUT);
		bitmapUtils = new BitmapUtils(getActivity());
		tab2 = inflater.inflate(R.layout.main_fragment2, container, false);
		mListView = (ListView) tab2.findViewById(R.id.fragment2_lv);
		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, true, false));
		search2 = (ImageView) tab2.findViewById(R.id.search2);
		search2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchAtivity.class);
				startActivity(intent);
			}
		});
		mList = new ArrayList<Recordset>();
		mAdapter = new ClassifyAdapter(getActivity(), mList);
		mListView.setAdapter(mAdapter);
		// requestOneClassify();
		return tab2;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	// private void requestOneClassify() {
	// HttpUtils.getDataFromVolley(getActivity(), URLConstant.ONE_CLASSIFY,
	// null, null, oneclassifyListener);
	// }

	private void requestOneClassify() {

	}

	private ResponseListener oneclassifyListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			OneClassifyBean bean = gson.fromJson(str, OneClassifyBean.class);
			if (bean.getRecordset() != null) {
				mAdapter = new ClassifyAdapter(getActivity(), bean.getRecordset());
				mAdapter.notifyDataSetChanged();
				mListView.setAdapter(mAdapter);
			}

		}
	};

	private RequestCallBack<String> callback = new RequestCallBack<String>() {

		@Override
		public void onFailure(HttpException arg0, String arg1) {

		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {

		}

	};

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
}
