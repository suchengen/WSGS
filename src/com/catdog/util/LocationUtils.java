package com.catdog.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 作者：魏立宇 时间：2014年12月18号 19：03 作用：定位的工具类
 */
public class LocationUtils {
	// private static final String TAG = "LocationUtils";
	private LocationClient mLocClient;
	public double mCurrentLatitude;
	public double mCurrentLongtitude;
	private String latitude;
	private String longitude;
	private String address;
	private String errorcode;
//	private BaiduMap mBaiduMap;

	// private ProgressDialog pDialog;

	/**
	 * 作用：获取定位信息
	 * 
	 * @param context
	 * @return
	 */
	public void getLocationResult(final Context context,
			final GetLocateResultListener locateResultListener) {
		// 提示正在定位
		// pDialog = new ProgressDialog(context);
		// pDialog.setMessage(context.getResources().getString(R.string.locating));

//		MapView mMapView = new MapView(context);
//		mBaiduMap = mMapView.getMap();
//		// 开启定位图层
//		mBaiduMap.setMyLocationEnabled(true);
//		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//				LocationMode.NORMAL, true, null));
		// 定位初始化
		mLocClient = new LocationClient(context);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式:
		option.setAddrType("detail");
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);
		option.setAddrType("all");
		option.setScanSpan(500);
		option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 包含机头方向
		mLocClient.setLocOption(option);
		mLocClient.start();
		// pDialog.show();

		// 注册位置监听器
		mLocClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				// pDialog.dismiss();
				if (location == null) {
					locateResultListener.getLocateResult(null);
					return;
				}
				StringBuffer sb1 = new StringBuffer(64);
				StringBuffer sb2 = new StringBuffer(64);
				StringBuffer sb3 = new StringBuffer(64);
				StringBuffer sb4 = new StringBuffer(64);

				sb1.append(location.getLatitude());// 纬度
				sb2.append(location.getLongitude());// 经度
				sb3.append(location.getAddrStr());// 地址
				sb4.append(location.getLocType());// 错误码

				latitude = sb1.toString().trim();
				longitude = sb2.toString().trim();
				address = sb3.toString().trim();
				errorcode = sb4.toString().trim();

				locateResultListener.getLocateResult(location);
				if ("161".equals(errorcode)) {
					// 网络定位结果
				} else if ("68".equals(errorcode)) {
					// 网络连接失败时，查找本地离线定位时对应的返回结果
				} else if ("65".equals(errorcode)) {
					// 定位缓存的结果。
				} else if ("162".equals(errorcode) || "163".equals(errorcode)
						|| "164".equals(errorcode) || "165".equals(errorcode)
						|| "166".equals(errorcode) || "167".equals(errorcode)) {
					// 服务端定位失败
				} else {
					//
				}
			}
		});
		if (mLocClient.isStarted()) {
			mLocClient.stop();
		} else {
			mLocClient.start();
		}
	}

	/**
	 * 作用：经纬度 转成 地址
	 * 
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 */
	public void getAddr(double latitude, double longitude,
			final LatLngToAddrListener latLngToAddrListener) {
//		GeoCoder mSearch = GeoCoder.newInstance();
//		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//			@Override
//			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//				latLngToAddrListener.latLngToAddr(result.getAddress());
//			}
//
//			@Override
//			public void onGetGeoCodeResult(GeoCodeResult result) {
//			}
//		});
//		LatLng ptCenter = new LatLng(latitude, longitude);
//		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
	}

	/**
	 * 作用：经纬度 转成 地址
	 * 
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 */
	public void getAddr(String latitude, String longitude,
			LatLngToAddrListener latLngToAddrListener) {
		getAddr(Float.valueOf(latitude), Float.valueOf(longitude),
				latLngToAddrListener);
	}

	/**
	 * 作用：地址 转成 经纬度
	 * 
	 * @param city
	 *            城市
	 * @param addr
	 *            地址
	 * @param addrToLatLngListener
	 */
	public void getLatLng(String city, String addr,
			final AddrToLatLngListener addrToLatLngListener) {
//		GeoCoder mSearch = GeoCoder.newInstance();
//		if (MyTextUtils.isEmpty(city)) {
//			return;
//		}
//		if (MyTextUtils.isEmpty(addr)) {
//			return;
//		}
//		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//			@Override
//			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
//			}
//
//			@Override
//			public void onGetGeoCodeResult(GeoCodeResult result) {
//				if (result != null) {
//					LatLng latLng = result.getLocation();
//					if (latLng != null) {
//						addrToLatLngListener.addrToLatLng(latLng.latitude,
//								latLng.longitude);
//						return;
//					}
//				}
//				addrToLatLngListener.addrToLatLng(0, 0);
//			}
//		});
//		mSearch.geocode(new GeoCodeOption().city(city).address(addr));
	}

	/**
	 * 作者：Lyman 时间：2014-12-27下午3:30:04 作用：获取定位结果的监听接口
	 */
	public interface GetLocateResultListener {
		void getLocateResult(BDLocation location);
	}

	/**
	 * 作者：Lyman 时间：2014-12-27下午4:46:15 作用：获取经纬度转换地址结果的监听
	 */
	public interface LatLngToAddrListener {
		void latLngToAddr(String addr);
	}

	/**
	 * 作者：Lyman 时间：2014-12-27下午4:46:46 作用：获取地址转换经纬度结果的监听
	 */
	public interface AddrToLatLngListener {
		void addrToLatLng(double latitude, double longitude);
	}

	/**
	 * 作用：获取两个坐标点间的距离 单位：米
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
//	public static double getDistance(LatLng p1, LatLng p2) {
//		return DistanceUtil.getDistance(p1, p2);
//	}

	/**
	 * 作用：获取两个坐标点间的距离 单位：米
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
//	public static double getDistance(double lat1, double lon1, double lat2,
//			double lon2) {
//		LatLng p1 = new LatLng(lat1, lon1);
//		LatLng p2 = new LatLng(lat2, lon2);
//		return getDistance(p1, p2);
//	}

	/**
	 * 作用：截取 市 县
	 */
	public static String getCityAndCounty(String address) {
		String location = "";
		String city = "市";
		String province = "省";
		if (!MyTextUtils.isEmpty(address)
				&& (address.contains(city) || address.contains(province))) {
			if (address.indexOf(city) == 2) {
				location = address.substring(0, address.indexOf("市") + 1)
						+ " "
						+ address.substring(address.indexOf("市") + 1,
								address.indexOf("区") + 1);
			} else {
				location = address.substring(0, address.indexOf("省") + 1)
						+ " "
						+ address.substring(address.indexOf("省") + 1,
								address.indexOf("市") + 1);
			}
		} else {
			location = "无定位结果";
		}
		return location;
	}
}
