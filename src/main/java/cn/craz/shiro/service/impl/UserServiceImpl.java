package cn.craz.shiro.service.impl;

import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private SysUserMapper userMapper;
	public SysUser findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public void signUp(SysUser user) {
		userMapper.insert(user);
	}
}
