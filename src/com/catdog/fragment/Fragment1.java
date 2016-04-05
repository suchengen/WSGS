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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.catdog.activity.ClassifyActivity;
import com.catdog.activity.ShopDetailActivity;
import com.catdog.adapter.ClassifyAdapter;
import com.catdog.adapter.ClassifyListAdapter;
import com.catdog.adapter.ImageBannerAdapter;
import com.catdog.adapter.MainClassifyAdapter;
import com.catdog.bean.ClassifyListBean;
import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.bean.OneClassifyBean;
import com.catdog.bean.OneClassifyBean.Recordset;
import com.catdog.bean.ClassifyListBean.Recordsets;
import com.catdog.bean.Flash_info;
import com.catdog.helper.LoadImageHelper;
import com.catdog.util.HttpUtils;
import com.catdog.util.ResponseListener;
import com.catdog.util.ToastUtil;
import com.catdog.util.URLConstant;
import com.catdog.view.MyGridView;
import com.catdog.view.MyViewPager;
import com.catdog.view.Options;
import com.catdog.volly.NetWorkUtil;
import com.catdog.volly.VolleyHttpClient;
import com.catdog.wsgs.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.internal.RLScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Fragment1 extends Fragment implements OnClickListener {
	private View tab1;
	private MyViewPager fragment1_viewpager_image;
	private ArrayList<Flash_info> flash_infos;
	private MyViewPager main_myviewpager;
	private List<View> imageList = new ArrayList<View>();
	private List<ImageView> icons = new ArrayList<ImageView>();
	private Handler handler = new Handler();
	private LoadImageHelper imageHelper;
	private ImageLoader imageLoader;
	private MyGridView mGridView;
	private int currentItem;
	private ClassifyListAdapter mAdapter;
	private MainClassifyAdapter oneAdapter;
	private int row;
	private int currenpage = 1;
	private PullToRefreshScrollView mScrollView;
	private ImageBannerAdapter imageComicAdapter;// 首页banner的滑动
	private LinearLayout ll_one, ll_two, ll_three;
	private LinearLayout ll_cuxiao, ll_wanggou, ll_guoshu, ll_jingye;
	private ImageView img_banjia, img_jinkou, img_rou, img_haichanpin, search1;
	private MyGridView classifyGridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tab1 = inflater.inflate(R.layout.main_fragment1, container, false);
		initView();
		(new MyThreadimgs()).start();
		requestGanxingqu(currenpage);
		requestClassify();
		return tab1;
	}

	private void requestClassify() {
		// TODO 自动生成的方法存根
		HttpUtils.getDataFromVolley(getActivity(), URLConstant.ONE_CLASSIFY,
				null, null, oneclassifyListener);
	}

	private ResponseListener oneclassifyListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			OneClassifyBean bean = gson.fromJson(str, OneClassifyBean.class);
			if (bean.getRecordset() != null) {
				oneAdapter = new MainClassifyAdapter(getActivity(),
						bean.getRecordset());
				oneAdapter.notifyDataSetChanged();
				classifyGridView.setAdapter(oneAdapter);
				classifyGridView
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								// TODO 自动生成的方法存根
								Recordset bean = (Recordset) oneAdapter
										.getItem(position);
								Intent intent = new Intent(getActivity(),
										ClassifyActivity.class);
								intent.putExtra("classify", bean);
								intent.putExtra("tag", 2000);
								startActivity(intent);
							}
						});
			}

		}
	};

	private void requestGanxingqu(int currenpage) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", "126");
		map.put("tuijian", "0");
		map.put("row", "10");
		map.put("currenpage", "" + currenpage);
		HttpUtils.getDataFromVolley(getActivity(), URLConstant.TWO_CLASSIFY,
				null, map, twoListener);

	}

	private ResponseListener twoListener = new ResponseListener() {

		@Override
		public void getResponse(Object object) throws JSONException {
			// TODO Auto-generated method stub
			String str = (String) object;
			Gson gson = new Gson();
			ClassifyListBean bean = gson.fromJson(str, ClassifyListBean.class);
			List<Recordsets> list = bean.getRecordset();
			mGridView.setNumColumns(2);
			mAdapter = new ClassifyListAdapter(getActivity(), list);
			mGridView.setAdapter(mAdapter);
			mGridView.setOnItemClickListener(gridviewClickListener);
		}
	};

	private OnItemClickListener gridviewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
			Recordsets bean = (Recordsets) mAdapter.getItem(position);
			intent.putExtra("shopId", bean.getN_id());
			startActivity(intent);
		}
	};

	private void initView() {
		search1 = (ImageView) tab1.findViewById(R.id.search1);
		ll_one = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_one);
		ll_two = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_two);
		ll_three = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_three);
		ll_guoshu = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_guoshu);
		ll_cuxiao = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_cuxiao);
		ll_jingye = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_jingye);
		ll_wanggou = (LinearLayout) tab1.findViewById(R.id.mainFt_ll_wanggou);
		img_banjia = (ImageView) tab1.findViewById(R.id.mainFt_img_banjia);
		img_jinkou = (ImageView) tab1.findViewById(R.id.mainFt_img_jinkou);
		img_rou = (ImageView) tab1.findViewById(R.id.mainFt_img_rou);
		img_haichanpin = (ImageView) tab1
				.findViewById(R.id.mainFt_img_haichanpin);

		search1.setOnClickListener(this);
		ll_jingye.setOnClickListener(this);
		ll_guoshu.setOnClickListener(this);
		ll_cuxiao.setOnClickListener(this);
		ll_wanggou.setOnClickListener(this);
		img_banjia.setOnClickListener(this);
		img_jinkou.setOnClickListener(this);
		img_rou.setOnClickListener(this);
		img_haichanpin.setOnClickListener(this);
		ll_one.setOnClickListener(this);
		ll_two.setOnClickListener(this);
		ll_three.setOnClickListener(this);

		mScrollView = (PullToRefreshScrollView) tab1
				.findViewById(R.id.mainFt_sv);
		mScrollView.setMode(Mode.BOTH);
		mScrollView.setOnRefreshListener(new OnRefreshListener<RLScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<RLScrollView> refreshView) {
				// TODO Auto-generated method stub
				// (new MyThreadimgs()).start();
				mScrollView.onRefreshComplete();
			}
		});
		mGridView = (MyGridView) tab1.findViewById(R.id.mainFt_gv);
		classifyGridView = (MyGridView) tab1
				.findViewById(R.id.mainFt_gv_classify);
		imageHelper = new LoadImageHelper(getActivity());
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public void onClick(View v) {
		/**
		 * jian=0推荐（ 活动推广：1，网购钜惠：2， 天天半价：3，进口推荐：4， 果蔬类：5，茎叶类：6， 肉类：7，海产品：8）
		 */
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.search1:
			intent = new Intent(getActivity(), SearchAtivity.class);
			startActivity(intent);
			break;
		case R.id.mainFt_ll_cuxiao: // 活动推广
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 1);
			startActivity(intent);
			break;
		case R.id.mainFt_ll_wanggou: // 网购优惠
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 2);
			startActivity(intent);

			break;
		case R.id.mainFt_img_banjia: // 天天半价
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 3);
			startActivity(intent);
			break;
		case R.id.mainFt_img_jinkou: // 进口
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 4);
			startActivity(intent);
			break;

		case R.id.mainFt_ll_guoshu: // 果蔬
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 5);
			startActivity(intent);
			break;
		case R.id.mainFt_ll_jingye: // 茎叶
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 6);
			startActivity(intent);
			break;
		case R.id.mainFt_img_rou: // 肉
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 7);
			startActivity(intent);
			break;
		case R.id.mainFt_img_haichanpin: // 海产品
			intent = new Intent(getActivity(), ClassifyActivity.class);
			intent.putExtra("tag", 8);
			startActivity(intent);

			break;
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
}
