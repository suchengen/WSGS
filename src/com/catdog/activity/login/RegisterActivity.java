package com.catdog.activity.login;



import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.catdog.activity.Constant;
import com.catdog.activity.MainActivity;
import com.catdog.base.BaseActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.LogUtil;
import com.catdog.util.MyCount;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.MyTextUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.StringUtils;
import com.catdog.util.ToastUtil;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private static final RequestQueue requestQueue = null;
	private EditText et_name,et_phone,et_mail,et_code,et_pwd1,et_pwd2;
	private MyProgressDialog pDialog;
	private Button btn_ok;
	private Button btn_code;
	private String str_name,str_phone,str_email,str_pwd1,str_pwd2,str_code,str_code2;
	private SharePreferencesManager manager;
	//	private EventHandler eventHandler;
	private TextView tv_login;
	private ImageView register_img_back;
	private MyCount myCount;
	private String 发送验证码的手机号,现在的手机号;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registe);
		manager = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
		//		initMobSDK();
		initView();
		initListner();
		initData();
	}


	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		register_img_back = (ImageView) findViewById(R.id.register_img_back);
		tv_login = (TextView) findViewById(R.id.register_tv_login);
		tv_login.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		btn_code = (Button) findViewById(R.id.register_btn_code);
		et_name = (EditText) findViewById(R.id.register_et_name);
		et_phone = (EditText) findViewById(R.id.register_et_phone);
		et_mail = (EditText) findViewById(R.id.register_et_email);
		et_code = (EditText) findViewById(R.id.register_et_code);
		et_pwd1 = (EditText) findViewById(R.id.register_et_pwd1);
		et_pwd2 = (EditText) findViewById(R.id.register_et_pwd2);
		btn_ok = (Button) findViewById(R.id.register_btn_ok);
	}

	private void initListner() {
		// TODO Auto-generated method stub
		btn_code.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		register_img_back.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_btn_ok:
			if (ver()) {
				getLoginJsonMessage();
			}
			break;
		case R.id.register_img_back:
			finish();
			break;

		case R.id.register_tv_login:
			Intent intent = new Intent(mContext,LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.register_btn_code:
			str_phone = et_phone.getText().toString().trim();
			if (StringUtils.isMobileNO(str_phone)) {
				myCount = new MyCount(60000, 1000,btn_code,RegisterActivity.this);// 倒计时60秒
				myCount.start();
				btn_code.setClickable(false);
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
		default:
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
					发送验证码的手机号 = str_phone;
					showToast("短信已发送，请注意查收！");
				}else
				{
					showToast(msg);

				}
			}
		}
	};

	private boolean ver() {
		str_name = et_name.getText().toString().trim();
		str_email = et_mail.getText().toString().trim();
		str_phone = et_phone.getText().toString().trim();
		str_pwd1 = et_pwd1.getText().toString().trim();
		str_pwd2 = et_pwd2.getText().toString().trim();
		str_code = et_code.getText().toString().trim();
		if(isEmpty(str_name)){
			showToast("请填写用户名");
			return false;
		}
		if(isEmpty(str_phone)){
			showToast("请填写手机号码");
			return false;
		}
		if (str_phone.equals(发送验证码的手机号)) {
			showToast("请重新发送验证码");
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
		if(!isEmpty(str_code2))
		{
			if(!str_code2.equals(str_code))
			{
				showToast("验证码不正确！");
				return false;
			}
		}else
		{
			showToast("请重新获取验证码");
			return false;
		}
		if(isEmpty(str_email)){
			showToast("请填写邮箱");
			return false;
		}
		if (!str_email.matches(MyTextUtils.REG_EMAIL)) {
			ToastUtil.showSortToast(getApplicationContext(), "邮箱不符合邮箱规范");
			return false;
		}if (StringUtils.isEmpty(str_pwd1)) {
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


	private void getLoginJsonMessage() {
		// TODO Auto-generated method stub
		pDialog = new MyProgressDialog(mContext);
		pDialog.setMessage("正在注册...");
		pDialog.show();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("me_uid", str_name);
		map.put("me_pwd", str_pwd1);
		map.put("me_tel", str_phone);
		map.put("me_email", str_email);
		HttpUtils.getDataFromVolley(mContext, URLConstant.REGISTER, null, map, listener);
	}

	private ResponseListener listener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			if(null != pDialog && pDialog.isShowing())
				pDialog.dismiss();
			String str = (String) object;
			JSONObject object2;
			LogUtil.i(RegisterActivity.class.getSimpleName(), str);
			try {
				object2 = new JSONObject(str);
				if(!isEmpty(object2.getString("msg")))
				{
					showToast(object2.getString("msg"));
					if(object2.getString("msg").equals("用户已存在") || object2.getString("msg").equals("手机号已存在!"))
					{
						str_code2 = "";
						et_code.setText("");
						et_phone.setText("");
						et_name.setText("");
						myCount.cancel();
						btn_code.setClickable(true);
						btn_code.setText("获取验证码");
					}
				}
			} catch (Exception e) {
				object2 = new JSONObject(str);
				writeToShared(object2);
				Intent intent = new Intent(mContext,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(pDialog!=null)
			pDialog.dismiss();
	}

	private void writeToShared(JSONObject object) throws JSONException {
		String me_id = object.getString("me_id");
		String me_uid = object.getString("me_uid");
		String me_email = object.getString("me_email");
		String me_card = object.getString("me_card");
		String me_tel = object.getString("me_tel");
		manager.setString(Constant.REMEMBER_PWD, str_pwd1);
		manager.setString(Constant.USER_ID, me_id);
		manager.setString(Constant.USER_UID, me_uid);
		manager.setString(Constant.USER_EMAIL, me_email);
		manager.setString(Constant.USER_CARD, me_card);
		manager.setString(Constant.USER_TEL, me_tel);
	}
}
