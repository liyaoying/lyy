package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/** 
 * redis配置类
 * */
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		String[] rediStrings=redisNodes.split(",");
		Set<HostAndPort> nodes=new HashSet<>();
		for (String string : rediStrings) {
			String[] s=string.split(":");
			int port=Integer.parseInt(s[1]);
			nodes.add(new HostAndPort(s[0], port));
		}
		return new JedisCluster(nodes);
	}
	
	
	
	
	
	
//	@Value("${jedis.sentinel.masterName}")
//	private String masterName;
//	@Value("${jedis.sentinels}")
//	private String jedisSentinelNodes;
//	//哨兵连接1000个
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels=new HashSet<>();
//		sentinels.add(jedisSentinelNodes);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
	
	
	
	
	
	
//	@Value("${jedis.host}")
//	private String host;
//	@Value("${jedis.port}")
//	private Integer port;
//	@Value("${jedis.notes}")
//	private String notes;
//	@Bean
//	public ShardedJedis getShards() {
//		String[] s=notes.split(",");
//		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
//		for (String ns : s) {
//			String[] ss = ns.split(":");
//			int port=Integer.parseInt(ss[1]);
//		    JedisShardInfo info=new JedisShardInfo(ss[0], port);
//			shards.add(info);
//		}
//		return new ShardedJedis(shards);
//	}
}
