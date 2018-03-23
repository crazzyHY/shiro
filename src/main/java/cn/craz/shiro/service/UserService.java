package cn.craz.shiro.service;

import cn.craz.shiro.entity.SysUser;

import java.util.Set;

public interface UserService {
	SysUser findByUsername(String username);


	void signUp(SysUser user);


}
