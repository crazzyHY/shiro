package cn.craz.shiro.credentials;

import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.UserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	@Autowired
	private UserService userService;

	private Cache<String, AtomicInteger> passwordRetryCache;


	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}


	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		Object o =passwordRetryCache.get(username);
		AtomicInteger retryCount = (AtomicInteger) JSONObject.parseObject((String) o).get("retryCount");

		if (null == retryCount) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		//超过5次就报错


		if (retryCount.incrementAndGet() > 5) {
			throw new ExcessiveAttemptsException();
		}

		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			passwordRetryCache.remove(username);
			SysUser user = userService.findByUsername(username);

			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			//匹配后存入session用于后面权限获取
			session.setAttribute("SysUser", user);
		}

		return matches;

	}

}
