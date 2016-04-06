package com.catdog.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.catdog.adapter.FragmentTabAdapter;
import com.catdog.adapter.FragmentTabAdapter.OnRgsExtraCheckedChangedListener;
import com.catdog.base.MyActivity;
import com.catdog.fragment.Fragment2;
import com.catdog.fragment.Fragment3;
import com.catdog.fragment.Fragment4;
import com.catdog.fragment.Fragment5;
import com.catdog.fragment.FragmentOne;
import com.catdog.util.SharedPreferencesUtil;
import com.catdog.util.URLConstant;
import com.catdog.wsgs.R;
import com.lidroid.xutils.HttpUtils;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends MyActivity implements
		OnRgsExtraCheckedChangedListener {

	private final static String LONGITUDE = "Longitude";
	private final static String LATITUDE = "Latitude";

	private RadioGroup main_include_radiogroup;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private Activity activity;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	private FragmentOne fragment1;

	private HttpUtils httpUtils;

	private double longitude;
	private double latitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UmengUpdateAgent.update(this);
		initView();
		initData();
		initHttpUtil();
		longitude = Double.valueOf(SharedPreferencesUtil.getString(
				getApplicationContext(), LONGITUDE, "0"));
		latitude = Double.valueOf(SharedPreferencesUtil.getString(
				getApplicationContext(), LATITUDE, "0"));
		if (0.0 == longitude || 0.0 == latitude) {
			mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
			mLocationClient.registerLocationListener(myListener); // 注册监听函数
			initLocation();
			mLocationClient.start();
		} else {
			fragment1.getClassList(longitude, latitude);
			System.out.println(longitude);
			System.out.println(latitude);
		}
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				System.out.println("GPS定位结果");
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				System.out.println("网络定位结果");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				System.out.println("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError) {
				System.out
						.println("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
				System.out.println("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
				System.out
						.println("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			SharedPreferencesUtil.save(getApplicationContext(), LONGITUDE,
					String.valueOf(location.getLongitude()));
			SharedPreferencesUtil.save(getApplicationContext(), LATITUDE,
					String.valueOf(location.getLatitude()));
			fragment1.getClassList(location.getLongitude(),
					location.getLatitude());
		}
	}

	private void initView() {
		main_include_radiogroup = (RadioGroup) findViewById(R.id.main_include_radiogroup);
	}

	private void initData() {
		fragment1 = new FragmentOne();
		Bundle bundle = new Bundle();
		fragment1.setArguments(bundle);

		Fragment fragment2 = new Fragment2();
		bundle = new Bundle();
		fragment2.setArguments(bundle);

		Fragment fragment3 = new Fragment3();
		bundle = new Bundle();
		fragment3.setArguments(bundle);

		Fragment fragment4 = new Fragment4();
		bundle = new Bundle();
		fragment3.setArguments(bundle);

		Fragment fragment5 = new Fragment5();
		bundle = new Bundle();
		fragment3.setArguments(bundle);

		fragments.add(fragment1);
		fragments.add(fragment2);
		fragments.add(fragment3);
		fragments.add(fragment4);
		fragments.add(fragment5);
		// 预加载
		FragmentTabAdapter fragmentAdapter = new FragmentTabAdapter(activity,
				getFragmentManager(), fragments, R.id.main_framelayout_show,
				main_include_radiogroup);
		fragmentAdapter.setOnRgsExtraCheckedChangedListener(this);
	}

	@Override
	public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId,
			int index) {

	}

	private void initHttpUtil() {
		httpUtils = new HttpUtils(URLConstant.CONNECT_TIME_OUT);
		fragment1.setHttpUtils(httpUtils);
	}

}
