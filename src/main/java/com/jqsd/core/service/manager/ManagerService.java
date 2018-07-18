package com.jqsd.core.service.manager;

import cn.dreampie.quartz.QuartzKey;
import cn.dreampie.quartz.QuartzKit;
import cn.dreampie.quartz.job.QuartzCronJob;

import com.jfinal.aop.Before;
import com.jfinal.ext2.core.Service;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jqsd.common.AppConfig;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.model.*;
import com.jqsd.common.util.MD5Util;
import com.jqsd.common.util.StrUtil;
import com.jqsd.common.vo.AccountSessionVo;
import com.jqsd.common.vo.QueryParamVo;
import com.jqsd.core.job.DemoJob;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.quartz.CronScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sam on 9/13/17.
 */
public class ManagerService extends Service {

    /**
     * 查询用户分页信息
     * @param vo
     * @return
     */
    public Page<Admin> getUserPage(QueryParamVo vo){
        return Admin.dao.getUserPage(vo);
    }

    /**
     * 返回所有角色
     * @return
     */
    public List<Role> getAllRole(){
        return Role.dao.findAll();
    }

    /**
     * 保存用户
     * @注解声明开启事务
     * @param admin
     * @return
     */
    @Before(Tx.class)
    public boolean saveUser(Admin admin){
        String id = admin.getId();
        if(StrUtil.isNotBlank(id)){
            admin.update();
        }else{
            id = UUID.randomUUID().toString();
            admin.setId(id);
            admin.setStatus(CommonConstant.STATUS_Y);
            admin.setPassword(MD5Util.getDefaultPwd());
            admin.setCreateTime(new Date());
            admin.save();
        }

        AdminMenu.dao.deleteByAid(id);
        return AdminMenu.dao.saveAdminMenuByRole(id,admin.getRoleId());
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    public boolean resetPwd(String id) {
        return Admin.dao.updatePwd(id,MD5Util.getDefaultPwd());
    }

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @param oPwd
     * @return
     */
    public boolean updatePwd(String id,String pwd,String oPwd) {
        Admin admin = Admin.dao.findById(id);
        if(MD5Util.getMD5Pwd(oPwd).equals(admin.getPassword())){
            return Admin.dao.updatePwd(id,MD5Util.getMD5Pwd(pwd));
        }else{
            return false;
        }
    }
    /**
     * 修改用户启用/禁用状态
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id,String status){
        return Admin.dao.updateStatus(id,status);
    }

    /**
     * 查询菜单
     * @return
     */
    public List<Record> getMenuList(){
        return Module.dao.getMenuList();
    }

    /**
     * 根据级别查询菜单
     * @param level
     * @return
     */
    public List<Record> getMenuByLevel(int level){
        return Module.dao.getMenuByLevel(level);
    }

    /**
     * 保存菜单
     * @param module
     * @return
     */
    public boolean saveMenu(Module module){
        Integer pId = module.getPId();
        module.setLevel(pId==1 ? 1 : 2);
        module.setIsUse(module.getIsUse() ==null ? 0 : 1 );

        if(StrUtil.isBlank(module.getUrl())){
            module.setUrl("");
        }

        return Module.dao.saveMenu(module);
    }

    /**
     * 根据用户ID，查询菜单
     * @param aId
     * @return
     */
    public List<Record> getMenuListByUserId(String aId){
        return Module.dao.getMenuListByUserId(aId);
    }

    /**
     * 保存用户菜单
     * @注解声明开启事务
     * @param aId
     * @param mId
     * @return
     */
    @Before(Tx.class)
    public boolean saveUserMenu(String aId,String mId){
        AdminMenu.dao.deleteByAid(aId);
        return AdminMenu.dao.saveUserMenu(aId,mId);
    }

    /**
     * 角色分页列表
     * @param vo
     * @return
     */
    public Page<Record> getRoleList(QueryParamVo vo){
        return Role.dao.getRoleList(vo);
    }

    /**
     * 保存角色
     * @注解声明开启事务
     * @param role
     * @return
     */
    @Before(Tx.class)
    public boolean saveRole(Role role){
        if(role.getId()==null){
            return role.save();
        }else {
            String omId = Role.dao.findById(role.getId()).getMId();
            List<String> list = Admin.dao.getUserIdByRole(role.getId());

            list.stream().forEach(aId->{
                AdminMenu.dao.deleteById(aId,omId);
                AdminMenu.dao.saveUserMenu(aId,role.getMId());
            });
            return role.update();
        }
    }

    /**
     * 根据角色ID，查询菜单
     * @param id
     * @return
     */
    public List<Record> getMenuListByRoleId(String id){
        return Module.dao.getMenuListByRoleId(id);
    }

    /**
     * 查询所有模块by树
     * @return 模块列表
     */
    public List<Record> getUserModule(AccountSessionVo accountVo) {
        if(accountVo.getUserName().equals("admin")){
            return Module.dao.getModuleAllByTree();
        }else {
            String id = accountVo.getId();
            return Module.dao.getModuleByAid(id);
        }
    }

    public List<DbConfig> getDbList(){
        return DbConfig.dao.findAll();
    }

    public ScheduleJob findJobById(String id){
        return ScheduleJob.dao.findById(id);
    }

    public Page<Record> getJobList(QueryParamVo vo){
        return ScheduleJob.dao.getJobList(vo);
    }



    public boolean changeStatus(QueryParamVo vo){

        if(ScheduleJob.dao.updateStatus(vo)){
            QuartzKey quartzKey = new QuartzKey(vo.getId(), vo.getName());
            if(vo.getStatus().equals(CommonConstant.STATUS_1)){
                //QuartzKit.resumeJob(quartzKey);

                ScheduleJob job = ScheduleJob.dao.findById(vo.getId());
                AppConfig.addJob(job);
            }else{
                //QuartzKit.pauseJob(quartzKey);
                QuartzKit.stopJob(quartzKey);
            }
        }
        return true;
    }

    public boolean deleteJob(int id){
        return ScheduleJob.dao.deleteById(id);
    }

    public boolean saveJob(ScheduleJob job) throws RuntimeException,SQLServerException {

        String cron = StrUtil.getCron(job);
        System.out.println(cron);
        System.out.println(cron.equals("0 */30 * * * ?"));
        //检测cron 表达式是否正确
        CronScheduleBuilder.cronSchedule(cron);

        job.setCron(cron);

        if(ScheduleJob.dao.saveJob(job)){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        String cron = "0 */30 * * * ?";
        CronScheduleBuilder.cronSchedule(cron);
    }

    public Page<Record> getLogPage(QueryParamVo vo){
        return JobLog.dao.getPage(vo);
    }

}
