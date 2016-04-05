package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

public class OneClassifyBean implements Serializable {
	/**
	 *      "recordset": [
        {
            "classid": "142",
            "classname": "水果",
            "pic": "http://www.wssxls.com:80/shop/data/images/1448847554494.gif",
            "picr": "http://www.wssxls.com:80/shop/data/images/1448847642488.jpg",
            "contents": "",
            "classlist": [
                {
                    "classid": "227",
                    "classname": "果类",
                    "contents": ""
                },
	 */
	
	private List<Recordset> recordset;
	

	public List<Recordset> getRecordset() {
		return recordset;
	}


	public void setRecordset(List<Recordset> recordset) {
		this.recordset = recordset;
	}


	public class Recordset implements Serializable{
		private String classid;
		private String classname;
		private String pic;
		private String picr;
		private String contents;
		private List<Classlist> classlist;

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
		public String getContents() {
			return contents;
		}
		public void setContents(String contents) {
			this.contents = contents;
		}



		public List<Classlist> getClasslist() {
			return classlist;
		}
		public void setClasslist(List<Classlist> classlist) {
			this.classlist = classlist;
		}



		/**
		 *  {
                    "classid": "227",
                    "classname": "果类",
                    "contents": ""
                },
		 * @author sumomogenbi
		 *
		 */
		public class Classlist implements Serializable{
			private String classid;
			private String classname;
			private String contents;
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
			public String getContents() {
				return contents;
			}
			public void setContents(String contents) {
				this.contents = contents;
			}


		}
	}

}
