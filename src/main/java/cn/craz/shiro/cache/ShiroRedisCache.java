package cn.craz.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import redis.clients.jedis.Jedis;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

import cn.craz.shiro.cache.JedisUtils;

@Slf4j
public class ShiroRedisCache<K, V> implements Cache<K, V> {
	// shiro cache  key = value
	// redis key = value



	public Object get(Object key) throws CacheException {

		byte[] bs = SerializationUtils.serialize((Serializable) key);

		byte[] value = JedisUtils.getJedis().get(bs);

		if (value == null) {
			return null;
		}
		return SerializationUtils.deserialize(value);
	}

	/**
	 * 将shiro的缓存保存到redis中
	 */
	public Object put(Object key, Object value) throws CacheException {

		Jedis jedis = JedisUtils.getJedis();

		//序列化   和  反序列化
		String oo = jedis.set(SerializationUtils.serialize((Serializable) key), SerializationUtils.serialize((Serializable) value));
		log.info("保存到redis中:key=" + key + " value=" + value);
		log.info(oo);
		byte[] bs = jedis.get(SerializationUtils.serialize((Serializable) key));

		Object object = SerializationUtils.deserialize(bs);
		System.out.println(object);
		return object;
	}


	public Object remove(Object key) throws CacheException {

		Jedis jedis = JedisUtils.getJedis();

		byte[] bs = jedis.get(SerializationUtils.serialize((Serializable) key));

		jedis.del(SerializationUtils.serialize((Serializable) key));

		return SerializationUtils.deserialize(bs);
	}

	public static void main(String[] args) {
		String key = "admin";
		Jedis jedis = JedisUtils.getJedis();

		byte[] bs = jedis.get(SerializationUtils.serialize((Serializable) key));
		System.out.println(bs);
//		Cache cache = new ShiroRedisCache();
//		Object o = cache.remove("lll");
//		System.out.println(o.toString());
	}
	/**
	 * 清空所有缓存
	 */
	public void clear() throws CacheException {
		JedisUtils.getJedis().flushDB();
	}

	/**
	 * 缓存的个数
	 */
	public int size() {

		Long size = JedisUtils.getJedis().dbSize();

		return size.intValue();
	}

	/**
	 * 获取所有的key
	 */
	public Set keys() {

		Set<byte[]> keys = JedisUtils.getJedis().keys(new String("*").getBytes());

		Set<Object> set = new HashSet<Object>();

		for (byte[] bs : keys) {
			set.add(SerializationUtils.deserialize(bs));
		}
		return set;
	}

	/**
	 * 获取所有的value
	 */
	public Collection values() {

		Set keys = this.keys();

		List<Object> values = new ArrayList<Object>();

		for (Object object : keys) {
			byte[] bs = JedisUtils.getJedis().get(SerializationUtils.serialize((Serializable) object));
			values.add(SerializationUtils.deserialize(bs));
		}
		return values;
	}
}