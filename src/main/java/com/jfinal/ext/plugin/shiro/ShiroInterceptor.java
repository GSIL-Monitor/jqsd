/**
 * Copyright (c) 2011-2017, dafei 李飞 (myaniu AT gmail DOT com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jfinal.ext.plugin.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class ShiroInterceptor implements Interceptor {
	/**
	 * 登录成功时所用的页面。
	 */
	private final String loginUrl;

	/**
	 * 权限验证失败时所用的页面。
	 */
	private final String unauthorizedUrl;
	
	/**
	 * 保存为登陆前的访问页面
	 */
	private final String savedRequestKey;
	
	public ShiroInterceptor(){
		this.loginUrl = ShiroKit.getLoginUrl();
		this.unauthorizedUrl = ShiroKit.getUnauthorizedUrl();
		this.savedRequestKey = ShiroKit.getSavedRequestKey();
	}

	public void intercept(Invocation ai) {
		AuthzHandler ah = ShiroKit.getAuthzHandler(ai.getActionKey());
		// 存在访问控制处理器。
		if (ah != null) {
			Controller  c = ai.getController();
			try {
				// 执行权限检查。
				ah.assertAuthorized();
			}catch (UnauthenticatedException lae) {
				// RequiresGuest，RequiresAuthentication，RequiresUser，未满足时，抛出未经授权的异常。
				// 如果没有进行身份验证，返回HTTP401状态码,或者跳转到默认登录页面
				if(StrKit.notBlank(this.loginUrl)){
					//保存登录前的页面信息,只保存GET请求。其他请求不处理。
					if(c.getRequest().getMethod().equalsIgnoreCase("GET")){
						if(c.getSessionAttr(this.savedRequestKey) == null ){
							HttpServletRequest req = c.getRequest();
							String saveUrl =req.getRequestURI().substring(req.getContextPath().length());
							String saveQs = req.getQueryString();
							if(StrKit.notBlank(saveQs)){
								saveUrl = saveUrl +"?"+ saveQs;
							}
							if(StrKit.notBlank(saveUrl)){
								c.setSessionAttr(this.savedRequestKey, saveUrl);
							}
						}
					}
					c.redirect(this.loginUrl);
				}else{
					ai.getController().renderError(401);
				}
				return;
			} catch (AuthorizationException ae) {
				// RequiresRoles，RequiresPermissions授权异常
				// 如果没有权限访问对应的资源，返回HTTP状态码403，或者调转到为授权页面
				if(StrKit.notBlank(this.unauthorizedUrl)){
					c.redirect(this.unauthorizedUrl);
				}else{
					c.renderError(403);
				}
				return;
			}
		}
		// 执行正常逻辑
		ai.invoke();
	}
}
