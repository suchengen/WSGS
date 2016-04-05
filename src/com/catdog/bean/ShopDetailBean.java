package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

public class ShopDetailBean implements Serializable {
	/**
	 *     "n_id": "150",
    "shopid": "10000078",
    "n_pagetitle": "闻氏生鲜网上订购系统",
    "n_keywords": "闻氏生鲜、闻氏果业、闻氏生鲜连锁",
    "n_miaoshu": "闻氏生鲜、闻氏果业、闻氏生鲜连锁网上商城系统",
    "n_parentid": "227",
    "n_title": "【闻氏生鲜】黄元帅苹果 - 约2斤",
    "n_site": "0",
    "n_scj": "0.00",
    "n_zhekou": "0.00",
    "n_zkj": "7.90",
    "n_jifen": "0",
    "n_area": "sdf",
    "n_url": "0",
    "n_pic": "1448353075192.jpg",
    "n_focus": [
        "http://www.wssxls.com:80/shop/data/images/1448353072227.jpg",
        "http://www.wssxls.com:80/shop/data/images/1450318582646.jpg"
    ],

	 */
	private String n_detailurl;
	private String n_id;
	private String shopid;
	private String n_pagetitle;
	private String n_keywords;
	private String n_miaoshu;
	private String n_parentid;
	private String n_title;
	private String n_site;
	private String n_scj;
	private String n_zhekou;
	private String n_zkj;
	private String n_jifen;
	private String n_area;
	private String n_url;
	private String n_pic;
	private NFocus n_focus_key;

	/**
	 *     "n_file": "",
    "n_content": "商品编码：10000078销售码：厂名：厂址：厂家联系方式：配料表：储藏方法：保质期：食品添加剂：无净含量：约2斤 包装方式：其他食品类型：初级农产品 品牌：热卖时间：全年产地：中国大陆省份：吉林省城市：长春市特产品类：",
    "n_tuijian": "",
    "n_ding": "0",
    "n_hits": "0",
    "n_count": "0",
    "n_sorting": "20",
    "n_pass": "0",
    "n_addtime": "1450318588",
    "n_metype": "",
    "n_zhongliang": "5个/约1.9-2.1斤/份",
    "n_jibie": "A",
    "n_unit": "份",
    "n_xiaoliang": "0",
    "n_pinglun": "1",
    "n_xing": "5",
    "n_fahuo": "次日09:00",
    "n_jiedan": "20:00"
	 */
	private String n_file;
	private String n_content;
	private String n_tuijian;
	private String n_ding;
	private String n_hits;
	private String n_count;
	public String getN_content() {
		return n_content;
	}


	public void setN_content(String n_content) {
		this.n_content = n_content;
	}


	private String n_sorting;
	private String n_pass;
	private String n_addtime;
	private String n_metype;
	private String n_zhongliang;
	private String n_jibie;
	private String n_unit;
	private String n_xiaoliang;
	private String n_pinglun;
	private String n_xing;
	private String n_fahuo;
	private String n_jiedan;


	public NFocus getN_focus_key() {
		return n_focus_key;
	}


	public void setN_focus_key(NFocus n_focus_key) {
		this.n_focus_key = n_focus_key;
	}


	public class NFocus{
		private String index_pic_0;
		private String index_pic_1;
		private String index_pic_2;
		private String index_pic_3;
		private String index_pic_4;
		private String index_pic_5;
		private String index_pic_6;
		private String index_pic_7;
		private String index_pic_8;
		private String index_pic_9;
		private String index_pic_10;
		@Override
		public String toString() {
			return index_pic_0 + ","+ index_pic_1 + "," + index_pic_2+ "," + index_pic_3 + ","
					+ index_pic_4 + "," + index_pic_5+ "," + index_pic_6 + ","
					+ index_pic_7 + "," + index_pic_8+ "," + index_pic_9 + ","+ index_pic_10 + "]";
		}



	}


	public String getN_id() {
		return n_id;
	}


	public void setN_id(String n_id) {
		this.n_id = n_id;
	}


	public String getShopid() {
		return shopid;
	}


	public void setShopid(String shopid) {
		this.shopid = shopid;
	}


	public String getN_pagetitle() {
		return n_pagetitle;
	}


	public void setN_pagetitle(String n_pagetitle) {
		this.n_pagetitle = n_pagetitle;
	}


	public String getN_keywords() {
		return n_keywords;
	}


	public void setN_keywords(String n_keywords) {
		this.n_keywords = n_keywords;
	}


