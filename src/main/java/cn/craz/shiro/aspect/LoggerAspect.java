package cn.craz.shiro.aspect;

import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
	@Pointcut("execution(* cn.craz.shiro.controller.*.*(..))")
	public void point(){}

//	@Around("point()")
//	public void around(ProceedingJoinPoint pjp){
//		log.info("进入Controller");
//		try {
//			pjp.proceed();
//		} catch (Throwable throwable) {
//			throwable.printStackTrace();
//		}
//		log.info("退入Controller");
//
//	}
	@Before("point()")
	public void before(JoinPoint jp){

		log.info("进入Controller方法："+jp.getSignature().getName());
	}
//	@Pointcut("@annotation(cn.craz.shiro.aspect.Log)")
//	public void point(){
//	}
//
//
//	@Before("point()")
//	public void beforeLogger(JoinPoint joinPoint){
//
//	}
//
//	private String getName(ProceedingJoinPoint point) {
//		String className = point.getTarget().getClass().getSimpleName();
//		String methodName = point.getSignature().getName();
//		Object[] args = point.getArgs();
//		Class<?> classTarget = point.getTarget().getClass();
//		Class<?>[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
//		try {
//			Method objMethod = classTarget.getMethod(methodName, par);
//
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
}
