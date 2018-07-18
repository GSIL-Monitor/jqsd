package com.jqsd.common.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.vo.AccountSessionVo;

/**
 * Session工具
 */
public class SessionUtil
{
	/**
	 * 返回帐号id
	 * @param session session
	 * @return 帐号id
	 */
	public static String getAccountId(HttpSession session){
		AccountSessionVo accountVo = (AccountSessionVo)session.getAttribute(CommonConstant.LOGIN_ACCOUNT);
		if (accountVo != null){
			return accountVo.getId();
		}
		
		return null;
	}
	
	/**
	 * 返回帐号sessionVo
	 * @param session session
	 * @return AccountSessionVo
	 */
	public static AccountSessionVo getAccountSessionVo(HttpSession session){
		AccountSessionVo accountVo = (AccountSessionVo)session.getAttribute(CommonConstant.LOGIN_ACCOUNT);
		if (accountVo != null){
			return accountVo;
		}
		
		return null;
	}
	


	/**
	 * 设置帐号sessionVo
	 * @param session session
	 * @return AccountSessionVo
	 */
	public static AccountSessionVo setAccountSessionVo(HttpSession session, AccountSessionVo accountVo){
		session.setAttribute(CommonConstant.LOGIN_ACCOUNT, accountVo);
		return accountVo;
	}
	
	/**
	 * 移除sessionVo
	 * @param session session
	 * @return AccountSessionVo
	 */
	public static void removeAccountSessionVo(HttpSession session){
		session.removeAttribute(CommonConstant.LOGIN_ACCOUNT);
	}
	
	/**
	 * 获取真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}   
}
