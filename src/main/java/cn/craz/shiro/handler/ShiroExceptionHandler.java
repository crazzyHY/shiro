package cn.craz.shiro.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sun.security.util.AuthResources_it;

@ControllerAdvice

public class ShiroExceptionHandler {
//	@ExceptionHandler(UnauthorizedException.class)
//	public void handlerShiroException() {
//		System.out.println("done");
//	}
}
