package cn.craz.shiro.service.impl;

import cn.craz.shiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-config.xml")
//@WebAppConfiguration
public class UserServiceImplTest {

	@Autowired
	RedisTemplate redisTemplate;
	@Test
	public void findByUsername() {
		String key = "lll";
		String value = "ooo";
		redisTemplate.opsForValue().set(key, value);
		System.out.println(redisTemplate.opsForValue().get(key));
	}
}