package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.RedisService;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
//	@Autowired
//	private RedisService shardedJedis;
	

	@Override
	public String queryItemName(Long itemCatId) {
		String name=itemCatMapper.selectById(itemCatId).getName();
		return name;
	}
   
    @Cache_Find(key="ITME_CAT",keyType=KEY_ENUM.AUTO)
	@Override
	public List<EasyUITree> findEasyUITreeById(Long parentId) {
		List<ItemCat> itemList =findItemCatList(parentId);
		List<EasyUITree> treeList=new ArrayList<>();
		for (ItemCat itemCat : itemList) {
			EasyUITree tree =new EasyUITree();
			tree.setId(itemCat.getId());
			tree.setText(itemCat.getName());
			String state=itemCat.getIsParent()?"closed":"open";
			tree.setState(state);
			treeList.add(tree);
		}
		return treeList;
	}

	private List<ItemCat> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemList =itemCatMapper.selectList(queryWrapper);
				return itemList;
	}

//	@Override
//	public List<EasyUITree> findEasyUITreeByCache(Long parentId) {
//		String key="ITEM_CAT_"+parentId;
//		List<EasyUITree> treeList=new ArrayList<>();
//		String result=shardedJedis.get(key);
//		if(StringUtils.isEmpty(result)) {
//			treeList=findEasyUITreeById(parentId);
//			String json = ObjectMapperUtil.toJSON(treeList);
//			shardedJedis.setex(key, 7*24*60*60, json);
//			System.out.println("查询数据库!");
//		}else {
//			treeList=ObjectMapperUtil.toObject(result, treeList.getClass());
//			System.out.println("查询Redis缓存");
//		}
//		return treeList;
//	}


	
	
}
