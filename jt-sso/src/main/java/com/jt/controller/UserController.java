package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 校验用户是否存在
	 * 跨域请求,返回值需要特殊处理
	 *1234
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		try {
			boolean flag = userService.checkUser(param,type);
			return new JSONPObject(callback, SysResult.ok(flag));
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONPObject(callback, SysResult.fail());
		}
	}
	@RequestMapping("/doRegister")
	public SysResult doRegister(String user){
		try {
			User user1 = ObjectMapperUtil.toObject(user, User.class);
			userService.doRegister(user1);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("/query/{ticket}")
	public JSONPObject query(@PathVariable String ticket,String callback) {
		String userJSON = jedisCluster.get(ticket);
		if(StringUtils.isEmpty(userJSON)) {
			return new JSONPObject(callback, SysResult.fail());
		}
		return new JSONPObject(callback,SysResult.ok(userJSON));
	}

	public static void main(String[] args) {
		System.out.println("Dev");
        System.out.println("master");
	}
}
