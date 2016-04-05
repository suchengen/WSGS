package com.catdog.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
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
import com.catdog.wsgs.R.id;
import com.catdog.wsgs.R.layout;

public class ChangePhoneActivity extends BaseActivity {

	private EditText et_name;
	private Button btn_ok;
	private ImageView img_back;
	private MyProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone);
		initView();
	}
	String str_name;
	private void initView() {
		// TODO 自动生成的方法存根
		et_name = (EditText) findViewById(R.id.change_et_phone);
		btn_ok = (Button) findViewById(R.id.change_btn_ok);
		img_back = (ImageView) findViewById(R.id.name_img_left);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				str_name = et_name.getText().toString().trim();
				if(!isEmpty(str_name))
				{
					pDialog = new MyProgressDialog(mContext);
					pDialog.setMessage("正在修改...");
					pDialog.show();

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("me_id", getUserId());
					map.put("me_tel",str_name);
					HttpUtils.getDataFromVolley(mContext, URLConstant.CHAGE_PHONE, null, map, listener);
				}else
				{
					showToast("手机号不能为空！");
				}
			}
		});
	}
	
	private ResponseListener listener = new ResponseListener(){

		@Override
		public void getResponse(Object object) {
			// TODO 自动生成的方法存根
			String str = (String) object;
			try {
				if(null != pDialog && pDialog.isShowing())
					pDialog.dismiss();
				JSONObject jsonObject = new JSONObject(str);
				String msg = jsonObject.getString("msg");
				if("success".equals(msg))
				{
					
					showToast("修改成功！");
					SharePreferencesManager ma = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
					ma.setString(Constant.USER_TEL, str_name);
				}else
				{
					showToast(msg);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};


}
