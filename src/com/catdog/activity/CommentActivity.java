package com.catdog.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.catdog.adapter.CommentAdapter;
import com.catdog.base.BaseActivity;
import com.catdog.bean.CommentBean;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CommentActivity extends BaseActivity {
	private PullToRefreshListView mListView;
	private CommentAdapter mAdapter;
	private String shop_id;
	private ImageView img_back;
	private String str_score;
	private TextView tv_score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initView();
		shop_id = getIntent().getStringExtra("shop_id");
		str_score = getIntent().getStringExtra("score");
		if(!isEmpty(str_score))
			tv_score.setText(str_score);
		else
			tv_score.setText("0");
		if(!isEmpty(shop_id))
			requestComment();
		else
			showToast("id值为空");
	}


	private void initView() {
		img_back = (ImageView) findViewById(R.id.comment_title_img_left);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mListView = (PullToRefreshListView)findViewById(R.id.comment_lv);
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_head_comment, null);
		ListView lv = mListView.getRefreshableView();
		lv.addHeaderView(view);
		tv_score = (TextView) view.findViewById(R.id.comment_tv_score);
	}
	
	private void requestComment() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pl_nid", shop_id);
		HttpUtils.getDataFromVolley(mContext, URLConstant.SHOP_COMMENT, null, map, listener);
	}

	
	private ResponseListener listener = new ResponseListener() {
		
		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			CommentBean bean = gson.fromJson(str, CommentBean.class);
			if(bean.getRecordset().size() !=0){
				mAdapter = new CommentAdapter(mContext, bean.getRecordset());
				mListView.setAdapter(mAdapter);
			}else{
				showToast("暂无数据！");
			}
		}
	};
}
