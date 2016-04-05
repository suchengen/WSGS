package com.catdog.activity;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.catdog.activity.login.RegisterActivity;
import com.catdog.base.BaseActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.MyCount;
import com.catdog.util.MyTextUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.StringUtils;
import com.catdog.util.ToastUtil;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.catdog.wsgs.R.id;
import com.catdog.wsgs.R.layout;

public class ForgetPwdActivity extends BaseActivity implements OnClickListener{
	private EditText et_phone,et_code,et_pwd1,et_pwd2;
	private String str_code2;
	private ImageView back;
	private Button btn_ok;
	private Button tv_code;
	//	private EventHandler eventHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		initView();
		initListener();
		//		initMobSDK();
	}
	private void initListener() {
		// TODO Auto-generated method stub
		tv_code.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.back);
		et_code = (EditText) findViewById(R.id.forget_et_code);
		et_phone = (EditText) findViewById(R.id.forget_et_phone);
		et_pwd1 = (EditText) findViewById(R.id.forget_et_pwd1);
		et_pwd2 = (EditText) findViewById(R.id.forget_et_pwd2);
		btn_ok = (Button) findViewById(R.id.forget_btn_ok);
		tv_code = (Button) findViewById(R.id.forget_tv_code);
	}
	private String str_phone,str_code,str_pwd1,str_pwd2;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.forget_tv_code:
			str_phone = et_phone.getText().toString().trim();
			if (StringUtils.isMobileNO(str_phone)) {
				new MyCount(60000, 1000,tv_code,ForgetPwdActivity.this).start();// 倒计时60秒
				tv_code.setClickable(false);
				//				SMSSDK.getVerificationCode("86", str_phone);
				Random random = new Random();
				str_code2 = "";
				for(int i=0;i<6;i++){
					str_code2+=random.nextInt(10);
				}
				requestPhoneCode();
			}else
			{
				showToast("输入的电话号码有误！");
			}

			break;

		case R.id.forget_btn_ok:
			str_code = et_code.getText().toString().trim();
			if(str_code.equals(str_code2))
			{
				if (ver()) {
					requestForgetPwd();
				}
			}else
			{
				showToast("验证码不正确！");
			}
			break;
		}
	}


	private void requestPhoneCode() {
		// TODO 自动生成的方法存根
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", str_code2);
		map.put("phone", str_phone);
		HttpUtils.getDataFromVolley(mContext, URLConstant.GET_PHONE_CODE, null, map, listener2);
	}

	private ResponseListener listener2 = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
			String str = (String) object;
			JSONObject object2 = new JSONObject(str);
			String msg = object2.getString("msg");
			if(!isEmpty(msg))
			{
				if(msg.equals("success"))
				{
					showToast("短信已发送，请注意查收！");
				}else
				{
					showToast(msg);
				}
			}
		}
	};
	protected void requestForgetPwd() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_tel", str_phone);
		map.put("me_pwd", str_pwd1);
		HttpUtils.getDataFromVolley(mContext, URLConstant.FORT_PWD, null, map, forgetPwd);
	}

	private ResponseListener forgetPwd = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO 自动生成的方法存根
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
				showToast(msg);
			}
		}
	};

	private boolean ver() {
		str_phone = et_phone.getText().toString().trim();
		str_pwd1 = et_pwd1.getText().toString().trim();
		str_pwd2 = et_pwd2.getText().toString().trim();
		if(isEmpty(str_phone)){
			showToast("请填写手机号码");
			return false;
		}

		if (str_phone.length()<11) {
			ToastUtil.showSortToast(getApplicationContext(), "手机号码小于11位");
			return false;
		}else{
			if (str_phone.length() > 11) {
				ToastUtil.showSortToast(getApplicationContext(), "手机号码大于11位");
				return false;
			}else {
				if (!str_phone.matches(MyTextUtils.REG_PHONE)) {
					ToastUtil.showSortToast(getApplicationContext(), "手机号码不符合规范");
					return false;
				}
			}
		}
		if (StringUtils.isEmpty(str_pwd1)) {
			ToastUtil.showSortToast(getApplicationContext(), "请输入密码");
			return false;
		}if(str_pwd1.length()<6 || str_pwd1.length()>16){
			ToastUtil.showSortToast(getApplicationContext(), "密码不能少于6位或者大于16位");
			return false;
		}if (StringUtils.isEmpty(str_pwd2)) {
			ToastUtil.showSortToast(getApplicationContext(), "请重复输入密码");
			return false;
		}if (!str_pwd1.equals(str_pwd2)) {
			ToastUtil.showSortToast(getApplicationContext(), "密码输入不一致");
			return false;
		}
		return true;
	}



}
