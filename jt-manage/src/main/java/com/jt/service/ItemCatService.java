package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITree;

public interface ItemCatService {

	String queryItemName(Long itemCatId);

	

	List<EasyUITree> findEasyUITreeById(Long parentId);



	//List<EasyUITree> findEasyUITreeByCache(Long parentId);

}
