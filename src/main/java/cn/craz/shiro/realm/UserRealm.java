package cn.craz.shiro.realm;

import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
@Slf4j
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SysUserMapper userMapper;


	@Override
	public String getName() {
		return "customRealm";
	}


	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		if (StringUtils.isEmpty(username)) {
			throw new UnknownAccountException();
		}
		SysUser user = userMapper.findByUsername(username);
		if (user == null) {
			throw new UnknownAccountException();
		}
		//需要使用credentialsMatcher来进行密码匹配

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername()
				, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
		log.info(user.toString());
		return simpleAuthenticationInfo;
	}
}
