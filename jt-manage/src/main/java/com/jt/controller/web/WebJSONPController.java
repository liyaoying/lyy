package com.jt.controller.web;

import org.springframework.web.bind.annotation.RestController;

import com.jt.util.ObjectMapperUtil;


@RestController
public class WebJSONPController {
	//@RequestMapping("/web/testJSONP")
	public String testJSONP(String callback) {
		User user=new User();
		user.setId(20);
		user.setName("name");
		String json=ObjectMapperUtil.toJSON(user);
		return callback+"("+json+")";
	}
	class User{
		private String name;
		private Integer id;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		@Override
		public String toString() {
			return "User [name=" + name + ", id=" + id + "]";
		}
		
		
	}
	
}