package cn.craz.shiro.session;

import cn.craz.shiro.cache.JedisUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Date;

@Slf4j
public class CustomShiroSessionDao extends CachingSessionDAO {


	@Setter
	private ShiroSessionDao shiroSessionDao;
	/**
	 * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
	 */
	@Override
	protected void doUpdate(Session session) {
		try {
			if (session instanceof ValidatingSession && ((ValidatingSession) session).isValid()) {
				return;
			}
		} catch (Exception e) {
			log.warn("创建Session失败", e);
		}
		try {
			if (session instanceof ShiroSession) {
				ShiroSession ss = (ShiroSession) session;
				if (!ss.isChanged()) {
					return;
				}
				ss.setChanged(false);
				ss.setLastAccessTime(new Date());
				shiroSessionDao.updateSession(session);
				log.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
			} else {
				log.debug("sessionId {} name {} 更新失败", session.getId(), session.getClass().getName());

			}
		} catch (Exception e) {
			log.warn("更新session失败",e);
		}
	}

	@Override
	protected void doDelete(Session session) {
		log.debug("begin doDelete {} ", session);
		try {
			shiroSessionDao.deleteSession(session.getId());
		this.uncache(session.getId());
			log.debug("shiro session id {} 被删除", session.getId());
		} catch (Exception e) {
			log.warn("删除Session失败",e);
		}
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		try {
			shiroSessionDao.saveSession(session);
			log.info("sessionId {} name {} 被创建", sessionId, session.getClass().getName());
		} catch (Exception e) {
			log.warn("创建Session失败", e);
		}

		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		log.debug("begin doReadSession {} ", sessionId);
		Session session = null;
		try {
			session = shiroSessionDao.getSession(sessionId);
			shiroSessionDao.refreshSession(sessionId);
			log.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
		} catch (Exception e) {
			log.warn("读取Session失败", e);
		}
		return session;
	}
	/**
	 * 删除cache中缓存的Session
	 */
	public void uncache(Serializable sessionId) {
		try {
			Session session = super.getCachedSession(sessionId);
			super.uncache(session);
			log.debug("删除本地 cache中缓存的Session id {} 的缓存失效", sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
