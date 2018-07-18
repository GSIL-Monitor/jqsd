package com.jqsd.core.job;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.AppConfig;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.model.JobLog;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;

/**
 * Created by sam on 11/28/17.
 */
public class DemoJob implements Job{
        Log log = Log.getLog(this.getClass());

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            //get param  from  job
            Map data = jobExecutionContext.getJobDetail().getJobDataMap();

            long jobId = (Long) data.get("id");
            String name = data.get("name")+"";
            JobLog.dao.saveLog(jobId,name," job start ------------->"+data.get("name")+"", CommonConstant.JOB_SUCC);
            log.info(" job start ------------->"+data.get("name")+"");
        }

    public static void main(String[] args) {
        AppConfig.init();


        List<Record> list = Db.find("select * from t_gl_voucher");

        System.out.println(list);

        //String a = "1";
        //int b = 1
        //System.out.println(a.equals(b+""));


        System.out.println("test");
    }
}
