package com.catdog.bean;

import java.io.Serializable;

public class ShopStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sp_id;
	private String sp_name;
	private String sp_user;
	private String sp_tel;
	private String sp_address;
	private String sp_lat;
	private String sp_lng;
	private String sp_juli;

	public String getSp_id() {
		return sp_id;
	}

	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	public String getSp_name() {
		return sp_name;
	}

	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}

	public String getSp_user() {
		return sp_user;
	}

	public void setSp_user(String sp_user) {
		this.sp_user = sp_user;
	}

	public String getSp_tel() {
		return sp_tel;
	}

	public void setSp_tel(String sp_tel) {
		this.sp_tel = sp_tel;
	}

	public String getSp_address() {
		return sp_address;
	}

	public void setSp_address(String sp_address) {
		this.sp_address = sp_address;
	}

	public String getSp_lat() {
		return sp_lat;
	}

	public void setSp_lat(String sp_lat) {
		this.sp_lat = sp_lat;
	}

	public String getSp_lng() {
		return sp_lng;
	}

	public void setSp_lng(String sp_lng) {
		this.sp_lng = sp_lng;
	}

	public String getSp_juli() {
		return sp_juli;
	}

	public void setSp_juli(String sp_juli) {
		this.sp_juli = sp_juli;
	}

};