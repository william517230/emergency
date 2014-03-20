package com.modelsystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modelsystem.dao.ResourceDao;
import com.modelsystem.po.Resource;
import com.modelsystem.service.ResourceService;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService{
	
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	
	@javax.annotation.Resource
	public void setBaseDao(ResourceDao resourceDao) {
		super.setBaseDao(resourceDao);
	}

	//获取该菜单面板中的菜单树或者菜单树下的子树
	public List<Resource> findMenuTree(String node) {
		return resourceDao.findMenuTree(node);
	}
}
