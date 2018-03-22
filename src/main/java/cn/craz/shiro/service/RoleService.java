package cn.craz.shiro.service;


import cn.craz.shiro.entity.SysRole;

import java.util.Set;

public interface RoleService {
	Set<String> getAllRolesByUserId(Long userId);


}
