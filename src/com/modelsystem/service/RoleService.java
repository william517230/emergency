package com.modelsystem.service;

import com.modelsystem.po.Role;

public interface RoleService extends BaseService<Role>{

	/**
	 * 角色分配
	 * @param id 需要分配角色的用户id
	 * @param idsString 角色Id
	 * @return 分配成功 ： true <br>
	 *         分配失败 ： false
	 */
	public boolean assignRole(String id, String idsString);
}
