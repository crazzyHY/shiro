package cn.craz.shiro.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {


	private static JedisPool jedisPool;

	static {
		JedisPoolConfig jedisConfig  = new JedisPoolConfig();
		jedisConfig.setMaxTotal(100);
		jedisConfig.setMaxIdle(10);
		jedisConfig.setMaxWaitMillis(100);
		//主机名称和端口号，开启redis的服务器和端口号
		jedisPool = new JedisPool(jedisConfig, "127.0.0.1", 6379);
	}
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	public static void close(Jedis jedis) {
		jedis.close();
	}
}
