package cn.craz.shiro.filter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.provider.certpath.PKIXTimestampParameters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


@Setter
public class RcCaptchaValidateFilter extends AccessControlFilter {
	//是否开启验证码支持
	private boolean captchaEnabled = true;
	//前端验证码参数名
	private String captchaParam = "captchaCode";
	//验证码错误后存储到的属性名
	private String failureKeyAttribute = "shiroLoginFailure";



	@Override
	//验证码验证逻辑 是否通过
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
 		request.setAttribute("captchaEbabled", captchaEnabled);

		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

		//2 判断验证码是否禁用或不是表单提交

		if (!captchaEnabled||! "post".equalsIgnoreCase(httpServletRequest.getMethod()) ) {//
			return true;
		}
		//3 此时是表单提交 验证验证码是否正确 获取页面验证码
		String submitCaptcha = httpServletRequest.getParameter(captchaParam);
		//获取session中的验证码

		String captcha = (String) httpServletRequest.getSession().getAttribute("rcCaptcha");
		//如果提交的验证码等于session中的 验证码

		if (submitCaptcha.equals(captcha)) {
			return true;
		}
  		return false;
	}

	@Override
	//验证码失败逻辑
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		servletRequest.setAttribute(failureKeyAttribute, "验证码错误！");
		return true;
	}
}
