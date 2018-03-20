package cn.craz.shiro.dao;

import cn.craz.shiro.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class SysUserMapperTest {


	@Autowired
	private SysUserMapper sysUserMapper;
	@Test
	public void selectByPrimaryKey() {
		SysUser user = sysUserMapper.selectByPrimaryKey(1L);
		System.out.println(user);
	}

	@Test
	public void findByUsername() {
		SysUser user = sysUserMapper.findByUsername("admin");
		System.out.println(user);
	}
}