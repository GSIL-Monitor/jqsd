package com.jqsd.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseAdmin<M extends BaseAdmin<M>> extends Model<M> implements IBean {

	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.String getId() {
		return getStr("id");
	}

	public M setUserName(java.lang.String userName) {
		set("userName", userName);
		return (M)this;
	}

	public java.lang.String getUserName() {
		return getStr("userName");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

	public M setRealName(java.lang.String realName) {
		set("realName", realName);
		return (M)this;
	}

	public java.lang.String getRealName() {
		return getStr("realName");
	}

	public M setPhone(java.lang.String phone) {
		set("phone", phone);
		return (M)this;
	}

	public java.lang.String getPhone() {
		return getStr("phone");
	}

	public M setEmail(java.lang.String email) {
		set("email", email);
		return (M)this;
	}

	public java.lang.String getEmail() {
		return getStr("email");
	}

	public M setStatus(java.lang.String status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.String getStatus() {
		return getStr("status");
	}

	public M setLastLoginTime(java.util.Date lastLoginTime) {
		set("lastLoginTime", lastLoginTime);
		return (M)this;
	}

	public java.util.Date getLastLoginTime() {
		return get("lastLoginTime");
	}

	public M setLastLoginIp(java.lang.String lastLoginIp) {
		set("lastLoginIp", lastLoginIp);
		return (M)this;
	}

	public java.lang.String getLastLoginIp() {
		return getStr("lastLoginIp");
	}

	public M setLoginTime(java.util.Date loginTime) {
		set("loginTime", loginTime);
		return (M)this;
	}

	public java.util.Date getLoginTime() {
		return get("loginTime");
	}

	public M setLoginIp(java.lang.String loginIp) {
		set("loginIp", loginIp);
		return (M)this;
	}

	public java.lang.String getLoginIp() {
		return getStr("loginIp");
	}

	public M setRoleId(java.lang.String roleId) {
		set("roleId", roleId);
		return (M)this;
	}

	public java.lang.String getRoleId() {
		return getStr("roleId");
	}

	public M setRoleName(java.lang.String roleName) {
		set("roleName", roleName);
		return (M)this;
	}

	public java.lang.String getRoleName() {
		return getStr("roleName");
	}

}
