package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.ShardedJedis;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
   @Autowired
   private ItemCatService itemCatService ;
  
   @RequestMapping("/queryItemName")
   public String queryItemName(Long itemCatId) {
	   String name=itemCatService.queryItemName(itemCatId);
	   return name;
   }
   //@Cache_Find(key="ITME_CAT",keyType=KEY_ENUM.AUTO)
   @RequestMapping("/list")
   public List<EasyUITree> findEasyUITrees(@RequestParam(value="id",defaultValue="0")Long parentId) {
	  return itemCatService.findEasyUITreeById(parentId);
	    //return itemCatService.findEasyUITreeByCache(parentId);
   }
}
