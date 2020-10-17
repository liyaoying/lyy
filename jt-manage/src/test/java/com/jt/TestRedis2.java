package com.jt;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	//编辑list集合
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.161.130", 6379);
		jedis.lpush("list", "1","a");
		System.out.println(jedis.rpop("list"));
		System.out.println(jedis.rpop("list"));
	}
	//事务控制
	@Test
	public void testTX() {
		Jedis jedis = new Jedis("192.168.161.130",6379);
		Transaction transaction = jedis.multi();
		try {
			transaction.set("dd","bbb");
			transaction.set("b", null);
			transaction.exec();
		} catch (Exception e) {
			transaction.discard();
		}
	}
}
