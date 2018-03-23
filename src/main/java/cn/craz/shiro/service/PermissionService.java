package cn.craz.shiro.service;

import com.sun.org.apache.commons.digester.SetRootRule;
import org.apache.shiro.authz.Permission;

import java.util.Set;

public interface PermissionService {

	Set<String> getPermissionsByUsername(String username);



}
