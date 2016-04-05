package com.catdog.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.catdog.base.BaseActivity;
import com.catdog.util.HttpUtils;
import com.catdog.util.MyProgressDialog;
import com.catdog.util.ResponseListener;
import com.catdog.util.SharePreferencesManager;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.catdog.wsgs.R.id;
import com.catdog.wsgs.R.layout;

public class ChangeEmailActivity extends BaseActivity {
	private EditText et_name;
	private Button btn_ok;
	private MyProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_email);
		initView();
	}
	String str_email;
	private void initView() {
		// TODO 自动生成的方法存根
		et_name = (EditText) findViewById(R.id.change_et_email);
		btn_ok = (Button) findViewById(R.id.change_btn_email);
		
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				str_email = et_name.getText().toString().trim();
				if(!isEmpty(str_email))
				{
					dialog = new MyProgressDialog(mContext);
					dialog.setMessage("正在修改邮箱");
					dialog.show();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("me_id", getUserId());
					map.put("me_email", getUserUid());
					HttpUtils.getDataFromVolley(mContext, URLConstant.CHANGE_EMAIL, null, map, listener);
			
				}else
				{
					showToast("邮箱不能为空！");
				}
			}
		});
	}
	
	private ResponseListener listener = new ResponseListener(){

		@Override
		public void getResponse(Object object) {
			dialog.dismiss();
			// TODO 自动生成的方法存根
			String str = (String) object;
			try {
				JSONObject jsonObject = new JSONObject(str);
				String msg = jsonObject.getString("msg");
				if("success".equals(msg))
				{
					showToast("修改成功！");
					SharePreferencesManager ma = new SharePreferencesManager(mContext, Constant.REMEMBER_PREFERENCE);
					ma.setString(Constant.USER_EMAIL, str_email);
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
