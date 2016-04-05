package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable {
	
	/**
	 * "recordset": [
        {
            "pl_nid": "150",
            "pl_meid": "86",
            "pl_xing": "5",
            "pl_content": "非常好。",
            "pl_addtime": "2015-12-07",
            "pl_mepic": "http://www.wssxls.com:80/shop/data/images/",
            "pl_meuid": "酸奶不酸"
        }
    ]

	 */
	private List<Recordset> recordset;
	
	
	public List<Recordset> getRecordset() {
		return recordset;
	}


	public void setRecordset(List<Recordset> recordset) {
		this.recordset = recordset;
	}


	public class Recordset{
		private String pl_nid;
		private String pl_meid;
		private String pl_xing;
		private String pl_content;
		private String pl_addtime;
		private String pl_mepic;
		private String pl_meuid;
		public String getPl_nid() {
			return pl_nid;
		}
		public void setPl_nid(String pl_nid) {
			this.pl_nid = pl_nid;
		}
		public String getPl_meid() {
			return pl_meid;
		}
		public void setPl_meid(String pl_meid) {
			this.pl_meid = pl_meid;
		}
		public String getPl_xing() {
			return pl_xing;
		}
		public void setPl_xing(String pl_xing) {
			this.pl_xing = pl_xing;
		}
		public String getPl_content() {
			return pl_content;
		}
		public void setPl_content(String pl_content) {
			this.pl_content = pl_content;
		}
		public String getPl_addtime() {
			return pl_addtime;
		}
		public void setPl_addtime(String pl_addtime) {
			this.pl_addtime = pl_addtime;
		}
		public String getPl_mepic() {
			return pl_mepic;
		}
		public void setPl_mepic(String pl_mepic) {
			this.pl_mepic = pl_mepic;
		}
		public String getPl_meuid() {
			return pl_meuid;
		}
		public void setPl_meuid(String pl_meuid) {
			this.pl_meuid = pl_meuid;
		}
		
		
	}
}
