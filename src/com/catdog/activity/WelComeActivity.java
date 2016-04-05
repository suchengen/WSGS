package com.catdog.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.catdog.activity.login.LoginActivity;
import com.catdog.base.BaseActivity;
import com.catdog.wsgs.R;

public class WelComeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelComeActivity.this,LoginActivity.class);
				startActivity(intent);
				WelComeActivity.this.finish();
			}
		}, 2000);
	}
}
