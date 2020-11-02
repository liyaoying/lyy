package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import com.jt.pojo.Item;


public class EasyUIData implements Serializable{
	//序列化
	private static final long serialVersionUID = 1968115217587984105L;
	private Integer total;
	private List<Item> rows;
	
	public EasyUIData() {
		super();
	}
	public EasyUIData(Integer total, List<Item> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Item> getRows() {
		return rows;
	}
	public void setRows(List<Item> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIData [total=" + total + ", rows=" + rows + "]";
	}
	
	
}
