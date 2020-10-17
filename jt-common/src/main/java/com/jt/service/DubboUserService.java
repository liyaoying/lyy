package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void doRegister(User user);

	String findUserByUP(User user);

	void deleteToken(String token);

}
