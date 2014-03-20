package com.modelsystem.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.modelsystem.bean.Pager;
import com.modelsystem.po.Users;
import com.modelsystem.service.UserService;
import com.modelsystem.util.MD5Encryption;
import com.modelsystem.vo.UserHasCheckVO;
import com.modelsystem.vo.UserVO;

/**
 * 用户Action
 * @Title: UserAction.java 
 * @Description: 用来处理各种与用户信息相关的请求
 * @author	huangjj
 * @date 2012-9-17 
 * @version V1.0
 */
public class UserAction extends BaseAction{

	private static final long serialVersionUID = 7363546285536187370L;

	private String username;	//用户名
	private String password;	//用户密码
	private Integer status;		//是否启用
	private Users user;
	
	private String check;

	@Resource
	private UserService userService;
	
	// 用户登陆
	public String login() {
		String str = userService.isLoginSuccess(username, password);
		System.out.println("str = " + str + " " + username + " " + password);
		if(str.equals("true")) {
			return ajaxJsonAfterSubmitForm(true, "登陆成功");
		}
		if(str.equals("noright")) {
			return ajaxJsonAfterSubmitForm(false, "您的账户受限！");
		}
		return ajaxJsonAfterSubmitForm(false, "登陆失败");
	}
	
	//用户退出
	public String exit() {
		if(userService.isExitSuccess()) {
			return ajaxJsonAfterSubmitForm(true, "退出成功");
		}
		return ajaxJsonAfterSubmitForm(false, "退出失败");
	}
	
	//个人密码修改
	public String changePassword() {
		Users user = (Users)getSessionAttribute("loginUser");
		user.setPassword(MD5Encryption.getMD5(password));
		userService.update(user);
		getHttpSession().setAttribute("loginUser", user);
		return ajaxJsonAfterSubmitForm(true, "密码修改成功");
	}
	
	
	//修改个人资料
	public String updatePersonalInfo() {
		userService.get(user.getId());
		return ajaxJsonAfterSubmitForm(true, "个人资料修改成功");
	}
	
	//查询登陆用户的信息
	public String queryPersonalInfo() {
		Users user = userService.get(((Users)getSessionAttribute("loginUser")).getId());
		List<Users> userList = new ArrayList<Users>();
		userList.add(user);
		List<UserVO> voList = UserVO.changeToVO(userList);
		return ajaxJsonByListAndMap(voList, null, null);
	}
	
	
	// 用户保存
	public String save() {
		try{
			user.setStatus(setStatusValue());
			password = MD5Encryption.getMD5("123456");
			user.setPassword(password);
			userService.save(user);
			return ajaxJsonAfterSubmitForm(true, "保存成功");
		} catch (Exception e) {
			return ajaxJsonAfterSubmitForm(false, "保存失败");
		}
	}
	
	//用户信息修改
	public String update() {
		user.setStatus(setStatusValue());
		if(userService.isUpdateSuccess(user)) {
			return ajaxJsonAfterSubmitForm(true, "更改成功");
		}
		return ajaxJsonAfterSubmitForm(false, "更改失败");
	}
	
	//删除用户
	public String delete() {
		if(userService.isDeleteSuccess(user)) {
			return ajaxJsonAfterSubmitForm(true, "删除成功");
		}
		return ajaxJsonAfterSubmitForm(false, "删除失败");
	}
	
	//分页查看用户信息
	@SuppressWarnings("unchecked")
	public String findByPage() {
		Pager pager = userService.findByPager(getPager());
		List<UserVO> list = UserVO.changeToVO(pager.getList());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", pager.getTotalCount());
		return ajaxJsonByListAndMap(list, jsonMap, null);
	}
	
	//查询全部用户
	public String queryAll() {
		List<Users> list = userService.findAll();
		List<UserVO> voList = UserVO.changeToVO(list);
		return ajaxJsonByListAndMap(voList, null, null);
	}
	
	//查询全部用户__给日程提醒用
		public String query_All() {
			List<Users> list = userService.findAll();
			List<UserVO> voList = UserVO.changeToVO(list);
			String[] propertyNames = {"leaf", "icon"};	//需要过滤的属性
			return ajaxJsonByListDirecdt(voList, propertyNames);
		}
	
	//查询所有用户
	public String findAll() {
		if(check.equals("true")) {
			List<UserHasCheckVO> voList;
			voList = UserHasCheckVO.changeToVO(userService.findAll());
			return ajaxJsonByListDirecdt(voList, null);
		} else {
			List<UserVO> voList;
			voList = UserVO.changeToVO(userService.findAll());
			return ajaxJsonByListDirecdt(voList, null);
		}
	}
	
	/***********************************************
	 * USEFULL METHOD
	 * ********************************************/
	/**
	 * 
	 */
	public Boolean setStatusValue() {
		if(status == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将迭代子中的数据转换为以逗号分割的字符串
	 */
	public String changIterateToString(Iterator<?> i) {
		StringBuilder sb = new StringBuilder();
		while(i.hasNext()) {
			sb.append(" , ").append(i.next());
		}
		return sb.substring(1, sb.length());
	}
	/***********************************************/
	//			GETTER AND SETTER METHOD     	   //
	/***********************************************/
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
}
