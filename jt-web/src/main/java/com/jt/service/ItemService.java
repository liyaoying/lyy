package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

public interface ItemService {

	Item fingIemById(Long itemId);

	ItemDesc fingIemDescById(Long itemId);

}
