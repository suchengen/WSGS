package com.catdog.bean;

import java.util.List;

public class OrderBean {        
	private List<OrderInfoBean> recordset;
	public List<OrderInfoBean> getRecordset() {
		return recordset;
	}
	public void setRecordset(List<OrderInfoBean> recordset) {
		this.recordset = recordset;
	}
	public class OrderInfoBean{
		private String or_status;
		/*
		 * 	1-全部；
			2-未支付
			3-未发货
			4-未收货
			5-未评价
			6-交易结束
			7-退换货
		 */
		private String or_id;//595",
		private String or_meid;//53",
		private String or_payok;//0",
		private String or_peisong;//立即配送",
		private String or_danhao;//201512261737304278",
		private String or_name;//",
		private String or_tel;//",
		private String or_sheng;//",
		private String or_shi;//",
		private String or_qu;//",
		private String or_address;//",
		private String or_content;//",
		private String or_addtime;//15-12-26",
		private String or_pass;//0",
		private String or_pay;//100.00",
		private String or_paytype;//0",
		private String or_yunfei;//0",
		private OrsiteBean or_site;//{
		private List<OrderDetailBean> or_detail;//{
		
		public List<OrderDetailBean> getOr_detail() {
			return or_detail;
		}
		public void setOr_detail(List<OrderDetailBean> or_detail) {
			this.or_detail = or_detail;
		}
		public String getOr_status() {
			return or_status;
		}
		public void setOr_status(String or_status) {
			this.or_status = or_status;
		}
		public String getOr_id() {
			return or_id;
		}
		public void setOr_id(String or_id) {
			this.or_id = or_id;
		}
		public String getOr_meid() {
			return or_meid;
		}
		public void setOr_meid(String or_meid) {
			this.or_meid = or_meid;
		}
		public String getOr_payok() {
			return or_payok;
		}
		public void setOr_payok(String or_payok) {
			this.or_payok = or_payok;
		}
		public String getOr_peisong() {
			return or_peisong;
		}
		public void setOr_peisong(String or_peisong) {
			this.or_peisong = or_peisong;
		}
		public String getOr_danhao() {
			return or_danhao;
		}
		public void setOr_danhao(String or_danhao) {
			this.or_danhao = or_danhao;
		}
		public String getOr_name() {
			return or_name;
		}
		public void setOr_name(String or_name) {
			this.or_name = or_name;
		}
		public String getOr_tel() {
			return or_tel;
		}
		public void setOr_tel(String or_tel) {
			this.or_tel = or_tel;
		}
		public String getOr_sheng() {
			return or_sheng;
		}
		public void setOr_sheng(String or_sheng) {
			this.or_sheng = or_sheng;
		}
		public String getOr_shi() {
			return or_shi;
		}
		public void setOr_shi(String or_shi) {
			this.or_shi = or_shi;
		}
		public String getOr_qu() {
			return or_qu;
		}
		public void setOr_qu(String or_qu) {
			this.or_qu = or_qu;
		}
		public String getOr_address() {
			return or_address;
		}
		public void setOr_address(String or_address) {
			this.or_address = or_address;
		}
		public String getOr_content() {
			return or_content;
		}
		public void setOr_content(String or_content) {
			this.or_content = or_content;
		}
		public String getOr_addtime() {
			return or_addtime;
		}
		public void setOr_addtime(String or_addtime) {
			this.or_addtime = or_addtime;
		}
		public String getOr_pass() {
			return or_pass;
		}
		public void setOr_pass(String or_pass) {
			this.or_pass = or_pass;
		}
		public String getOr_pay() {
			return or_pay;
		}
		public void setOr_pay(String or_pay) {
			this.or_pay = or_pay;
		}
		public String getOr_paytype() {
			return or_paytype;
		}
		public void setOr_paytype(String or_paytype) {
			this.or_paytype = or_paytype;
		}
		public String getOr_yunfei() {
			return or_yunfei;
		}
		public void setOr_yunfei(String or_yunfei) {
			this.or_yunfei = or_yunfei;
		}
		public OrsiteBean getOr_site() {
			return or_site;
		}
		public void setOr_site(OrsiteBean or_site) {
			this.or_site = or_site;
		}
		@Override
		public String toString() {
			return "OrderInfoBean [or_status=" + or_status + ", or_id=" + or_id
					+ ", or_meid=" + or_meid + ", or_payok=" + or_payok
					+ ", or_peisong=" + or_peisong + ", or_danhao=" + or_danhao
					+ ", or_name=" + or_name + ", or_tel=" + or_tel
					+ ", or_sheng=" + or_sheng + ", or_shi=" + or_shi
					+ ", or_qu=" + or_qu + ", or_address=" + or_address
					+ ", or_content=" + or_content + ", or_addtime="
					+ or_addtime + ", or_pass=" + or_pass + ", or_pay="
					+ or_pay + ", or_paytype=" + or_paytype + ", or_yunfei="
					+ or_yunfei + ", or_site=" + or_site + ", or_detail="
					+ or_detail + "]";
		}
	}
}
