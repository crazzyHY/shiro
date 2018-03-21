package cn.craz.shiro.service;

import cn.craz.shiro.entity.SysUser;

public interface UserService {
	SysUser findByUsername(String username);


	void signUp(SysUser user);

}
