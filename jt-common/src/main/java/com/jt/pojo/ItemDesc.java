package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -388974915400233145L;
	@TableId
	private Long itemId;
	private String itemDesc;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	@Override
	public String toString() {
		return "ItemDesc [itemId=" + itemId + ", itemDesc=" + itemDesc + "]";
	}
	
}
