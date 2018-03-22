package cn.craz.shiro.realm;

import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.RoleService;
import cn.craz.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import sun.dc.pr.PRError;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public String getName() {
		return "customRealm";
	}


	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) principalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		SysUser user = (SysUser) session.getAttribute("SysUser");
		if (user == null) {
			user = userService.findByUsername(username);
			session.setAttribute("SysUser", user);
		}
		Long userId = user.getId();
		Set<String> roles = (Set<String>) session.getAttribute("Roles");
		if (roles.isEmpty() ) {
			roles = roleService.getAllRolesByUserId(userId);
			session.setAttribute("Roles", roles);
		}
		authorizationInfo.setRoles(roles);

		Set<String> permissions = (Set<String>) session.getAttribute("Permissions");
		if (permissions.isEmpty()) {
			permissions =
		}
		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		if (StringUtils.isEmpty(username)) {
			throw new UnknownAccountException();
		}
		SysUser user = userService.findByUsername(username);
		if (user == null) {
			throw new UnknownAccountException();
		}
		//需要使用credentialsMatcher来进行密码匹配

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername()
				, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());

		return simpleAuthenticationInfo;
	}
}
