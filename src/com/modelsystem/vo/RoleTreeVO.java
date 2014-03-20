package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.List;

import com.modelsystem.po.Role;

public class RoleTreeVO {
	private String id; // 角色ID
	private String text; // 角色名称
	private Boolean leaf;
	private String roleDescription; // 角色描述
	private int sort; // 显示顺序

	/**
	 * 将角色PO集合转换成VO集合
	 * 
	 * @param roleList
	 *            需要转换的角色集合
	 * @return 角色VO集合
	 */
	public static List<RoleTreeVO> changeToVO(List<Role> roleList) {
		List<RoleTreeVO> roleVOList = new ArrayList<RoleTreeVO>();
		for (Role role : roleList) {
			RoleTreeVO roleVO = new RoleTreeVO();
			roleVO.setId(role.getId());
			roleVO.setRoleDescription(role.getRoleDescn());
			roleVO.setText(role.getRoleName());
			roleVO.setLeaf(true);
			roleVOList.add(roleVO);
		}
		return roleVOList;
	}

	/**
	 * 将角色PO转换成VO
	 * 
	 * @param roleList
	 *            需要转换的角色
	 * @return 角色VO
	 */
	public static RoleTreeVO changeToVO(Role role) {
		RoleTreeVO roleVO = new RoleTreeVO();
		roleVO.setId(role.getId());
		roleVO.setRoleDescription(role.getRoleDescn());
		roleVO.setText(role.getRoleName());
		return roleVO;
	}

	/***********************************************/
	// GETTER AND SETTER METHOD //
	/***********************************************/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
