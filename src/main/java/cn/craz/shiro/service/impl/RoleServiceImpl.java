package cn.craz.shiro.service.impl;

import cn.craz.shiro.dao.SysPermissionMapper;
import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysRole;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.RoleService;
import jdk.internal.cmm.SystemResourcePressureImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysRoleMapper roleMapper;
	@Override
	public Set<String> getAllRolesByUsername(String username) {
		List<String> roles = Arrays.asList(userMapper.getRolesIdByUsername("lll").split(","));
		return new HashSet<>(roleMapper.getRolesByRoleId(roles));

	}
}
