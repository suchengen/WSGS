package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

public class CollectBean implements Serializable {

	/**
	 *     "recordset": [
        {
            "sc_id": "29",
            "sc_nid": "146",
            "sc_ntitle": "【闻氏生鲜】九红瓜 - 约4.5斤",
            "sc_npic": "http://www.wssxls.com:80/shop//data/images/1448353176767.jpg",
            "sc_nzkj": "15.75"
        },
	 */
	
	private List<Recordset> recordset;
	
	public List<Recordset> getRecordset() {
		return recordset;
	}



	public void setRecordset(List<Recordset> recordset) {
		this.recordset = recordset;
	}



	public class Recordset{
		private String sc_id;
		private String sc_nid;
		private String sc_ntitle;
		private String sc_npic;
		private String sc_nzkj;
		private String sc_pinglun;
		public String getSc_id() {
			return sc_id;
		}
		public void setSc_id(String sc_id) {
			this.sc_id = sc_id;
		}
		public String getSc_nid() {
			return sc_nid;
		}
		public void setSc_nid(String sc_nid) {
			this.sc_nid = sc_nid;
		}
		public String getSc_ntitle() {
			return sc_ntitle;
		}
		public void setSc_ntitle(String sc_ntitle) {
			this.sc_ntitle = sc_ntitle;
		}
		public String getSc_npic() {
			return sc_npic;
		}
		public void setSc_npic(String sc_npic) {
			this.sc_npic = sc_npic;
		}
		public String getSc_nzkj() {
			return sc_nzkj;
		}
		public void setSc_nzkj(String sc_nzkj) {
			this.sc_nzkj = sc_nzkj;
		}
		public String getSc_pinglun() {
			return sc_pinglun;
		}
		public void setSc_pinglun(String sc_pinglun) {
			this.sc_pinglun = sc_pinglun;
		}
		
		
	}
}
