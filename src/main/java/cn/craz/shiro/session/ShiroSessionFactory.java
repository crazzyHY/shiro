package cn.craz.shiro.session;

import cn.craz.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

public class ShiroSessionFactory implements SessionFactory {
	@Override
	public Session createSession(SessionContext sessionContext) {
		if (sessionContext != null) {
			String host = sessionContext.getHost();
			if (host != null) {
				return new ShiroSession(host);
			}
		}
		return new ShiroSession();
	}
}
