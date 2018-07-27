package cn.craz.shiro.service.impl;

import cn.craz.shiro.dao.SysPermissionMapper;
import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServerImpl implements PermissionService {
	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysPermissionMapper permissionMapper;
	@Override
	public Set<String> getPermissionsByUsername(String username) {
		List<String> roles = Arrays.asList(userMapper.getRolesIdByUsername(username).split(","));
		String permissionIds = roleMapper.getPermissionsIdByRoleIdList(roles).toString();
		Set<Long> pmsId=Arrays.asList(permissionIds.substring(1,permissionIds.length()-1).split(",")).stream().map(value->Long.parseLong(value.trim())).collect(Collectors.toSet());
		Set<String> permissions = new HashSet<>(permissionMapper.getPermissionsById(pmsId));
		return permissions;
		}
	}
