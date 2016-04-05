package com.catdog.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.catdog.util.SharePreferenceUtil;
import com.catdog.util.ToastUtil;
import com.catdog.volly.NetWorkUtil;

public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {
	private ImageView imageView;
	private List<Fragment> fragments; // 一个tab页面对应一个Fragment
	private RadioGroup rgs; // 用于切换tab
	private FragmentManager fragmentManager; // Fragment所属的Activity
	private int fragmentContentId; // Activity中所要被替换的区域的id
	private int currentTab; // 当前Tab页面索引
	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能
	private Context mainActivity;
//	private SharePreferenceUtil sharePreferenceUtil;
//	private int user_id;
	public FragmentTabAdapter(Context mainActivity,FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.fragmentManager = fragmentManager;
		this.fragmentContentId = fragmentContentId;
		this.mainActivity = mainActivity;
		// 默认显示第一页
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(fragmentContentId, fragments.get(0));
		ft.addToBackStack(fragments.get(0).getClass().getName());
		ft.commit();
		rgs.setOnCheckedChangeListener(this);
//		sharePreferenceUtil = new SharePreferenceUtil(mainActivity, "users");
//		user_id = sharePreferenceUtil.getInt("user_id");

	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//		if (user_id == 0) {
//			ToastUtil.showSortToast(mainActivity, "您未登录");
//		}else{
//			if (NetWorkUtil.isNetworkAvailable(mainActivity)) {
				for (int i = 0; i < rgs.getChildCount(); i++) {
					if (rgs.getChildAt(i).getId() == checkedId) {
						Fragment fragment = fragments.get(i);
						FragmentTransaction ft = obtainFragmentTransaction(i);
						// getCurrentFragment().onPause(); // 暂停当前tab
						getCurrentFragment().onStop(); // 暂停当前tab
						if (fragment.isAdded()) {
							fragment.onStart(); // 启动目标tab的onStart()
							// fragment.onResume(); // 启动目标tab的onResume()
						} else {
							ft.addToBackStack(fragment.getClass().getName());
							ft.add(fragmentContentId, fragment);
						}
						showTab(i); // 显示目标tab
						ft.commit();

						// 如果设置了切换tab额外功能功能接口
						if (null != onRgsExtraCheckedChangedListener) {
							onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
						}

					}
//				}
//			}else{
//				ToastUtil.showSortToast(mainActivity, "网络不可用,请检测网络连接!");
//			}
		}
	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	private void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);
			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// 设置切换动画
		//		if (index > currentTab) {
		//			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		//		} else {
		//			ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
		//		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}
	/**
	 * 切换tab额外功能功能接口
	 */
	public static interface OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
	}

}
