package com.catdog.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.catdog.adapter.FragmentClassifyAdapter;
import com.catdog.bean.OneClassifyBean;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.util.GsonUtil;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Fragment2 extends Fragment implements OnClickListener,
		OnItemClickListener {

	private View tab2;
	private ListView mListView;
	private FragmentClassifyAdapter mAdapter;
	private ImageView search2;
	private List<Recordset> mList;

	private HttpUtils httpUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		httpUtils = new HttpUtils(URLConstant.CONNECT_TIME_OUT);
		tab2 = inflater.inflate(R.layout.main_fragment2, container, false);
		mListView = (ListView) tab2.findViewById(R.id.fragment2_lv);
		mListView.setOnItemClickListener(this);
		// search2 = (ImageView) tab2.findViewById(R.id.search2);
		// search2.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(getActivity(), SearchAtivity.class);
		// startActivity(intent);
		// }
		// });
		mList = new ArrayList<Recordset>();
		mAdapter = new FragmentClassifyAdapter(getActivity(), mList);
		mListView.setAdapter(mAdapter);
		// requestOneClassify();
		return tab2;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		requestOneClassify();
	}

	//
	// private void requestOneClassify() {
	// HttpUtils.getDataFromVolley(getActivity(), URLConstant.ONE_CLASSIFY,
	// null, null, oneclassifyListener);
	// }

	private void requestOneClassify() {
		httpUtils.send(HttpMethod.GET, URLConstant.ONE_CLASSIFY, callback);
	}

	// private ResponseListener oneclassifyListener = new ResponseListener() {
	//
	// @Override
	// public void getResponse(Object object) throws JSONException {
	// // TODO Auto-generated method stub
	// String str = (String) object;
	// Gson gson = new Gson();
	// OneClassifyBean bean = gson.fromJson(str, OneClassifyBean.class);
	// if (bean.getRecordset() != null) {
	// mAdapter = new FragmentClassifyAdapter(getActivity(),
	// bean.getRecordset());
	// mAdapter.notifyDataSetChanged();
	// mListView.setAdapter(mAdapter);
	// }
	//
	// }
	// };

	private RequestCallBack<String> callback = new RequestCallBack<String>() {

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			System.out.println(arg1);
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			OneClassifyBean bean = GsonUtil.jsonToBean(arg0.result,
					OneClassifyBean.class);
			if (null != bean && null != bean.getRecordset()) {
				if (0 < mList.size()) {
					mList.clear();
				}
				mList.addAll(bean.getRecordset());
				mAdapter.notifyDataSetChanged();
			} else {
				System.out.println("error");
			}
		}

	};

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
}
