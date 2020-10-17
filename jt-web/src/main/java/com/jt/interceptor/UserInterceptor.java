package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
@Component
public class UserInterceptor implements HandlerInterceptor{
	/**
	 * 在spring4版本中要求必须重写3个方法,不管是否需要
	 * 在spring5版本中在接口中添加了default属性,则省略
	 */
	
	/**
	 * handler:处理器
	 */
	@Autowired
	private JedisCluster jedisCluster;
	//请求业务处理之前
	//true代表拦截放行,false代表请求拦截(重定向到登录页面)
	/**
	 * 业务逻辑
	 * 1.获取cookie数据
	 * 2.获取token(ticket)
	 * 3.判断redis缓存服务器中是否有数据
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("JT_TICKET".equals(cookie.getName())) {
					token=cookie.getValue();
					break;
				}
			}
			if(token!=null) {
				String userJSON = jedisCluster.get(token);
				if(userJSON!=null) {
					User user = ObjectMapperUtil.toObject(userJSON, User.class);
			        request.setAttribute("userId", user.getId());
				    UserThreadLocal.set(user);
					return true;
				}
			}
		}
		//重定向到用户登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	//业务处理完后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	//视图解析器视图渲染后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}


//import org.omg.CORBA.PUBLIC_MEMBER;