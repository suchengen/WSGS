package com.catdog.bean;

import java.io.Serializable;
import java.util.List;

import android.view.View.OnClickListener;

public class ClassifyListBean {
	private List<Recordsets> recordset;


	public List<Recordsets> getRecordset() {
		return recordset;
	}


	public void setRecordset(List<Recordsets> recordset) {
		this.recordset = recordset;
	}


	@Override
	public String toString() {
		return "ClassifyListBean [recordset=" + recordset + "]";
	}


	public class Recordsets implements Serializable
	{
		/**
		 *   {
            "n_pic": "http://www.wssxls.com:80/shop/data/images/1449286888868.jpg",
            "n_id": "289",
            "n_title": "【闻氏生鲜】土豆 - 1斤",
            "n_zkj": "0.59",
            "n_scj": "0.00",
            "n_xiaoliang": "218",
            "n_ding": "1",
            "n_pinglun": "0",
            "n_content": "商品编号：10000147销售码：1026厂名：厂址：厂家联系方式：配料表：储藏方法：保质期：食品添加剂：无净含量：1斤包装方式：其他食品类型：蔬菜品牌：热卖时间：全年产地：中国大陆省份：吉林省城市：长春市特产品类："
        },
		 */

		private String n_pic;
		private String n_id;
		private String n_title;
		private String n_zkj;
		private String n_scj;
		private String n_xiaoliang;
		private String n_ding;
		private String n_pinglun;
		private String n_content;
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
		public String getN_ding() {
			return n_ding;
		}
		public void setN_ding(String n_ding) {
			this.n_ding = n_ding;
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
		@Override
		public String toString() {
			return "Recordset [n_pic=" + n_pic + ", n_id=" + n_id
					+ ", n_title=" + n_title + ", n_zkj=" + n_zkj + ", n_scj="
					+ n_scj + ", n_xiaoliang=" + n_xiaoliang + ", n_ding="
					+ n_ding + ", n_pinglun=" + n_pinglun + ", n_content="
					+ n_content + "]";
		}
	}
}
