package com.jqsd.common.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.model.base.BaseJobLog;
import com.jqsd.common.vo.QueryParamVo;

import java.util.Date;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class JobLog extends BaseJobLog<JobLog> {
	public static final JobLog dao = new JobLog().dao();

	public boolean saveLog(long jobId,String name,String msg,String status){
		return new JobLog()
				.setJobId(jobId)
				.setJobName(name)
				.setMsg(msg)
				.setStatus(status)
				.setCreateTime(new Date())
				.remove("id")
				.save();
	}

	public Page<Record> getPage(QueryParamVo vo){
		StringBuilder sql = new StringBuilder();
		sql.append("from t_yf_job_log ");

		sql.append(" order by createTime desc ");
		return Db.paginate(vo.getPageNumber(),vo.getPageSize(),"select * ",sql.toString());
	}
}