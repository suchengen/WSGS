package com.catdog.activity.login;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.catdog.activity.Constant;
import com.catdog.activity.ForgetPwdActivity;
import com.catdog.activity.MainActivity;
import com.catdog.base.BaseActivity;
import com.catdog.base.MyActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.LogUtil;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;

/**
 * 登陆界面
 * 没有跳转到注册界面,登录貌似没有用
 */
public class LoginActivity extends MyActivity implements OnClickListener{
	private Button login_button_login;
	private EditText et_name,et_pwd;
	private LinearLayout ll_register,ll_forget;
	private CheckBox login_checkbox_login;
	private SharePreferencesManager manager;
	private boolean isAutoLogin;
	private boolean isCheck = true;
	private MyProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		manager = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
		initView();
		pDialog = new MyProgressDialog(mContext);
		isAutoLogin = manager.getBoolean(Constant.IS_AUTO_LOGIN, false);
		if(isAutoLogin)
		{
			login(manager.getString(Constant.USER_TEL, null),manager.getString(Constant.REMEMBER_PWD, null));
		}

	}


	@SuppressWarnings("unused")
	private void login(String str_name,String str_pwd) {
		// TODO Auto-generated method stub
		pDialog.setMessage("正在登录...");
		pDialog.show();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_uid", str_name);
		map.put("me_pwd", str_pwd);
		HttpUtils.getDataFromVolley(mContext, URLConstant.LOGIN, null, map, autologinListener);
	}

	private ResponseListener autologinListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			if(null != pDialog && pDialog.isShowing())
				pDialog.dismiss();
			String str = (String) object;
			LogUtil.i("Login", "00000"+str);
			JSONObject object2;
			try {
				object2 = new JSONObject(str);
				if(!isEmpty(object2.getString("msg")))
				{
					showToast(object2.getString("msg"));
				}
			} catch (Exception e) {
				
				object2 = new JSONObject(str);
				Intent intent = new Intent(mContext,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				showToast("登录成功！");
				finish();
			}
		}
	};

	private void initView() {
		login_button_login = (Button) findViewById(R.id.login_button_login);
		login_button_login.setOnClickListener(this);
		ll_forget = (LinearLayout) findViewById(R.id.login_ll_forget);
		ll_forget.setOnClickListener(this);
		et_name = (EditText) findViewById(R.id.login_et_name);
		et_pwd = (EditText) findViewById(R.id.login_et_pwd);
		ll_register = (LinearLayout) findViewById(R.id.login_ll_register);
		ll_register.setOnClickListener(this);
		login_checkbox_login = (CheckBox) findViewById(R.id.login_checkbox_login);
		login_checkbox_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					isCheck = true;
				}else{
					isCheck = false;
				}
			}
		});

	}
	private String str_pwd;
	private String str_name;
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.login_button_login:
			str_name = et_name.getText().toString().trim();
			str_pwd = et_pwd.getText().toString().trim();
			if(!isEmpty(str_name) && !isEmpty(str_pwd))
			{
				
				pDialog.setMessage("正在登陆...");
				pDialog.show();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("me_uid", str_name);
				map.put("me_pwd", str_pwd);
				HttpUtils.getDataFromVolley(mContext, URLConstant.LOGIN, null, map, listener);
			}else
			{
				showToast("用户名或密码不能为空！");
			}
			break;

		case R.id.login_ll_register:
			intent = new Intent(mContext,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_ll_forget:
			intent = new Intent(mContext,ForgetPwdActivity.class);
			startActivity(intent);
			break;
		}
	}

	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			if(null != pDialog && pDialog.isShowing())
				pDialog.dismiss();
			String str = (String) object;
			LogUtil.i("Login", "00000"+str);
			JSONObject object2;
			try {
				object2 = new JSONObject(str);
				if(!isEmpty(object2.getString("msg")))
				{
					showToast(object2.getString("msg"));
				}
			} catch (Exception e) {
				object2 = new JSONObject(str);
				if(isCheck)
				{
					writeToShared(object2);
				}else
				{
					manager.setBoolean(Constant.IS_AUTO_LOGIN, false);
				}
				Intent intent = new Intent(mContext,MainActivity.class);
				startActivity(intent);
				showToast("登录成功！");
				finish();
			}
		}
	};

	private void writeToShared(JSONObject object) throws JSONException {
		String me_id = object.getString("me_id");
		String me_uid = object.getString("me_uid");
		LogUtil.i("login", me_id);
		LogUtil.i("login", me_uid);
		manager.setString(Constant.REMEMBER_PWD, str_pwd);
		manager.setString(Constant.USER_ID, me_id);
		manager.setString(Constant.REMEMBER_ACCOUNT, str_name);
		manager.setString(Constant.USER_UID, me_uid);
		manager.setString(Constant.USER_TEL, me_uid);
		manager.setBoolean(Constant.IS_LOGIN, true);
		manager.setBoolean(Constant.IS_AUTO_LOGIN, true);
	}
	
	
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		if(pDialog!=null)
		{
			pDialog.dismiss();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if(pDialog != null && pDialog.isShowing())
		{
			pDialog.dismiss();
		}
	}
}
