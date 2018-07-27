package cn.craz.shiro.session.impl;

import cn.craz.shiro.cache.JedisUtils;
import cn.craz.shiro.session.ShiroSessionDao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ShiroSessionDaoImpl implements ShiroSessionDao {
	private static final String REDIS_SHIRO_SESSION = "shiro-session:";
	private static final int SESSION_VAL_TIME_SPAN = 1800;

	// 保存到Redis中key的前缀 prefix+sessionId
	@Setter
	private String redisShiroSessionPrefix = REDIS_SHIRO_SESSION;

	// 设置会话的过期时间
	@Setter
	private int redisShiroSessionTimeout = SESSION_VAL_TIME_SPAN;


	@Setter
	private RedisTemplate<String, Session> redisTemplate;

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			throw new NullPointerException("session is empty");
		}
		try {
			redisTemplate.opsForValue().set(buildRedisSessionKey(session.getId()), session,
					redisShiroSessionTimeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.getMessage();
			log.error("save session error");
		}

	}


	@Override
	public void updateSession(Session session) {
		if (session == null || session.getId() == null) {
			throw new NullPointerException("session is empty");
		}
		try {
			redisTemplate.boundValueOps(buildRedisSessionKey(session.getId())).set(session, redisShiroSessionTimeout
					, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.getMessage();
			log.error("update session error");
		}

	}

	@Override
	public void refreshSession(Serializable sessionId) {
		redisTemplate.expire(buildRedisSessionKey(sessionId), redisShiroSessionTimeout, TimeUnit.SECONDS);
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		if (sessionId == null) {
			throw new NullPointerException("session id is empty");
		}
		try {
			redisTemplate.delete(buildRedisSessionKey(sessionId));
		} catch (Exception e) {
			e.getMessage();
			log.error("delete session error");
		}

	}

	@Override
	public Session getSession(Serializable sessionId) {
		if (sessionId == null) {
			throw new NullPointerException("session id is empty");
		}
		Session session = null;
		try {
			session = redisTemplate.boundValueOps(buildRedisSessionKey(sessionId)).get();
		} catch (Exception e) {
			e.getMessage();
			log.error("get session error");
		}
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		return null;
	}

	private String buildRedisSessionKey(Serializable sessionId) {
		return redisShiroSessionPrefix + sessionId;
	}
}
