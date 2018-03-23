package cn.craz.shiro.service.impl;

import cn.craz.shiro.dao.SysRoleMapper;
import cn.craz.shiro.dao.SysUserMapper;
import cn.craz.shiro.entity.SysRole;
import cn.craz.shiro.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-config.xml")
public class RoleServiceImplTest {
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private SysRoleMapper roleMapper;
	@Test
	public void getAllRolesByUsername() {
		List<String> roles = Arrays.asList(userMapper.getRolesIdByUsername("lll").split(","));
		List<String> roleNames=roleMapper.getRolesByRoleId(roles);
		System.out.println();
	}
}