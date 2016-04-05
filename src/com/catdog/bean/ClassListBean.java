package com.catdog.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String classid;
	private String classname;
	private String pic;
	private String picr;
	private String appad;
	private String contents;
	private String detailurl;
	private ArrayList<ClassListBean> classlist;
	private ArrayList<NewsListBean> newslist;

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPicr() {
		return picr;
	}

	public void setPicr(String picr) {
		this.picr = picr;
	}

	public String getAppad() {
		return appad;
	}

	public void setAppad(String appad) {
		this.appad = appad;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

	public ArrayList<ClassListBean> getClasslist() {
		return classlist;
	}

	public void setClasslist(ArrayList<ClassListBean> classlist) {
		this.classlist = classlist;
	}

	public ArrayList<NewsListBean> getNewslist() {
		return newslist;
	}

	public void setNewslist(ArrayList<NewsListBean> newslist) {
		this.newslist = newslist;
	}

}
