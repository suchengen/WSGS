package com.catdog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String n_pic;
	private String n_id;
	private String n_title;
	private String n_zkj;
	private String n_scj;
	private String n_xiaoliang;
	private String n_kucun;
	private String n_pinglun;
	private String n_content;
	private ArrayList<String> n_focus;
	private FocusKeyBean n_focus_key;

	public String getN_pic() {
		return n_pic;
	}

	public void setN_pic(String n_pic) {
		this.n_pic = n_pic;
	}

	public String getN_id() {
		return n_id;
	}

	public void setN_id(String n_id) {
		this.n_id = n_id;
	}

	public String getN_title() {
		return n_title;
	}

	public void setN_title(String n_title) {
		this.n_title = n_title;
	}

	public String getN_zkj() {
		return n_zkj;
	}

	public void setN_zkj(String n_zkj) {
		this.n_zkj = n_zkj;
	}

	public String getN_scj() {
		return n_scj;
	}

	public void setN_scj(String n_scj) {
		this.n_scj = n_scj;
	}

	public String getN_xiaoliang() {
		return n_xiaoliang;
	}

	public void setN_xiaoliang(String n_xiaoliang) {
		this.n_xiaoliang = n_xiaoliang;
	}

	public String getN_kucun() {
		return n_kucun;
	}

	public void setN_kucun(String n_kucun) {
		this.n_kucun = n_kucun;
	}

	public String getN_pinglun() {
		return n_pinglun;
	}

	public void setN_pinglun(String n_pinglun) {
		this.n_pinglun = n_pinglun;
	}

	public String getN_content() {
		return n_content;
	}

	public void setN_content(String n_content) {
		this.n_content = n_content;
	}

	public List<String> getN_focus() {
		return n_focus;
	}

	public void setN_focus(ArrayList<String> n_focus) {
		this.n_focus = n_focus;
	}

	public FocusKeyBean getN_focus_key() {
		return n_focus_key;
	}

	public void setN_focus_key(FocusKeyBean n_focus_key) {
		this.n_focus_key = n_focus_key;
	}

}
