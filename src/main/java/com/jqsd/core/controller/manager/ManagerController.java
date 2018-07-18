package com.jqsd.core.controller.manager;

import cn.dreampie.quartz.QuartzKey;
import cn.dreampie.quartz.QuartzKit;
import cn.dreampie.quartz.job.QuartzCronJob;
import cn.dreampie.quartz.job.QuartzOnceJob;
import com.alibaba.druid.pool.DruidDataSource;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jqsd.common.model.*;
import com.jqsd.common.util.JsonUtil;
import com.jqsd.common.vo.QueryParamVo;
import com.jqsd.core.controller.base.BaseController;
import com.jqsd.core.job.DemoJob;
import com.jqsd.core.service.manager.ManagerService;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.quartz.*;

import java.util.Date;
import java.util.List;

/**
 * 用户权限管理Controller
 * @author Administrator
 *
 */
public class ManagerController extends BaseController {

	ManagerService service = getServiceInstance(ManagerService.class);

	/**
	 * 展示用户信息列表
	 */
	public  void list(){
		if(isPost()){

			QueryParamVo vo= getParamVo();

			String name = getPara("name");
			String userName = getPara("userName");
			vo.setUserName(userName);
			vo.setName(name);
			Page<Admin> page= service.getUserPage(vo);
			renderJson(page);

		}else{
			List<Role> list = service.getAllRole();
			setAttr("roleList",list);
			render("admin.html");
		}
	}
	/**
	 * 添加用户
	 */
	public void saveUser(){
		Admin admin = getModel(Admin.class);
		if(service.saveUser(admin)){
			renderJson(JsonUtil.getSucc());
		}else{
			renderJson(JsonUtil.getFail());
		}
	}

	/**
	 * 重置用户密码
	 */
	public void resetPwd(){
		String id=getPara("id");
		if(service.resetPwd(id)){
			renderJson(JsonUtil.getSucc("密码重置成功，新密码:123456"));
		}else{
			renderJson(JsonUtil.getFail("密码重置失败"));
		}
	}

	/**
	 * 修改用户状态
	 */
	public void updateStatus(){
		String id = getPara("id");
		String status = getPara("status");
		if(service.updateStatus(id,status)){
			renderJson(JsonUtil.getSucc("修改成功"));
		}else {
			renderJson(JsonUtil.getFail("修改失败"));
		}
	}


	/**
	 * 菜单列表页
	 */
	public void menu(){
		if(isPost()){
			List<Record> list = service.getMenuList();
			renderJson(JsonUtil.getData(list));
		}else {
			List<Record> mList = service.getMenuByLevel(1);
			setAttr("mList",mList);
			render("menu.html");
		}
	}

	/**
	 * 添加修改菜单
	 */
	public void saveMenu(){
		Module module = getModel(Module.class);
		if(service.saveMenu(module)){
			renderJson(JsonUtil.getSucc());
		}else{
			renderJson(JsonUtil.getFail());
		}
	}

	/**
	 * 用户菜单
	 */
	public void userMenu(){
		String aId = getPara("aId").trim();
		if(isPost()){
			List<Record> list =service.getMenuListByUserId(aId);
			renderJson(JsonUtil.getData(list));
		}else {
			setAttr("aId",aId);
			render("userMenu.html");
		}
	}

	/**
	 * 保存用户菜单
	 */
	public void saveUserMenu(){
		String aId = getPara("aId");
		String mId = getPara("mId");
		if(service.saveUserMenu(aId,mId)){
			renderJson(JsonUtil.getSucc());
		}else {
			renderJson(JsonUtil.getFail());
		}
	}

	/**
	 * 角色列表
	 */
	public  void roleList(){
		if(isPost()){
			QueryParamVo vo = getParamVo();
			String name = getPara("roleName");
			vo.setName(name);
			Page<Record> page= service.getRoleList(vo);
			renderJson(JsonUtil.getPage(page));
		}else{
			render("role.html");
		}
	}

	/**
	 * 保存角色
	 */
	public void saveRole(){
		Role role = new Role();
		role.setId(getParaToInt("id"));
		role.setRoleName(getPara("roleName"));
		role.setDescription(getPara("description"));
		role.setMId(getPara("mId"));

		if (service.saveRole(role)) {
			renderJson(JsonUtil.getSucc());
		}else {
			renderJson(JsonUtil.getFail());
		}
	}

	/**
	 * 角色菜单
	 */
	public void roleMenu(){
		String rId = getPara("rId");
		List<Record> list = service.getMenuListByRoleId(rId);
		renderJson(JsonUtil.getData(list));
	}

	/**
	 * 任务列表
	 */
	public  void jobList(){
		if(isPost()){
			QueryParamVo vo = getParamVo();
			String name = getPara("name");
			vo.setName(name);
			Page<Record> page= service.getJobList(vo);
			renderJson(JsonUtil.getPage(page));
		}else{
			setAttr("dbList",service.getDbList());
			render("job.html");
		}
	}

	/**
	 * 改变任务状态
	 */
	public void changeStatus(){
		int id = getParaToInt("id");
		String name = getPara("name");
		String status = getPara("status");

		QueryParamVo vo = getParamVo();
		vo.setStatus(status);
		vo.setId(id);
		vo.setName(name);

		if(service.changeStatus(vo)){
			renderJson(JsonUtil.getSucc("修改成功"));
		}else {
			renderJson(JsonUtil.getFail("修改失败"));
		}
	}

	/**
	 * 执行一次
	 */
	public void workOnce(){
		String jobId = getPara("jobId");

		ScheduleJob job =  service.findJobById(jobId);

		Class clazz = null;
		try {
			 clazz = Class.forName(job.getJobClass());
		} catch (ClassNotFoundException e) {
		}

		long id = System.currentTimeMillis();
		QuartzKey quartzKey = new QuartzKey(id, job.getName());

 		new QuartzOnceJob(quartzKey, new Date(), clazz)
				.addParam("jobId",jobId)
				.addParam("name", job.getName())
				.addParam("dbId", job.getDbId()).start();

		renderJson(JsonUtil.getSucc("正在执行..."));
	}

	/**
	 * 删除任务
	 */
	public void deleteJob(){
		int id = getParaToInt("id");
		if(service.deleteJob(id)){
			renderJson(JsonUtil.getDelSucc());
		}else {
			renderJson(JsonUtil.getDelFail());
		}
	}

	/**
	 * 保存任务
	 */
	public void saveJob(){
		ScheduleJob job = getModel(ScheduleJob.class);
		try {
			if(service.saveJob(job)){
				renderJson(JsonUtil.getSucc());
			}else{
				renderJson(JsonUtil.getFail());
			}
		} catch (SQLServerException e){
			renderJson(JsonUtil.getFail("任务名称已存在!"));
		} catch (RuntimeException e) {
			System.out.println(e);
			e.printStackTrace();
			renderJson(JsonUtil.getFail("cron表达式有误，不能被解析！"));
		}
	}
//0 */10 * * * ?
//0 */10 * * * ?
	public void getJobBbId(){
		String id = getPara("id");
		ScheduleJob job = service.findJobById(id);
		renderJson(JsonUtil.getData(job));
	}

	/**
	 * 日志列表
	 */
	public void logPage(){
		if(isPost()){
			QueryParamVo vo = getParamVo();
			String name = getPara("name");
			vo.setName(name);
			Page<Record> page= service.getLogPage(vo);
			renderJson(JsonUtil.getPage(page));
		}else{
			render("log.html");
		}
	}

}
