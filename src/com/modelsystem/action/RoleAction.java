package com.modelsystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.modelsystem.bean.Pager;
import com.modelsystem.po.Resource;
import com.modelsystem.po.Role;
import com.modelsystem.po.Users;
import com.modelsystem.service.ResourceService;
import com.modelsystem.service.RoleService;
import com.modelsystem.service.UserService;
import com.modelsystem.vo.RoleTreeVO;
import com.modelsystem.vo.RoleVO;

/**
 * 角色Action
 * @Title: RoleAction.java 
 * @Description: 主要处理对用户角色的操作
 * @author	huangjj
 * @date 2012-9-7
 * @version V1.0
 */
public class RoleAction extends BaseAction{

	private static final long serialVersionUID = -6115111488358010647L;
	
	private Role role;
	private Pager pager;
	private String idsString;
	
	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private UserService userService;
	@javax.annotation.Resource
	private ResourceService resourceService;


	//分页查询
	@SuppressWarnings("unchecked")
	public String findByPage() {
		pager = roleService.findByPager(getPager());
		List<RoleVO> list = RoleVO.changeToVO(pager.getList());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", pager.getTotalCount());
		return ajaxJsonByListAndMap(list, jsonMap, null);
	}
	
	//获取角色树
	public String findRoleTree() {
		List<Role> list = roleService.findAll();
		List<RoleTreeVO> listVo = RoleTreeVO.changeToVO(list);
		String[] propertyNames = {"checked"};	//需要过滤的属性
		return ajaxJsonByListDirecdt(listVo, propertyNames);
	}
	
	//查询该用户的角色
	public String findAllRole() {
		List<Role> list = roleService.findAll();
		Set<Role> roleSet = userService.get(id).getRoleSet();
		for(Role role : list) {
			if(roleSet.contains(role)) {
				role.setChecked(true);
			}
		}
		List<RoleVO> voList = RoleVO.changeToVO(list);
		return ajaxJsonByListAndMap(voList, null, null);
	}
	
	//角色保存
	public String save() {
		roleService.save(role);
		return ajaxJsonAfterSubmitForm(true, "保存成功");
	}
	
	//角色修改
	public String update() {
		Role r = roleService.get(role.getId());
		r.setRoleDescn(role.getRoleDescn());
		r.setRoleName(role.getRoleName());
		roleService.update(r);
		return ajaxJsonAfterSubmitForm(true, "更新成功");
	}
	
	//角色删除 
	public String delete() {
		role = roleService.get(role.getId());
		Set<Resource> resourceSet = role.getResourceSet();
		Set<Users> userSet = role.getUserSet(); 
		for(Resource resource :resourceSet) {
			resource.getRoleSet().remove(role);
			resourceService.update(resource);
		}
		for(Users user : userSet) {
			user.getRoleSet().remove(role);
			userService.update(user);
		}
		role.getUserSet().clear();
		roleService.update(role);
		roleService.delete(role);
		return ajaxJsonAfterSubmitForm(true, "删除成功");
	}
	
	//权限分配
	public String assignRole() {
		if(roleService.assignRole(id, idsString)) {
			return ajaxJsonAfterSubmitForm(true, "分配成功");
		}
		return ajaxJsonAfterSubmitForm(false, "分配失败");
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	public String getIdsString() {
		return idsString;
	}
	public void setIdsString(String idsString) {
		this.idsString = idsString;
	}
}
