package com.jqsd.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRole<M extends BaseRole<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRoleName(java.lang.String roleName) {
		set("roleName", roleName);
		return (M)this;
	}

	public java.lang.String getRoleName() {
		return getStr("roleName");
	}

	public M setMId(java.lang.String mId) {
		set("mId", mId);
		return (M)this;
	}

	public java.lang.String getMId() {
		return getStr("mId");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}

	public java.lang.String getDescription() {
		return getStr("description");
	}

}