package com.jqsd.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.model.Admin;
import com.jqsd.common.util.MD5Util;
import com.jqsd.common.vo.AccountSessionVo;

import java.util.Date;

/**
 * 授权业务处理
 */
public class ShiroDbRealm extends AuthorizingRealm {

	/**
	 * 是否启用超级管理员
	 */
	protected boolean activeRoot = false;

	/**
	 * 是否使用验证码
	 */
	protected boolean useCaptcha = false;

	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//登录用户
		//String loginName = (String) principals.fromRealm(getName()).iterator().next();
		//获取权限
		
		return null;
	}

	/**
	 * 获取认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		//验证码校验
		/*if (useCaptcha) {
			if (CaptchaRender.validate(indexController, StrUtil.toUpperCase(token.getCaptcha()), CommonConstant.RANDOM_CODE_KEY_CAPTCHA)) {
				throw new IncorrectCaptchaException("验证码错误！");
			}
		}*/
		
		//登录校验
		Admin admin = Admin.dao.getAdminByUserName(token.getUsername());
		if (token.getUsername() != null) {
			if (CommonConstant.STATUS_N.equals(admin.getStatus())) {
				throw new DisabledAccountException();
			}
            if (!MD5Util.getMD5Pwd(String.valueOf(token.getPassword())).equals(admin.getPassword())) {
				throw new IncorrectCredentialsException();
			}

			Admin update = new Admin();
			update.setLastLoginTime(admin.getLoginTime())
					.setLoginTime(new Date())
					.setLastLoginIp(admin.getLoginIp())
					.setLoginIp(token.getHost())
					.setId(admin.getId())
					.update();


			AccountSessionVo sessionVo = new AccountSessionVo();
			sessionVo.setId(admin.getId());
			sessionVo.setUserName(admin.getUserName());
			sessionVo.setRealName(admin.getRealName());
			
			return new SimpleAccount(sessionVo, token.getPassword(), getName());
		} else {
			throw new UnknownAccountException();
		}
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}

	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public boolean isActiveRoot() {
		return activeRoot;
	}

	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}
	
}
