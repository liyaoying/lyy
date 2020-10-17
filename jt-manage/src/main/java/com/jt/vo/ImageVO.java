package com.jt.vo;

import java.io.Serializable;

public class ImageVO implements Serializable{
	private static final long serialVersionUID = -8019017074323385599L;
	private Integer error; //0表示成功,1表示失败
	private String url;
	private Integer width;
	private Integer height;
	
	
	public ImageVO() {
		super();
	}
	public ImageVO(Integer error, String url, Integer width, Integer height) {
		super();
		this.error = error;
		this.url = url;
		this.width = width;
		this.height = height;
	}
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "ImageVO [error=" + error + ", url=" + url + ", width=" + width + ", height=" + height + "]";
	}
	
	
}
