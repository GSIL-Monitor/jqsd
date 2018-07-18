package com.jqsd.core.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.constant.PageConstant;
import com.jqsd.common.util.JsonUtil;
import com.jqsd.common.util.TreeUtil;
import com.jqsd.common.vo.AccountSessionVo;
import com.jqsd.common.vo.TreeVo;
import com.jqsd.core.controller.base.BaseController;
import com.jqsd.core.service.manager.ManagerService;

import java.util.List;
import java.util.Map;

public class IndexController extends BaseController {

	ManagerService service = getServiceInstance(ManagerService.class);


	public void index() throws Exception{
		AccountSessionVo accountVo = (AccountSessionVo)getSession().getAttribute(CommonConstant.LOGIN_ACCOUNT);
		List<Record> modList = service.getUserModule(accountVo);

		TreeVo topTree = TreeUtil.getTree(modList, "1");
		List<Record> list = Lists.newArrayList();
		modList.stream().filter(record -> record.getInt("level")==2)
				.forEach(record -> list.add(record));

		setAttr("option",list);
		setAttr("modules", new Gson().toJson(topTree.getChildren()));

		render(PageConstant.PAGE_INDEX);
	}

	/**
	 * 修改密码
	 */
	public void updatePwd(){
		AccountSessionVo accountVo = (AccountSessionVo)getSession().getAttribute(CommonConstant.LOGIN_ACCOUNT);
		String aId = accountVo.getId();
		String oPwd=getPara("oPwd");
		String pwd=getPara("pwd");

		if(service.updatePwd(aId,pwd,oPwd)){
			renderJson(JsonUtil.getSucc("修改成功"));
		}else{
			renderJson(JsonUtil.getFail("原始密码错误"));
		}
	}

	/**
	 * main主页数据
	 */
	public void main(){
			render("main.html");
	}



}
