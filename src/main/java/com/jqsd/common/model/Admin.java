package com.jqsd.common.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jqsd.common.model.base.BaseAdmin;
import com.jqsd.common.util.StrUtil;
import com.jqsd.common.vo.QueryParamVo;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Admin extends BaseAdmin<Admin> {
	public static final Admin dao = new Admin().dao();

	public Page<Admin> getUserPage(QueryParamVo vo){
		StringBuilder sql = new StringBuilder();
		sql.append(" from t_yf_admin where id!='1'");
		if(StrUtil.isNotBlank(vo.getUserName())){
			sql.append(" and userName='").append(vo.getUserName()).append("' ");
		}
		if(StrUtil.isNotBlank(vo.getName())){
			sql.append(" and realName like '%").append(vo.getName()).append("%' ");
		}

		sql.append(" order by createTime desc");
		return dao.paginate(vo.getPageNumber(),vo.getPageSize(),"select *",sql.toString());
	}

	public List<String> getUserIdByRole(Integer rId){
		return Db.query("select id from t_yf_admin where roleId like '%"+rId+"%'");
	}

	public boolean updatePwd(String id,String pwd){
		return new Admin().setId(id).setPassword(pwd).update();
	}

	public boolean updateStatus(String id,String status){
		return new Admin().setId(id).setStatus(status).update();
	}

	public Admin getAdminByUserName(String userName){
		return dao.findFirst("select * from t_yf_admin where userName = ?",userName);
	}
}
