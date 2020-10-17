package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Reference(timeout = 3000,check = false)
	private DubboOrderService dubboOrderService;
	@Reference(timeout=3000,check=false)
	private DubboCartService dubboCartService;
	
	
	@RequestMapping("/create")
	public String orderCreate(Model model) {
		Long userId = UserThreadLocal.get().getId();
		List<Cart> carts = dubboCartService.findCartListByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult insertOrder(Order order) {
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			String orderId=dubboOrderService.insertOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				return SysResult.ok(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		Order order = dubboOrderService.findOrderById(id);
		model.addAttribute("order",order);
		return "success";
	}
}
