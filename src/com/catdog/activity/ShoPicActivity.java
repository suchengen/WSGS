package com.catdog.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.catdog.base.BaseActivity;
import com.catdog.helper.LoadImageHelper;
import com.catdog.wsgs.R;

public class ShoPicActivity extends BaseActivity {
	private WebView webView;
	private TextView tv_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sho_pic);
		tv_back = (TextView) findViewById(R.id.shopPic_title_text);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		webView = (WebView) findViewById(R.id.webview);
		String str = getIntent().getStringExtra("pic");
		if(!isEmpty(str))
		{
			webView.getSettings().setUseWideViewPort(true);
			webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			webView.getSettings().setJavaScriptEnabled(true); 
			webView.getSettings().setSupportZoom(true); 
			webView.getSettings().setBuiltInZoomControls(true);
			webView.getSettings().setLoadWithOverviewMode(true);
			webView.loadUrl(str);
		}else
		{
			showToast("暂无数据");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sho_pic, menu);
		return true;
	}

}
