package com.modelsystem.service;

import com.modelsystem.po.Users;

public interface UserService extends BaseService<Users>{
	
	/**
	 * 用户登陆验证
	 * @param username 用户名
	 * @param password 用户密码
	 * @return 登陆成功 ： true
	 *         登陆失败 : false
	 */
	public String isLoginSuccess(String username, String password);
	
	/**
	 * 用户退出验证
	 * @return 退出成功 ： true	<br>
	 * 		         退出失败 ： false
	 */
	public boolean isExitSuccess();
	
	/**
	 * 用户删除
	 * @return 删除成功 ： true	<br>
	 * 		        删除失败 ： false
	 */
	public boolean isDeleteSuccess(Users user);
	
	/**
	 * 用户更新
	 * @return 更新成功 ： true	<br>
	 * 		        更新失败 ： false
	 */
	public boolean isUpdateSuccess(Users user);
	
}
