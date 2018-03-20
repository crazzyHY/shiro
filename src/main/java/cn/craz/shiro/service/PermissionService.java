package cn.craz.shiro.service;

import org.apache.shiro.authz.Permission;

public interface PermissionService {
	Permission createPermission(Permission permission);

	void deletePermission(Long permissionId);
}
