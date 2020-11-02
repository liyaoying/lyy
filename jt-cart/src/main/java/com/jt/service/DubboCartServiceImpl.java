package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout=3000)
public class DubboCartServiceImpl implements DubboCartService{
	@Autowired
	private CartMapper cartMapper;

	/**
	 * testDev
	 * @param userId
	 * @return
	 */
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}
	@Transactional
	@Override
	public void updateCartNum(Cart cart) {
		Cart tempCart = new Cart();
		tempCart.setNum(cart.getNum());
		tempCart.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
		             .eq("item_id", cart.getItemId());
		cartMapper.update(tempCart, updateWrapper);
		
		
	}
	//将对象中不为null的属性当做where条件
	//前提保证cart中只能有两个属性不为空
	@Override
	@Transactional
	public void deletCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
		
	}
	/**
	 * 新增业务实现,第一次新增可以直接入库
	 * 如果不是第一次入库,应该只做数量的修改
	 */
	@Override
	@Transactional
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("item_id", cart.getItemId())
		            .eq("user_id", cart.getUserId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
		cart.setCreated(new Date());
		cart.setUpdated(cart.getCreated());
		cartMapper.insert(cart);
		}else {
			Integer num = cart.getNum()+cartDB.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateById(cartDB);
		}
	}
}
