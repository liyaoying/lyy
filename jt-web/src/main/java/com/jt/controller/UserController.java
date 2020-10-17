package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
@RequestMapping("/user")
@Controller
public class UserController {
//	@Autowired
//	private UserService userService;
	//导入dubbo的用户接口
	@Reference(timeout=3000,check=false)
	private DubboUserService userService;
	
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}
	@RequestMapping("/doRegister")
	@ResponseBody  
	public SysResult doRegister(User user) {
		try {
			userService.doRegister(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {
		try {
			//调用sso系统获取秘钥
			String token=userService.findUserByUP(user);
			//如果数据不为空时,将数据保存到Cookie中
			//cookie中的key固定值JT_TICKET
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET",token);
				cookie.setMaxAge(7*24*3600);//生命周期
				//cookie.setMaxAge(0); 表示立即删除
				//cookie.setMaxAge(d>0); 表示能存活多久 单位/秒
				//cookie.setMaxAge(-1); 表示会话结束后删除
				cookie.setPath("/");//cookie的权限,"/"后面的可以有权限, www.jd.com/b.html,www.jd.com/abc/a.html
				//cookie.setPath("/")
				cookie.setDomain("jt.com");//要求所有的xxx.jt.com,实现数据共享,二级域名,实现单点登录
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	//"http://www.jt.com/user/logout.html
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				String token = cookie.getValue();
				userService.deleteToken(token);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				cookie.setDomain("jt.com");
				response.addCookie(cookie);
				break;
			}
		}
		}
		return "redirect:/";
	}
	
}
