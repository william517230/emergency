package com.modelsystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.modelsystem.dao.ResourceDao;
import com.modelsystem.po.Resource;
import com.modelsystem.po.Resource.ResourceType;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDao{

	
	//获取该菜单面板中的菜单树或者菜单树下的子树
	@SuppressWarnings("unchecked")
	public List<Resource> findMenuTree(String node) {
		List<ResourceType> list = new ArrayList<ResourceType>();
		list.add(ResourceType.TreeLeaf);
		list.add(ResourceType.TreeNode);
		String hql = "from Resource where parent = ? and type in (:valueList)"; 
		return getSession().createQuery(hql).setParameter(0, node).setParameterList("valueList", list).list();
	}
	
	//获取树节点下的子树节点
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Resource> findBtnTree(String node) {
		List<ResourceType> list = new ArrayList<ResourceType>();
		list.add(ResourceType.Button);
		String hql = "from Resource where parent = ? type = ? and id in (select resc_id from RoleResource where )";
		
		return getSession().createQuery(hql).setParameter(0, node).list();
	}
}
