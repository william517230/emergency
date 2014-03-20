package com.modelsystem.service;

import java.util.List;

import com.modelsystem.po.Resource;

public interface ResourceService extends BaseService<Resource> {

	/**
	 * 获取该菜单面板中的菜单树或者菜单树下的子树
	 * @param node 该菜单树的ID
	 * @return 该菜单面板中的菜单树或者菜单树下的子树集合
	 */
	public List<Resource> findMenuTree(String node);

}
