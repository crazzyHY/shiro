package cn.craz.shiro.cache;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import org.apache.shiro.util.Destroyable;

@Getter
@Setter
public class ShiroSpringCacheManager implements CacheManager,Destroyable {

	private org.springframework.cache.CacheManager cacheManager;



	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if (name == null) {
			return null;
		}
		return new ShiroRedisCache<K, V>(name,getCacheManager());
	}

	@Override
	public void destroy() throws Exception {
		cacheManager = null;
	}
}
