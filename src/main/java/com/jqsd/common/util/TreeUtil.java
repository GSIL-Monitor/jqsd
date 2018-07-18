package com.jqsd.common.util;

import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.vo.TreeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具
 */
public class TreeUtil {

	/**
	 * 设置子节点
	 * @param treeList 数据
	 * @param tree 父节点
	 */
	private static void setChild(List<TreeVo> treeList, TreeVo tree){
		List<TreeVo> tmpTreeList = new ArrayList<TreeVo>();
		List<TreeVo> removeTreeList = new ArrayList<TreeVo>();
		tmpTreeList.addAll(treeList);
		
		TreeVo treeVo = new TreeVo();
		for (int i = 0; i < treeList.size(); i++) {
			treeVo = treeList.get(i);
			if (tree.getId().equals(treeVo.getpId())){
				tmpTreeList.remove(treeVo);
				removeTreeList.add(treeVo);
				if (tree.getChildren() == null){
					tree.setChildren(new ArrayList<TreeVo>());
				}
				tree.getChildren().add(treeVo);
			}
		}
		treeList = null;
		
		if (removeTreeList.size() > 0){
			for (int i = 0; i < removeTreeList.size(); i++) {
				setChild(tmpTreeList, tree.getChildren().get(i));
			}
			removeTreeList = null;
			tmpTreeList = null;
		}
	}
	
    /**
 	 * 获取数结构
 	 * @param treeList module数据
 	 * @param pId 顶级树
 	 * @return 树结构
 	 */
 	public static TreeVo getTree(List<Record> treeList, String pId) {
 		TreeVo topTree = new TreeVo();
		TreeVo treeVo = null;
		List<TreeVo> tmpTreeVoList = new ArrayList<TreeVo>();
		for (Record tmpRecord : treeList) {
			treeVo = new TreeVo();
			treeVo.setId(tmpRecord.getStr("id"));
			treeVo.setpId(tmpRecord.getStr("pId"));
			treeVo.setName(tmpRecord.getStr("name"));
			treeVo.setUrl(tmpRecord.getStr("url"));
			treeVo.setIcon(tmpRecord.getStr("icon"));
			tmpTreeVoList.add(treeVo);
			
			if (pId.equals(treeVo.getId())){
				topTree = treeVo;
			}
		}
		
		tmpTreeVoList.remove(topTree);
 		setChild(tmpTreeVoList, topTree);
 		
 		return topTree;
 	}
 	

}
