package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	@Test
	public void testredis() {
		Jedis jedis = new Jedis("192.168.161.130", 6379);
		jedis.set("1902", "1902班");
		jedis.expire("1902", 10);
		System.out.println(jedis.get("1902"));
		
	}
	@Test
	public void testTimeout() throws Exception {
		Jedis jedis = new Jedis("192.168.161.130", 6379);
		jedis.setex("aa", 1, "aa");
		System.out.println(jedis.get("aa"));
		Thread.sleep(2000);
		jedis.setnx("aa", "bb");//存在不赋值,不存在会进行操作
		System.out.println(jedis.get("aa"));
	}
	@Test
	public void ObjectToJson() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2000L);
		itemDesc.setItemDesc("测试");
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		ObjectMapper mapper=new ObjectMapper();
		String json = mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		//转化成对象
		ItemDesc desc = mapper.readValue(json, ItemDesc.class);
		System.out.println("转换结果:"+desc);
	}
	@Test
	public void ListToJson() throws IOException {
		List<ItemDesc> list=new ArrayList<ItemDesc>();
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2000L);
		itemDesc.setItemDesc("测试");
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		list.add(itemDesc);
		itemDesc.setItemId(2500L);
		itemDesc.setItemDesc("测试2");
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		list.add(itemDesc);
		ObjectMapper mapper=new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println("json:"+json);
		
		Jedis jedis = new Jedis("192.168.161.130", 6379);
		jedis.set("json", json);
		String ss=jedis.get("json");
		//转化成对象
		
		List<ItemDesc> jsList=mapper.readValue(ss, list.getClass());
		System.out.println("转换结果:"+jsList);
	}
	
	
	@Test
	public void ListUtil(){
		List<ItemDesc> list=new ArrayList<ItemDesc>();
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(2000L);
		itemDesc.setItemDesc("测试");
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		list.add(itemDesc);
		itemDesc.setItemId(2500L);
		itemDesc.setItemDesc("测试2");
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		list.add(itemDesc);
		String json=ObjectMapperUtil.toJSON(list);
		System.out.println("json:"+json);
		Jedis jedis = new Jedis("192.168.161.130", 6379);
		jedis.set("json", json);
		String ss=jedis.get("json");
		//转化成对象
		List<ItemDesc> jsList=ObjectMapperUtil.toObject(ss,list.getClass());
		System.out.println("转换结果:"+jsList);
	}
	
	/**
	 *1.首先获取对象的get方法,将get去掉,之后首字母小写获取属性的名称
	 *2.之后将属性名称:属性的值进行拼接
	 *3.形成json串 
	 */
	
	
	/**
	 * 1.获取json串,通过json串获取json中key
	 * 2,通过反射获取实例空对象
	 * 3,通过setkey方法为属性赋值
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	
	
	
	
	@Test
	public void tojson() throws IOException, ClassNotFoundException {
		ObjectMapper mapper=new ObjectMapper();
		User user =new User();
		user.setAge(18);
		user.setGender("男");
		user.setId(100);
		user.setName("张三");
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		System.out.println(user.getClass());
		User user1 = mapper.readValue(json, User.class);
		System.out.println(user1);
	}
	//非静态内部类使用反射创建对象也需要使用外部类的实例对象
@JsonIgnoreProperties(ignoreUnknown=true)//遇到未知属性忽略,getset方法不匹配时忽略
 static class User{
	private Integer id;
	private String name;
	private Integer age;
	private String gender;
	public  User() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}
	
}
}
