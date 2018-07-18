package com.jqsd.common.vo;

import java.io.Serializable;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 帐号SessionVo
 */
public class AccountSessionVo implements Serializable{

	/**
	 * ser
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 帐号ID
	 */
	private String id;


	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 一级菜单
	 */
	private List<TreeVo> topMenu;

	/**
	 * 二级菜单
	 */
	private List<TreeVo> menuList;
	
	private List<Record> allMenus;
	

	public List<Record> getAllMenus() {
		return allMenus;
	}

	public void setAllMenus(List<Record> allMenus) {
		this.allMenus = allMenus;
	}

	public List<TreeVo> getTopMenu() {
		return topMenu;
	}

	public void setTopMenu(List<TreeVo> topMenu) {
		this.topMenu = topMenu;
	}

	/**
	 * 当前菜单
	 */
	private String currentModule;
	
	private String currentModuleName;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
