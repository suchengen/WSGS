package com.catdog.bean;

public class Flash_info {
	private String ad_id;//69",
	private String ad_parentid;//71",
	private String ad_title;//app首页广告",
	private String ad_url;//",
	private String ad_pic;//http://www.wssxls.com:80/shop/data/images/1451203669255.jpg"
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public String getAd_parentid() {
		return ad_parentid;
	}
	public void setAd_parentid(String ad_parentid) {
		this.ad_parentid = ad_parentid;
	}
	public String getAd_title() {
		return ad_title;
	}
	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}
	public String getAd_url() {
		return ad_url;
	}
	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}
	public String getAd_pic() {
		return ad_pic;
	}
	public void setAd_pic(String ad_pic) {
		this.ad_pic = ad_pic;
	}
	@Override
	public String toString() {
		return "Flash_info [ad_id=" + ad_id + ", ad_parentid=" + ad_parentid
				+ ", ad_title=" + ad_title + ", ad_url=" + ad_url + ", ad_pic="
				+ ad_pic + "]";
	}
}
