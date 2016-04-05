package com.catdog.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.activity.ClassifyActivity;
import com.catdog.adapter.ImageBannerAdapter;
import com.catdog.adapter.MainFragmentClassListAdapter;
import com.catdog.bean.ClassListBean;
import com.catdog.bean.Flash_info;
import com.catdog.bean.MainFragmentClassBean;
import com.catdog.util.GsonUtil;
import com.catdog.util.ToastUtil;
import com.catdog.util.URLConstant;
import com.catdog.view.MyListView;
import com.catdog.view.MyViewPager;
import com.catdog.view.Options;
import com.catdog.volly.NetWorkUtil;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FragmentOne extends Fragment implements OnClickListener {

	private HttpUtils httpUtils;
	private BitmapUtils bitmapUtils;
	private MyListView mListView;
	private MainFragmentClassListAdapter mAdapter;
	private ArrayList<ClassListBean> mList;

	private View tab1;
	private ArrayList<Flash_info> flash_infos;
	private TextView search1;
	private MyViewPager main_myviewpager;
	private List<View> imageList = new ArrayList<View>();
	private List<ImageView> icons = new ArrayList<ImageView>();
	private ImageLoader imageLoader;
	private Handler handler = new Handler();
	private int currentItem;
	private ImageBannerAdapter imageComicAdapter;// 首页banner的滑动
	private TextView main_fragment_shuiguo, main_fragment_shucai,
			main_fragment_roudanqin, main_fragment_dongpin,
			main_fragment_shuichan, main_fragment_ganguo,
			main_fragment_gandiao, main_fragment_shangchao,
			main_fragment_jiagong, main_fragment_order;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tab1 = inflater.inflate(R.layout.main_fragment_one, container, false);
		bitmapUtils = new BitmapUtils(getActivity());
		initView();
		(new MyThreadimgs()).start();
		return tab1;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView() {
		imageLoader = ImageLoader.getInstance();
		search1 = (TextView) tab1.findViewById(R.id.search1);
		main_fragment_shuiguo = (TextView) tab1
				.findViewById(R.id.main_fragment_shuiguo);
		main_fragment_shucai = (TextView) tab1
				.findViewById(R.id.main_fragment_shucai);
		main_fragment_roudanqin = (TextView) tab1
				.findViewById(R.id.main_fragment_roudanqin);
		main_fragment_dongpin = (TextView) tab1
				.findViewById(R.id.main_fragment_dongpin);
		main_fragment_shuichan = (TextView) tab1
				.findViewById(R.id.main_fragment_shuichan);
		main_fragment_ganguo = (TextView) tab1
				.findViewById(R.id.main_fragment_ganguo);
		main_fragment_gandiao = (TextView) tab1
				.findViewById(R.id.main_fragment_gandiao);
		main_fragment_shangchao = (TextView) tab1
				.findViewById(R.id.main_fragment_shangchao);
		main_fragment_jiagong = (TextView) tab1
				.findViewById(R.id.main_fragment_jiagong);
		main_fragment_order = (TextView) tab1
				.findViewById(R.id.main_fragment_order);
		main_fragment_shuiguo.setOnClickListener(this);
		main_fragment_shucai.setOnClickListener(this);
		main_fragment_roudanqin.setOnClickListener(this);
		main_fragment_dongpin.setOnClickListener(this);
		main_fragment_shuichan.setOnClickListener(this);
		main_fragment_ganguo.setOnClickListener(this);
		main_fragment_gandiao.setOnClickListener(this);
		main_fragment_shangchao.setOnClickListener(this);
		main_fragment_jiagong.setOnClickListener(this);
		main_fragment_order.setOnClickListener(this);

		mListView = (MyListView) tab1
				.findViewById(R.id.lv_main_fragment_classify);
		mListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				true, false));
		mList = new ArrayList<ClassListBean>();
		mAdapter = new MainFragmentClassListAdapter(getActivity(), mList,
				bitmapUtils);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search1:
			break;
		case R.id.main_fragment_shuiguo:
			goClassifyActivity(URLConstant.CLASS_FRUIT_ID);
			break;
		case R.id.main_fragment_shucai:
			goClassifyActivity(URLConstant.CLASS_VEGETABLES_ID);
			break;
		case R.id.main_fragment_roudanqin:
			goClassifyActivity(URLConstant.CLASS_EGG_ID);
			break;
		case R.id.main_fragment_dongpin:
			goClassifyActivity(URLConstant.CLASS_FROZEN_ID);
			break;
		case R.id.main_fragment_shuichan:
			goClassifyActivity(URLConstant.CLASS_WATER_ID);
			break;
		case R.id.main_fragment_ganguo:
			goClassifyActivity(URLConstant.CLASS_DRY_ID);
			break;
		case R.id.main_fragment_gandiao:
			goClassifyActivity(URLConstant.CLASS_FRAIN_ID);
			break;
		case R.id.main_fragment_shangchao:
			goClassifyActivity(URLConstant.CLASS_MARKET_ID);
			break;
		case R.id.main_fragment_jiagong:
			goClassifyActivity(URLConstant.CLASS_MACHINING_ID);
			break;
		case R.id.main_fragment_order:

			break;
		}
	}

	public void goClassifyActivity(int id) {
		Intent intent = new Intent(getActivity(), ClassifyActivity.class);
		intent.putExtra(URLConstant.CLASS_ID, id);
		startActivity(intent);
	}

	class classFiyThread extends Thread {
		@Override
		public void run() {
			super.run();

			String url = URLConstant.BASE + URLConstant.NEWSCLASS;
			Map<String, String> params = new HashMap<String, String>();
			params.put("parentid", " ");
			params.put("appindex", "1");
			params.put("sp_id", "52");
			VolleyHttpClient.getInstance(getActivity()).post(url, params, null,
					new Listener<String>() {

						@Override
						public void onResponse(String res) {
							try {
								JSONObject obj = new JSONObject(res);
								JSONArray jsonArray = obj
										.getJSONArray("recordset");
								for (int i = 0; i < jsonArray.length(); i++) {

								}
								initHeadView();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError arg0) {
						}
					});

		}
	}

	class MyThreadimgs extends Thread {// 广告轮播

		public void run() {
			if (NetWorkUtil.isNetworkAvailable(getActivity())) {
				flash_infos = new ArrayList<Flash_info>();
				String url = "http://www.wssxls.com/shop/app.php?act=newsclassad";
				Map<String, String> params = new HashMap<String, String>();
				params.put("ad_parentid", "71");
				VolleyHttpClient.getInstance(getActivity()).post(url, params,
						null, new Listener<String>() {

							@Override
							public void onResponse(String res) {
								try {
									JSONObject obj = new JSONObject(res);
									JSONArray jsonArray = obj
											.getJSONArray("recordset");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObject = (JSONObject) jsonArray
												.get(i);
										Flash_info flash_info = new Flash_info();
										flash_info.setAd_id(jsonObject
												.getString("ad_id"));
										flash_info.setAd_parentid(jsonObject
												.getString("ad_parentid"));
										flash_info.setAd_title(jsonObject
												.getString("ad_title"));
										flash_info.setAd_url(jsonObject
												.getString("ad_url"));
										flash_info.setAd_pic(jsonObject
												.getString("ad_pic"));
										flash_infos.add(flash_info);
									}
									initHeadView();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}, new ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError arg0) {
							}
						});
			}
		};
	}

	// 首页头部 banner listview的头部
	private void initHeadView() {
		main_myviewpager = (MyViewPager) tab1
				.findViewById(R.id.fragment1_viewpager_image);
		LinearLayout linear = (LinearLayout) tab1
				.findViewById(R.id.lin_layout_icon);
		imageList.clear();
		icons.clear();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		for (int i = 0; i < flash_infos.size(); i++) {
			ImageView imageview = new ImageView(getActivity());

			String path = flash_infos.get(i).getAd_pic();

			ImageLoader.getInstance().displayImage(path, imageview,
					Options.getListOptions());

			imageview.setScaleType(ScaleType.CENTER_INSIDE);
			imageview.setTag(i);
			imageList.add(imageview);
			ImageView icon = new ImageView(getActivity());
			icon.setImageResource(R.drawable.icon_gray);
			icon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			icon.setAdjustViewBounds(true);
			linear.addView(icon);
			icons.add(icon);
		}
		icons.get(0).setImageResource(R.drawable.icon_red);

		// main_myviewpager的自动轮询
		handler = new Handler() {// 在initView方法中

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:// main_myviewpager的自动滑动
					currentItem++;// 首先当前页加1
					if (currentItem >= imageComicAdapter.getCount()) {// 如果加1之后大于等于总页数
						currentItem = 0;// 置0 也就是当main_myviewpager是最后一页时
										// 再次滚动滚动到第0页
					}
					main_myviewpager.setCurrentItem(currentItem);//
					handler.sendEmptyMessageDelayed(0, 5000);// 隔5s发送空消息
																// msg的what为0
																// 所以这是一个死循环
					// 从而实现自动滑动
					break;
				case 1:
					ToastUtil.showSortToast(getActivity(), "没有更多数据");
					break;

				default:
					break;
				}
			}
		};
		imageComicAdapter = new ImageBannerAdapter(imageList, getActivity());
		imageComicAdapter.setCarousels(flash_infos);
		main_myviewpager.setOnPageChangeListener(new OnPageChangeListener() {// 轮播图点击事件

					@Override
					public void onPageSelected(int arg0) {
						// main_myviewpager图片底部点的设置和切换
						for (int i = 0; i < icons.size(); i++) {
							icons.get(i).setImageResource(R.drawable.icon_gray);
						}
						icons.get(arg0).setImageResource(R.drawable.icon_red);
						currentItem = arg0;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						main_myviewpager.getParent()
								.requestDisallowInterceptTouchEvent(true);
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
		main_myviewpager.setAdapter(imageComicAdapter);
		handler.sendEmptyMessageDelayed(0, 5000);
	}

	private RequestCallBack<String> callback = new RequestCallBack<String>() {

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			System.out.println(arg1);
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			MainFragmentClassBean classBean = GsonUtil.jsonToBean(arg0.result,
					MainFragmentClassBean.class);
			if (0 < mList.size()) {
				mList.clear();
			}
			mList.addAll(classBean.getClasslist());
			mAdapter.notifyDataSetChanged();
		}

	};

	// 获取分类
	public void getClassList(double longitude, double latitude) {
		String url = "http://www.wssxls.com/shop/app.php?act=appindex&lat="
				+ latitude + "&lng=" + longitude;
		if (null != httpUtils) {
			httpUtils.send(HttpMethod.GET, url, callback);
		}
	}

	public HttpUtils getHttpUtils() {
		return httpUtils;
	}

	public void setHttpUtils(HttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}

}
