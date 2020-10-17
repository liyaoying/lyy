package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.pojo.User;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class UserServiceImpl implements UserService{
	@Autowired 
    private HttpClientService httpClientService;
	@Override
	public void doRegister(User user) {
		String url = "http://sso.jt.com/user/doRegister";
		//对密码进行加密
		String  md5password = 
		DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5password);
		String json = ObjectMapperUtil.toJSON(user);
		Map<String,String> param= new HashMap<String, String>();
		param.put("user", json);
		httpClientService.doPost(url,param);
		
	}

}