	public String getN_miaoshu() {
		return n_miaoshu;
	}


	public void setN_miaoshu(String n_miaoshu) {
		this.n_miaoshu = n_miaoshu;
	}


	public String getN_parentid() {
		return n_parentid;
	}


	public void setN_parentid(String n_parentid) {
		this.n_parentid = n_parentid;
	}


	public String getN_title() {
		return n_title;
	}


	public void setN_title(String n_title) {
		this.n_title = n_title;
	}


	public String getN_site() {
		return n_site;
	}


	public void setN_site(String n_site) {
		this.n_site = n_site;
	}


	public String getN_scj() {
		return n_scj;
	}


	public void setN_scj(String n_scj) {
		this.n_scj = n_scj;
	}


	public String getN_zhekou() {
		return n_zhekou;
	}


	public void setN_zhekou(String n_zhekou) {
		this.n_zhekou = n_zhekou;
	}


	public String getN_zkj() {
		return n_zkj;
	}


	public void setN_zkj(String n_zkj) {
		this.n_zkj = n_zkj;
	}


	public String getN_jifen() {
		return n_jifen;
	}


	public void setN_jifen(String n_jifen) {
		this.n_jifen = n_jifen;
	}


	public String getN_area() {
		return n_area;
	}


	public void setN_area(String n_area) {
		this.n_area = n_area;
	}


	public String getN_url() {
		return n_url;
	}


	public void setN_url(String n_url) {
		this.n_url = n_url;
	}


	public String getN_pic() {
		return n_pic;
	}


	public void setN_pic(String n_pic) {
		this.n_pic = n_pic;
	}

	public String getN_file() {
		return n_file;
	}


	public void setN_file(String n_file) {
		this.n_file = n_file;
	}


	public String getN_tuijian() {
		return n_tuijian;
	}


	public void setN_tuijian(String n_tuijian) {
		this.n_tuijian = n_tuijian;
	}


	public String getN_ding() {
		return n_ding;
	}


	public void setN_ding(String n_ding) {
		this.n_ding = n_ding;
	}


	public String getN_hits() {
		return n_hits;
	}


	public void setN_hits(String n_hits) {
		this.n_hits = n_hits;
	}


	public String getN_count() {
		return n_count;
	}


	public void setN_count(String n_count) {
		this.n_count = n_count;
	}


	public String getN_sorting() {
		return n_sorting;
	}


	public void setN_sorting(String n_sorting) {
		this.n_sorting = n_sorting;
	}


	public String getN_pass() {
		return n_pass;
	}


	public void setN_pass(String n_pass) {
		this.n_pass = n_pass;
	}


	public String getN_addtime() {
		return n_addtime;
	}


	public void setN_addtime(String n_addtime) {
		this.n_addtime = n_addtime;
	}


	public String getN_metype() {
		return n_metype;
	}


	public void setN_metype(String n_metype) {
		this.n_metype = n_metype;
	}


	public String getN_zhongliang() {
		return n_zhongliang;
	}


	public void setN_zhongliang(String n_zhongliang) {
		this.n_zhongliang = n_zhongliang;
	}


	public String getN_jibie() {
		return n_jibie;
	}


	public void setN_jibie(String n_jibie) {
		this.n_jibie = n_jibie;
	}


	public String getN_unit() {
		return n_unit;
	}


	public void setN_unit(String n_unit) {
		this.n_unit = n_unit;
	}


	public String getN_xiaoliang() {
		return n_xiaoliang;
	}


	public void setN_xiaoliang(String n_xiaoliang) {
		this.n_xiaoliang = n_xiaoliang;
	}


	public String getN_pinglun() {
		return n_pinglun;
	}


	public void setN_pinglun(String n_pinglun) {
		this.n_pinglun = n_pinglun;
	}


	public String getN_xing() {
		return n_xing;
	}


	public void setN_xing(String n_xing) {
		this.n_xing = n_xing;
	}


	public String getN_fahuo() {
		return n_fahuo;
	}


	public void setN_fahuo(String n_fahuo) {
		this.n_fahuo = n_fahuo;
	}


	public String getN_jiedan() {
		return n_jiedan;
	}


	public void setN_jiedan(String n_jiedan) {
		this.n_jiedan = n_jiedan;
	}


	public String getN_detailurl() {
		return n_detailurl;
	}


	public void setN_detailurl(String n_detailurl) {
		this.n_detailurl = n_detailurl;
	}




}
