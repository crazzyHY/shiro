package cn.craz.shiro.service.impl;

import cn.craz.shiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-config.xml")
@WebAppConfiguration
public class UserServiceImplTest {

	@Autowired
	UserService userService;
	@Test
	public void findByUsername() {
		System.out.println(userService.findByUsername("admin"));
	}
}