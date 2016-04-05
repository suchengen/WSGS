package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

public class AddressBean implements Serializable {
	private List<Address> recordset;


	public List<Address> getRecordset() {
		return recordset;
	}


	public void setRecordset(List<Address> recordset) {
		this.recordset = recordset;
	}


	/**
	 *  "recordset": [
        {
            "dz_id": "250",
            "dz_uname": "王先生",
            "dz_tel": "15543593777",
            "dz_phone": "043112345678",
            "dz_sheng": "吉林省",
            "dz_shi": "长春市",
            "dz_qu": "宽城区",
            "dz_address": "青年路",
            "dz_lng": "43.949592",
            "dz_lat": "125.308427",
            "dz_default": "0"
        },
	 */
	public class Address{
		private String dz_id;
		private String dz_uname;
		private String dz_tel;
		private String dz_phone;
		private String dz_sheng;
		private String dz_shi;
		private String dz_qu;
		private String dz_address;
		private String dz_lng;
		private String dz_lat;
		private String dz_default;
		public String getDz_id() {
			return dz_id;
		}
		public void setDz_id(String dz_id) {
			this.dz_id = dz_id;
		}
		public String getDz_uname() {
			return dz_uname;
		}
		public void setDz_uname(String dz_uname) {
			this.dz_uname = dz_uname;
		}
		public String getDz_tel() {
			return dz_tel;
		}
		public void setDz_tel(String dz_tel) {
			this.dz_tel = dz_tel;
		}
		public String getDz_phone() {
			return dz_phone;
		}
		public void setDz_phone(String dz_phone) {
			this.dz_phone = dz_phone;
		}
		public String getDz_sheng() {
			return dz_sheng;
		}
		public void setDz_sheng(String dz_sheng) {
			this.dz_sheng = dz_sheng;
		}
		public String getDz_shi() {
			return dz_shi;
		}
		public void setDz_shi(String dz_shi) {
			this.dz_shi = dz_shi;
		}
		public String getDz_qu() {
			return dz_qu;
		}
		public void setDz_qu(String dz_qu) {
			this.dz_qu = dz_qu;
		}
		public String getDz_address() {
			return dz_address;
		}
		public void setDz_address(String dz_address) {
			this.dz_address = dz_address;
		}
		public String getDz_lng() {
			return dz_lng;
		}
		public void setDz_lng(String dz_lng) {
			this.dz_lng = dz_lng;
		}
		public String getDz_lat() {
			return dz_lat;
		}
		public void setDz_lat(String dz_lat) {
			this.dz_lat = dz_lat;
		}
		public String getDz_default() {
			return dz_default;
		}
		public void setDz_default(String dz_default) {
			this.dz_default = dz_default;
		}
	}
}
