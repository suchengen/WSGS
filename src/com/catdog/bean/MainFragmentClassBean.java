package com.catdog.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MainFragmentClassBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ClassListBean> classlist;

	private String shoplist;

	public ArrayList<ClassListBean> getClasslist() {
		return classlist;
	}

	public void setClasslist(ArrayList<ClassListBean> classlist) {
		this.classlist = classlist;
	}

	public String getShoplist() {
		return shoplist;
	}

	public void setShoplist(String shoplist) {
		this.shoplist = shoplist;
	}

}
