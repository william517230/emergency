package com.modelsystem.dao;

import java.util.List;

import com.modelsystem.po.Resource;

public interface ResourceDao extends BaseDao<Resource> {

	/**
	 * 获取该菜单面板中的菜单树或者菜单树下的子树
	 * @param node 该菜单树的Id
	 * @return 该菜单树的直接子结点集合
	 */
	public List<Resource> findMenuTree(String node);

}
