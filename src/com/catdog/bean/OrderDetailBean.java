package com.catdog.bean;

import java.util.List;

public class OrderDetailBean {
	private String ord_id;//838",
	private String ord_orid;//585",
	private String ord_ordanhao;//201512261039107472",
	private String ord_dhid;//671",
	private String ord_title;//【闻氏生鲜】毛丹",
	private String ord_num;//3",
	private String ord_zkj;//18.00",
	private String ord_pic;//http://www.wssxls.com:80/shop/data/images/1450312757323.jpg"
	public String getOrd_id() {
		return ord_id;
	}
	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	public String getOrd_orid() {
		return ord_orid;
	}
	public void setOrd_orid(String ord_orid) {
		this.ord_orid = ord_orid;
	}
	public String getOrd_ordanhao() {
		return ord_ordanhao;
	}
	public void setOrd_ordanhao(String ord_ordanhao) {
		this.ord_ordanhao = ord_ordanhao;
	}
	public String getOrd_dhid() {
		return ord_dhid;
	}
	public void setOrd_dhid(String ord_dhid) {
		this.ord_dhid = ord_dhid;
	}
	public String getOrd_title() {
		return ord_title;
	}
	public void setOrd_title(String ord_title) {
		this.ord_title = ord_title;
	}
	public String getOrd_num() {
		return ord_num;
	}
	public void setOrd_num(String ord_num) {
		this.ord_num = ord_num;
	}
	public String getOrd_zkj() {
		return ord_zkj;
	}
	public void setOrd_zkj(String ord_zkj) {
		this.ord_zkj = ord_zkj;
	}
	public String getOrd_pic() {
		return ord_pic;
	}
	public void setOrd_pic(String ord_pic) {
		this.ord_pic = ord_pic;
	}
	@Override
	public String toString() {
		return "OrderInfoBean [ord_id=" + ord_id + ", ord_orid=" + ord_orid
				+ ", ord_ordanhao=" + ord_ordanhao + ", ord_dhid=" + ord_dhid
				+ ", ord_title=" + ord_title + ", ord_num=" + ord_num
				+ ", ord_zkj=" + ord_zkj + ", ord_pic=" + ord_pic + "]";
	}
}
