package com.catdog.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catdog.activity.AddressListActivity;
import com.catdog.activity.Constant;
import com.catdog.activity.InfoActivity;
import com.catdog.activity.MyCollectActivity;
import com.catdog.helper.LoadImageHelper;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.StringUtils;
import com.catdog.util.URLConstant;
import com.catdog.view.RoundImageView;
import com.catdog.wsgs.R;
import com.catdog.wsgs.wxapi.OrderActivity;

public class Fragment5 extends Fragment implements OnClickListener{
	private View tab5;
	private LinearLayout ll_add,ll_info,fragment5_ll_myorder,fragment5_ll_mycollect;
	private LinearLayout ll_contact,ll_public;
	private RoundImageView rimg_head;
	private LoadImageHelper imageHelper;
	private TextView tv_name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tab5 = inflater.inflate(R.layout.main_fragment5, container,false);
		imageHelper = new LoadImageHelper(getActivity());
		preferencesManager = new SharePreferencesManager(getActivity(), Constant.REMEMBER_PREFERENCE);
		initView();
		initListener();
		requestInfo();
		return tab5;
	}
	@Override
	public void onResume() {
		super.onResume();
		tv_name.setText(getUserUid());
	}
	private void requestInfo() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_id", getUserId());
		HttpUtils.getDataFromVolley(getActivity(), URLConstant.USER_HEAD, null, map, listener);
	}
	String me_pic;
	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			me_pic = object2.getString("me_pic");
			imageHelper.loadImage(me_pic, rimg_head);
		}
	};

	private void initListener() {
		// TODO 自动生成的方法存根
		ll_add.setOnClickListener(this);
		ll_info.setOnClickListener(this);
		ll_public.setOnClickListener(this);
		ll_contact.setOnClickListener(this);
		fragment5_ll_myorder.setOnClickListener(this);
		fragment5_ll_mycollect.setOnClickListener(this);
	}

	private void initView() {
		// TODO 自动生成的方法存根
		tv_name = (TextView) tab5.findViewById(R.id.centerFt_tv_name);
		rimg_head = (RoundImageView) tab5.findViewById(R.id.centerFt_rimg_head);
		ll_public = (LinearLayout) tab5.findViewById(R.id.meFt_ll_public);
		ll_contact = (LinearLayout) tab5.findViewById(R.id.meFt_ll_contact);
		ll_add= (LinearLayout) tab5.findViewById(R.id.centerFt_ll_add);
		ll_info = (LinearLayout) tab5.findViewById(R.id.centerFt_ll_info);
		fragment5_ll_myorder = (LinearLayout) tab5.findViewById(R.id.fragment5_ll_myorder);
		fragment5_ll_mycollect = (LinearLayout) tab5.findViewById(R.id.fragment5_ll_mycollect);
		tv_name.setText(getUserUid());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.centerFt_ll_add:
			intent = new Intent(getActivity(),AddressListActivity.class);
			intent.putExtra("style", "list");
			startActivity(intent);
			break;
		case R.id.centerFt_ll_info:
			intent = new Intent(getActivity(),InfoActivity.class);
			intent.putExtra("photo", me_pic);
			startActivityForResult(intent, 10000);
			break;
		case R.id.fragment5_ll_myorder:
			intent = new Intent(getActivity(),OrderActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment5_ll_mycollect:
			intent = new Intent(getActivity(),MyCollectActivity.class);
			startActivity(intent);
			break;
		case R.id.meFt_ll_contact:
			Uri uri = Uri.parse("tel:4000-91-5433");   
			intent = new Intent(Intent.ACTION_DIAL, uri);     
			startActivity(intent); 
			break;
		case R.id.meFt_ll_public:
			Uri uri1 = Uri.parse("http://www.wssxls.com"); 
			Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1); 
			startActivity(intent1);
			break;
		default:
			break;
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 10000:
			String pic = data.getStringExtra("photo");
			if(!StringUtils.isEmpty(pic))
				imageHelper.loadImage(pic, rimg_head);
			break;

		default:
			break;
		}
		tv_name.setText(getUserUid());
	}
	/**
	 * 获取用户id
	 * @return
	 */

	private SharePreferencesManager preferencesManager;
	public String getUserId(){
		return preferencesManager.getString(Constant.USER_ID, null);
	}

	public String getUserUid(){
		return preferencesManager.getString(Constant.USER_UID, null);
	}

}
