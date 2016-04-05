package com.catdog.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.catdog.base.BaseActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;

public class ChangePwdActivity extends BaseActivity {
	private EditText et_pwd1,et_pwd2,et_pwd3;
	private ImageView img_back;
	private Button btn_ok;
	private MyProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initView();
	}
	String str_pwd1,str_pwd2,str_pwd3;
	private void initView() {
		// TODO 自动生成的方法存根
		img_back = (ImageView) findViewById(R.id.changePwd_img_left);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		et_pwd1 = (EditText) findViewById(R.id.changePwd_et_pwd1);
		et_pwd2 = (EditText) findViewById(R.id.changePwd_et_pwd2);
		et_pwd3 = (EditText) findViewById(R.id.changePwd_et_pwd3);
		btn_ok = (Button) findViewById(R.id.changePwd_btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String str_pwd1,str_pwd2,str_pwd3;
				str_pwd1 = et_pwd1.getText().toString().trim();
				str_pwd2 = et_pwd2.getText().toString().trim();
				str_pwd3 = et_pwd3.getText().toString().trim();
				
				if(!isEmpty(str_pwd1) && !isEmpty(str_pwd2) && !isEmpty(str_pwd3))
				{
					if(str_pwd2.equals(str_pwd3))
					{
						requestChangePwd(str_pwd1,str_pwd2);
					}
				}else
				{
					showToast("不能为空！");
				}
			}
			
		});
	}

	private void requestChangePwd(String str_pwd1, String str_pwd2) {
		// TODO 自动生成的方法存根
		pDialog = new MyProgressDialog(mContext);
		pDialog.setMessage("正在修改...");
		pDialog.show();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_id", getUserId());
		map.put("me_oldpwd", str_pwd1);
		map.put("me_pwd", str_pwd2);
		HttpUtils.getDataFromVolley(mContext, URLConstant.CHANGE_PWD, null, map, changepwdListener);
	}
	
	private ResponseListener changepwdListener = new ResponseListener() {
		
		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			if(null != pDialog && pDialog.isShowing())
				pDialog.dismiss();
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String msg = object2.getString("msg");
			if(msg.equals("success"))
			{
				showToast("修改成功！");
				SharePreferencesManager ma = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
				ma.setString(Constant.REMEMBER_PWD, str_pwd2);
				finish();
			}else
			{
				showToast("修改失败");
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_pwd, menu);
		return true;
	}

}
