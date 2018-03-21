package cn.craz.shiro.controller;

import cn.craz.shiro.entity.SysUser;
import cn.craz.shiro.service.UserService;
import cn.craz.shiro.utils.PasswordHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;

import static cn.craz.shiro.utils.PasswordHelper.encryptPassword;

@Slf4j
@Controller
public class test {

	@Autowired
	UserService userService;


	@RequestMapping("/loginSuccess")
	public String loginSuccess() {
		return "index";
	}

	@RequestMapping("/index")
	public String index() {

		return "index";
	}

	@RequestMapping("/login")
	public String doLogin(HttpServletRequest request, Model model) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return "index";
		}
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (exceptionClassName != null) {
			error = "其他错误：" + exceptionClassName;
		}
		model.addAttribute("error", error);

		return "login";
	}

	@RequestMapping("/signUp")
	public String signUp(SysUser user) {

		PasswordHelper.encryptPassword(user);
		System.out.println(user);
		userService.signUp(user);
		return "redirect:/login";
	}


}
