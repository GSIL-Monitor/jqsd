package com.jqsd.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 树结构Vo
 */
public class TreeVo implements Serializable{

	/**
	 * ser
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * pId
	 */
	private String pId;
	
	/**
	 * name
	 */
	private String name;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * icon
	 */
	private String icon;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * children List
	 */
	private List<TreeVo> children;
	
	/**
	 * permissions List
	 */
	private List<TreeVo> permissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<TreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}

	public List<TreeVo> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<TreeVo> permissions) {
		this.permissions = permissions;
	}
	
	public String getDefaultTypeId(){
		String typeId = null;
		if (getChildren() !=null && getChildren().size()>0) {
			TreeVo treeVo = getChildren().get(0);
			typeId = treeVo.getId();
			if (treeVo.getChildren() !=null && treeVo.getChildren().size()>0) {
				TreeVo treeChildVo = treeVo.getChildren().get(0);
				typeId = treeChildVo.getId();
			}
		}
		return typeId;
	}
}
