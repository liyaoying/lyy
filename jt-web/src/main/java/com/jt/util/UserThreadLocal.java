package com.jt.util;
//ThreadLocal线程安全

import com.jt.pojo.User;

public class UserThreadLocal {
	/**
	 * ThreadLocal<Map<k,v>,可以存取多个数据
	 */
	private static ThreadLocal<User> thread= new ThreadLocal<>();
	public static void set(User user) {
		thread.set(user);
	}
	public static User get() {
		return thread.get();
	}
	//使用threadLocal切记关闭,防止内存泄漏
	public static void remove() {
		thread.remove();
	}
}
