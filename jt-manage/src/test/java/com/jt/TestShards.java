package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.ShardedJedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestShards {
	//测试异常报错
	@Autowired
	private ShardedJedis shardedJedis;
	@Test
	public void testconfig() {
		System.out.println("测试结果:"+shardedJedis);
	}
}
