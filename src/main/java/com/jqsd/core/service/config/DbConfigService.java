package com.jqsd.core.service.config;

import com.jfinal.ext2.core.Service;
import com.jfinal.plugin.activerecord.Page;
import com.jqsd.common.model.DbConfig;
import com.jqsd.common.model.ScheduleJob;
import com.jqsd.common.vo.QueryParamVo;

import java.util.Date;

/**
 * Created by sam on 12/4/17.
 */
public class DbConfigService extends Service {

    /**
     * 查询用户分页信息
     * @param vo
     * @return
     */
    public Page<DbConfig> getPage(QueryParamVo vo){
        return DbConfig.dao.getPage(vo);
    }

    public boolean saveConfig(DbConfig config){
        return config.setCreateTime(new Date()).remove("id").save();
    }
    
    public ScheduleJob findJobById(String id){
        return ScheduleJob.dao.findById(id);
    }
    
}
