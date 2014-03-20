package com.modelsystem.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.modelsystem.dao.RoleDao;
import com.modelsystem.dao.UserDao;
import com.modelsystem.po.Role;
import com.modelsystem.po.Users;
import com.modelsystem.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;
	
	@Resource
	public void setBaseDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
	}
	
	//角色分配
	public boolean assignRole(String id, String idsString) {
		Users user = userDao.get(id);
		if(idsString == null || idsString.isEmpty()) {
			for (Role role : user.getRoleSet()) {
				role.getUserSet().remove(user);
				roleDao.update(role);
			}
			user.getRoleSet().clear();
		} else {
			user.getRoleSet().clear();
			String[] idArray = idsString.replaceAll("=on&", ",").replace("=on", "").split(",");
			for(String idString : idArray) {
				Role role = roleDao.get(idString);
				user.getRoleSet().add(role);
				roleDao.update(role);
			}
		}
		userDao.update(user);
		return true;
	}
}
