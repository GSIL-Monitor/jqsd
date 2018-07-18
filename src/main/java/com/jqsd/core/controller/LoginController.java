package com.jqsd.core.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.jqsd.common.util.SessionUtil;
import com.jqsd.common.vo.AccountSessionVo;
import com.jqsd.core.controller.base.BaseController;
import com.jqsd.core.shiro.IncorrectCaptchaException;

/**
 * 登录Action
 */

public class LoginController extends BaseController {

	public void index(){
		String msg = parseException();
		if (!"".equals(msg)) {
			setAttr("msg", msg);
			setAttr("username", getAttr(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM));
		}
		render("login.html");
	}
	
	public void timeoutsuccess(){
		Subject subject = SecurityUtils.getSubject();
		AccountSessionVo accountVo = (AccountSessionVo)subject.getPrincipal();
		SessionUtil.setAccountSessionVo(getSession(),accountVo);
		redirect("/");
	}

	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		redirect("/login");
		return;
	}

	private String parseException() {
		String errorString = getAttr(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		Class<?> error = null;
		try {
			if (errorString != null) {
				error = Class.forName(errorString);
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} 
		
		String msg = "";
		if (error != null) {
			if (error.equals(UnknownAccountException.class))
				msg = "账号不存在！";
			else if (error.equals(IncorrectCredentialsException.class))
				msg = "密码错误！";
			else if (error.equals(IncorrectCaptchaException.class))
				msg = "验证码错误！";
			else if (error.equals(AuthenticationException.class))
				msg = "认证失败！";
			else if (error.equals(DisabledAccountException.class))
				msg = "账号被冻结！";
			else
				msg = "其他错误！";
			return "登录失败，" + msg;
		} else {
			return "";
		}
	}
}
