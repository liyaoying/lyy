package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Reference(timeout = 3000,check = false)
	private DubboCartService dubboCartService;
	//跳转到购物车页面并展示购物列表信息
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		Long userId =(long) request.getAttribute("userId");
		List<Cart> cartList = 
				dubboCartService.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	//更改购物车列表信息数量信息
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		try {
			User user = UserThreadLocal.get();
			cart.setUserId(user.getId());
			dubboCartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	//删除购物车列表信息
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		dubboCartService.deletCart(cart);
		return "redirect:/cart/show.html";
		}
	//购物车新增列表
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		dubboCartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
}

