package com.catdog.wsgs.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.catdog.base.BaseActivity;
import com.catdog.util.LogUtil;
import com.catdog.util.SharePreferenceUtil;
import com.catdog.wsgs.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	private IWXAPI api;
	private SharePreferenceUtil sharePreferenceUtil;
	private int pay_id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pay_result);
		
		api = WXAPIFactory.createWXAPI(this, "wx4d1b505d6020d737");
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp rep) {
		sharePreferenceUtil = new SharePreferenceUtil(WXPayEntryActivity.this, "users");
		pay_id = sharePreferenceUtil.getInt("pay_id");
		if(rep.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
			int str = rep.errCode;
			LogUtil.i("wx", "errcode = "+str + rep.errStr );
			
			String msg = null;
			if (str == 0) {
				msg = "支付成功！";
				WXPayEntryActivity.this.finish();
			} else if (str == -1) {
				finish();
				msg = "支付失败！";
			} else if (str == -2) {
				finish();
				msg = "用户取消了支付";
			}else
			{
				msg = "位置错误";
			}
			
		}		
	}
	
	
	
	
	

}
