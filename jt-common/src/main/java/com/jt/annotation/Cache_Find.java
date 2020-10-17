package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enu.KEY_ENUM;

/**
 * 查询注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD,ElementType.FIELD}) 使用数组表示修饰多种
@Target(ElementType.METHOD)
public @interface Cache_Find {
	String key() default "";//接受用户的key值
	KEY_ENUM keyType() default KEY_ENUM.AUTO;//定义key的类型,默认为自动拼接类型
	int secondes() default 0;//	永不失效,缓存数据失效时间
}
