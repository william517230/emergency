package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.modelsystem.po.Role;
import com.modelsystem.po.Users;

/**
 * 用户的值对象 (Value Object) 
 * @Title: UserVO.java 
 * @Description: 
 * @author	缘梦
 * @date 2012-10-7
 * @version V1.0
 */
public class UserVO {
	private String id;			//用户ID
	private String username;	//用户名
	private String realname;
	private String sex;
	private String roles;
	private String address;
	private String phoneNumber;
	private Boolean status;
	private boolean leaf;
	private String icon;
	
	/**
	 * 将用户PO集合转换成VO集合----------不带复选框
	 * @param loginRecordList 需要转换的用户集合
	 * @return 用户VO集合
	 */
	public static List<UserVO> changeToVO(List<Users> userList) {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for (Users user : userList) {
			UserVO userVO = new UserVO();
			userVO.setId(user.getId());
			userVO.setUsername(user.getUsername());
			userVO.setRealname(user.getRealname());
			userVO.setSex(user.getSex());
			userVO.setAddress(user.getAddress());
			userVO.setPhoneNumber(user.getPhoneNumber());
			userVO.setRoles(UserVO.changIterateToString(user.getRoleSet().iterator()));
			userVO.setStatus(user.getStatus());
			userVO.setLeaf(true);
			userVOList.add(userVO);
		}
		return userVOList;
	}
	
	
	/**
	 * 将用户PO转换成VO
	 * @param loginRecordList 需要转换的用户PO
	 * @return 用户VO
	 */
	public static UserVO changeToVO(Users user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setUsername(user.getUsername());
		userVO.setLeaf(true);
		return userVO;
	}
	
	/***********************************************
	 * USEFULL METHOD
	 * ********************************************/
	/**
	 * 将迭代子中的角色数据转换为以逗号分割的字符串
	 */
	public static String changIterateToString(Iterator<Role> i) {
		StringBuilder sb = new StringBuilder();
		while(i.hasNext()) {
			sb.append(",").append(i.next().getRoleName());
		}
		if(sb.length() != 0) {
			return sb.substring(1, sb.length());
		}
		return "";
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getRealname() {
		return realname;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getSex() {
		return sex;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getRoles() {
		return roles;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getAddress() {
		return address;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Boolean getStatus() {
		return status;
	}


	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

}