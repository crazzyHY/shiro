package cn.craz.shiro.dao;

import cn.craz.shiro.entity.SysRole;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = "classpath:spring-config.xml")
public class SysRoleMapperTest {

	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private SysPermissionMapper permissionMapper;
	@Test
	public void getPermissionsIdByRoleIdList() {
		List<String> rolesId = Arrays.asList("1","2");
		String permissionIds = roleMapper.getPermissionsIdByRoleIdList(rolesId).toString();
		Set<Long> pmsId=Arrays.asList(permissionIds.substring(1,permissionIds.length()-1).split(",")).stream().map(value->Long.parseLong(value.trim())).collect(Collectors.toSet());
		Set<String> permissions = new HashSet<>(permissionMapper.getPermissionsById(pmsId));

		System.out.println();

	}
}