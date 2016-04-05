package com.catdog.base;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 日期：2015-6-23
 * 作者：Paul
 * 功能：点击两次返回键返回
 */
public class MyActivity extends BaseActivity {
	private long exitTime = 0;
	// 点击两下返回界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				List<Activity> list = ((MyApplication) getApplication()).getActivities();
				for (int i = 0; i < list.size(); i++) {
					Activity activity = (Activity) list.get(i);
					activity.finish();
				}
				this.finish();
				ActivityManager activityMgr= (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
				//关闭应用
				//与当前应用相关的应用、进程、服务等也会被关闭。
				//会发送 ACTION_PACKAGE_RESTARTED广播。
				activityMgr.restartPackage(this.getPackageName());
				System.exit(0);//退出应用
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
