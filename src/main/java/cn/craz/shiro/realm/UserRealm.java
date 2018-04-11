package cn.craz.shiro.realm;

import cn.craz.shiro.cache.serializer.MySimpleByteSource;
import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.PermissionService;
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
	@Autowired
	private PermissionService permissionService;

	@Override
	public String getName() {
		return "customRealm";
	}


	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//获取当前用户username

		String username = (String) principalCollection.getPrimaryPrincipal();
		//授权信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//通过subject获取session
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		//从session中获取信息
		SysUser user = (SysUser) session.getAttribute("SysUser");
		if (user == null) {
			user = userService.findByUsername(username);
			session.setAttribute("SysUser", user);
		}
		Set<String> roles = (Set<String>) session.getAttribute("Roles");
		if (roles==null||roles.isEmpty()) {
			roles = roleService.getAllRolesByUsername(username);
			session.setAttribute("Roles", roles);
		}
		authorizationInfo.setRoles(roles);

		Set<String> permissions = (Set<String>) session.getAttribute("Permissions");
  		if (permissions==null||permissions.isEmpty()) {
			permissions = permissionService.getPermissionsByUsername(username);
			session.setAttribute("Permissions", permissions);
		}
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
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
				, user.getPassword(), new MySimpleByteSource(user.getCredentialsSalt().getBytes()), getName());

		return simpleAuthenticationInfo;
	}
}
