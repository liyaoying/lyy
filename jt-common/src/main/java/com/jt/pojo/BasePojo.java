package com.jt.pojo;

import java.io.Serializable;
import java.util.Date;


//pojo基类，完成2个任务，2个日期，实现序列化
public class BasePojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -608348803541737362L;
	private Date created;
	private Date updated;
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	@Override
	public String toString() {
		return "BasePojo [created=" + created + ", updated=" + updated + "]";
	}
	
	
}