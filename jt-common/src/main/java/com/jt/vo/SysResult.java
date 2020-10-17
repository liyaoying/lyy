package com.jt.vo;


//系统返回值对象
public class SysResult {
	private Integer status;//200表示成功
	private String msg; //后台返回的提示信息
	private Object data; //后台返回任意数据
	

	public SysResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	public SysResult() {
		super();
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public static SysResult ok() {
		return new SysResult(200,null,null);
	}
	public static SysResult ok(Object data) {
		return new SysResult(200,null,data);
	}
	public static SysResult ok(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}
	public static SysResult fail(String msg) {
		return new SysResult(201,msg,null);
	}
	@Override
	public String toString() {
		return "SysResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
