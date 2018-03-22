package cn.craz.shiro.service.impl;

import cn.craz.shiro.dao.SysPermissionMapper;
import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.entity.SysRole;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.RoleService;
import jdk.internal.cmm.SystemResourcePressureImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private SysPermissionMapper permissionMapper;

	@Override
	public Set<String> getAllRolesByUserId(Long userId) {
		List<SysRole> roles = roleMapper.selectByPrimaryKey(userId);
		Set<String> roleCodeSet = new HashSet<>();

		for (SysRole role : roles) {
			Long roleId = Long.valueOf(role.getId());
			SysRole sysRole = roleMapper.getById(roleId);
			if (null == sysRole) {
				continue;
			}
			roleCodeSet.add(sysRole.getId());
		}
		return roleCodeSet;
	}
}
