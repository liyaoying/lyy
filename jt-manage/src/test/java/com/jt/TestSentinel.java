package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	
	@Test //测试哨兵
	public void TestSentinel() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.161.130:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = sentinelPool.getResource();
		jedis.set("kk", "12369587");
		System.out.println(jedis.get("kk"));
		jedis.close();
	}

}
