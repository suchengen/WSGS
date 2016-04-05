package com.catdog.fragment;

import java.util.List;

import org.json.JSONException;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.catdog.adapter.AddressAdapter;
import com.catdog.adapter.ClassifyAdapter;
import com.catdog.bean.AddressBean;
import com.catdog.bean.OneClassifyBean;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Fragment2 extends Fragment implements OnClickListener{
	private View tab2;
	private ListView mListView;
	private ClassifyAdapter mAdapter;
	private ImageView search2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tab2 = inflater.inflate(R.layout.main_fragment2, container,false);
		mListView = (ListView) tab2.findViewById(R.id.fragment2_lv);
		search2 = (ImageView) tab2.findViewById(R.id.search2);
		search2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SearchAtivity.class);
				startActivity(intent);
			}
		});
		requestOneClassify();
		return tab2;
	}
	private void requestOneClassify() {
		HttpUtils.getDataFromVolley(getActivity(), URLConstant.ONE_CLASSIFY, null, null, oneclassifyListener);
	}

	private ResponseListener oneclassifyListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			OneClassifyBean bean = gson.fromJson(str, OneClassifyBean.class);
			if(bean.getRecordset()!=null)
			{
				mAdapter = new ClassifyAdapter(getActivity(),bean.getRecordset());
				mAdapter.notifyDataSetChanged();
				mListView.setAdapter(mAdapter);
			}

		}
	};
	@Override
	public void onClick(View v) {

	}
}
