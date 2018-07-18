package com.jqsd.core.controller.base;

import com.jfinal.aop.Duang;
import com.jfinal.ext2.core.ControllerExt;
import com.jfinal.ext2.core.Service;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.constant.PageConstant;
import com.jqsd.common.vo.AccountSessionVo;
import com.jqsd.common.vo.QueryParamVo;

public class BaseController  extends ControllerExt {

	private static final String POST = "POST";

	/**
	 * 判断是否post请求
	 * @return
	 */
	protected boolean isPost(){
	 	return POST.equals(getRequest().getMethod());
	 }

	/**
	 * 获取参数vo
	 * @return
	 */
	protected QueryParamVo getParamVo(){
		int pageNumber = getParaToInt("pageNum",1);		//当前页
		int pageSize = getParaToInt("numPerPage", PageConstant.DEFAULT_PAGESIZE_TEN);//分页数
		return new QueryParamVo(pageNumber,pageSize);
	}

	/**
	 * 获取service实例
	 * @param clazz
	 * @param <Ser>
	 * @return
	 */
	protected  <Ser extends Service> Ser getServiceInstance(Class<Ser> clazz) {
		Ser service = Service.getInstance(clazz,this);
		return Duang.duang(service);
	}

	protected String getAccountId(){
		AccountSessionVo accountVo = (AccountSessionVo)getSession().getAttribute(CommonConstant.LOGIN_ACCOUNT);
		return accountVo.getId();
	}

	protected String getAccountUserName(){
		AccountSessionVo accountVo = (AccountSessionVo)getSession().getAttribute(CommonConstant.LOGIN_ACCOUNT);
		return accountVo.getUserName();
	}

	protected String getAccountRealName(){
		AccountSessionVo accountVo = (AccountSessionVo)getSession().getAttribute(CommonConstant.LOGIN_ACCOUNT);
		return accountVo.getRealName();
	}


	@Override
	public void onExceptionError(Exception e) {

	}
}
