package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.List;

import com.modelsystem.po.Role;

public class RoleVO {
	private String id;	//角色ID
	private String roleName;	//角色名称
	private String roleDescription;	//角色描述
	private boolean checked;	//是否已经被选择
	private int sort;	//显示顺序

	
	/**
	 * 将角色PO集合转换成VO集合
	 * @param roleList 需要转换的角色集合
	 * @return 角色VO集合
	 */
	public static List<RoleVO> changeToVO(List<Role> roleList) {
		List<RoleVO> roleVOList = new ArrayList<RoleVO>();
		for (Role role : roleList) {
			RoleVO roleVO = new RoleVO();
			roleVO.setChecked(role.isChecked());
			roleVO.setId(role.getId());
			roleVO.setRoleDescription(role.getRoleDescn());
			roleVO.setRoleName(role.getRoleName());
			roleVOList.add(roleVO);
		}
		return roleVOList;
	}
	
	/**
	 * 将角色PO转换成VO
	 * @param roleList 需要转换的角色
	 * @return 角色VO
	 */
	public static RoleVO changeToVO(Role role) {
		RoleVO roleVO = new RoleVO();
		roleVO.setChecked(role.isChecked());
		roleVO.setId(role.getId());
		roleVO.setRoleDescription(role.getRoleDescn());
		roleVO.setRoleName(role.getRoleName());
		return roleVO;
	}
	
	/***********************************************/
	//			GETTER AND SETTER METHOD     	   //
	/***********************************************/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
