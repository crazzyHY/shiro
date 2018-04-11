package cn.craz.shiro.cache;


import cn.craz.shiro.cache.serializer.ByteSourceUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.connection.jedis.JedisUtils;

import java.io.Serializable;
import java.util.*;

@Slf4j


public class ShiroRedisCache<K, V> implements org.apache.shiro.cache.Cache<K, V> {

	private CacheManager cacheManager;
	private Cache cache;

	private byte[] getByteKey(Object key){
		if(key instanceof String){
			String preKey = (String) key;
			return preKey.getBytes();
		}else{
			return ByteSourceUtils.serialize((Serializable) key);
		}
	}

	public ShiroRedisCache(String name, CacheManager cacheManager) {
		if (name == null || cacheManager == null) {
			throw new CacheException("cacheManager or CacheName cannot be null");
		}
		this.cacheManager = cacheManager;
		this.cache = cacheManager.getCache(name);
	}

	public Object get(Object key) throws CacheException {


//		byte[] bs = SerializationUtils.serialize((Serializable) key);
//
//		Cache.ValueWrapper valueWrapper = cache.get(bs);
//
//		if (valueWrapper == null) {
//			return null;
//		}
//		return SerializationUtils.deserialize((byte[]) valueWrapper.get());



		String ketString = JSON.toJSONString(key);
		log.info("从缓存中获取key为{}的信息", ketString);
		if (ketString == null) {
			return null;
		}

		Cache.ValueWrapper valueWrapper = cache.get(ketString);
		if (valueWrapper == null) {
			return null;
		}
		log.info("信息为：" + valueWrapper.get());
		if (key instanceof SimplePrincipalCollection) {
			return JSON.parseObject((String) valueWrapper.get(),SimpleAuthorizationInfo.class);
		}
		return  valueWrapper.get();


	}

	/**
	 * 将shiro的缓存保存到redis中
	 */
	public Object put(Object key, Object value) throws CacheException {



//		 cache.put(SerializationUtils.serialize((Serializable) key), SerializationUtils.serialize((Serializable) value));
//		log.info("保存到redis中:key=" + key + " value=" + value);
//
//		Cache.ValueWrapper wrapper = cache.get(SerializationUtils.serialize((Serializable) key));
//
//		Object object = SerializationUtils.deserialize((byte[]) wrapper.get());
//		System.out.println(object);
//		return object;



//		String stringKey = key.toString();
//		log.info("创建新的缓存，信息为：{}={}", key, value);
//		cache.put(stringKey,keyserializer);
//		Cache.ValueWrapper valueWrapper = cache.get(stringKey);
//		Object object = ByteSourceUtils.deserialize((byte[]) valueWrapper.get());
//		return (V) object;

		String keyString = JSON.toJSONString(key);
		String valueJson = JSON.toJSONString(value);

		log.info("创建新的缓存，信息为：{}={}", keyString, valueJson);
		cache.put(keyString, valueJson);
		return value;

//		log.info("存入key:" + keyString + " value:" + valueJson);
//		cache.put(keyString,valueJson);
//		return get(keyString);

	}


	public Object remove(Object key) throws CacheException {
//		log.info("删除缓存key为{}的内容", JSON.toJSONString(key));
//		Object o = (String) get(key);
//		cache.evict(JSON.toJSONString(key));
//		return (V) o;
		return null;
	}


	/**
	 * 清空所有缓存
	 */
	public void clear() throws CacheException {
			cache.clear();
	}

	/**
	 * 缓存的个数
	 */
	public int size() {

		return 1;
	}

	/**
	 * 获取所有的key
	 */
	public Set keys() {

		return null;
	}

	/**
	 * 获取所有的value
	 */
	public Collection values() {

		return null;
	}

}