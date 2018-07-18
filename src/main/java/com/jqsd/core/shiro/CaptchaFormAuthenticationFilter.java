package com.jqsd.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jqsd.common.util.SessionUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 授权检查
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);
	
	/**
	 * 验证码-参数名
	 */
	private String captchaParam = "inputRandomCode";

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	/**
	 * 创建token
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = SessionUtil.getIpAddr((HttpServletRequest)request);
		return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
	}
	
	/**
	 * 登录前校验-避免不能多次登录错误
	 */
    @Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			// 先判断是否是登录操作
			if (isLoginSubmission(request, response)) {
				return false;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
    
    /**
	 * 登录失败处理
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		return super.onLoginFailure(token, e, request, response);
	}
	
	/**
	 * 登录成功处理
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		//不是ajax请求
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
				|| request.getParameter("ajax") == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
		} else {
			//ajax请求
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/timeoutsuccess");
		}
		
		return false;
	}

}
