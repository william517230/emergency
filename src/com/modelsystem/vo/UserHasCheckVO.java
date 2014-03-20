package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.List;

import com.modelsystem.po.Users;

/**
 * 用户的值对象 (Value Object) 
 * @Title: UserVO.java 
 * @Description: 
 * @author	缘梦
 * @date 2012-10-7
 * @version V1.0
 */
public class UserHasCheckVO {
	private String id;			//用户ID
	private String text;	//用户名                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
	private boolean leaf;
	private String icon;
	private boolean checked;
	

	/**
	 * 将用户PO集合转换成VO集合----------带复选框
	 * @param loginRecordList 需要转换的用户集合
	 * @return 用户VO集合
	 */
	public static List<UserHasCheckVO> changeToVO(List<Users> userList) {
		List<UserHasCheckVO> userVOList = new ArrayList<UserHasCheckVO>();
		for (Users user : userList) {
			UserHasCheckVO userVO = new UserHasCheckVO();
			userVO.setId(user.getId());
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
	public static UserHasCheckVO changeToVO(Users user) {
		UserHasCheckVO userVO = new UserHasCheckVO();
		userVO.setId(user.getId());
		userVO.setLeaf(true);
		return userVO;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

}

















/*********************************/
/*package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.modelsystem.po.Role;
import com.modelsystem.po.User;

*//**
 * 用户的值对象 (Value Object) 
 * @Title: UserVO.java 
 * @Description: 
 * @author	缘梦
 * @date 2012-10-7
 * @version V1.0
 *//*
public class UserVO {
	private String id;			//用户ID
	private String username;	//用户名                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
	private String password;	//用户密码
	private String realName;	//用户真实姓名    
	private String sex;			//用户性别
	private String phoneNumber;	//联系方式
	private String address;		//联系地址
	private boolean developer;	//是否开发人员
	private String lastLoginIP;	//最后登陆IP地址
	private Date lastLoginTime;	//最后登陆时间
	private String roles;		//用户担任角色
	
	*//**
	 * 将用户PO集合转换成VO集合
	 * @param loginRecordList 需要转换的用户集合
	 * @return 用户VO集合
	 *//*
	public static List<UserVO> changeToVO(List<User> userList) {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for (User user : userList) {
			UserVO userVO = new UserVO();
			userVO.setId(user.getId());
			userVO.setPassword(user.getPassword());
			userVO.setPhoneNumber(user.getPhoneNumber());
			userVO.setRealName(user.getRealname());
			userVO.setSex(user.getSex());
			String roles = "";
			Set<Role> roleSet = user.getRoleSet();
			for (Role role : roleSet) {
				roles =  roles + role.getRoleName() + " ";
			}
			userVO.setRoles(roles);
			userVO.setUsername(user.getUsername());
			userVO.setAddress(user.getAddress());
			userVOList.add(userVO);
		}
		return userVOList;
	}
	
	*//**
	 * 将用户PO转换成VO
	 * @param loginRecordList 需要转换的用户PO
	 * @return 用户VO
	 *//*
	public static UserVO changeToVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setPassword(user.getPassword());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setRealName(user.getRealname());
		userVO.setSex(user.getSex());
		String roles = "";
		Set<Role> roleSet = user.getRoleSet();
		for (Role role : roleSet) {
			roles =  roles + role.getRoleName() + " ";
		}
		userVO.setRoles(roles);
		userVO.setAddress(user.getAddress());
		userVO.setUsername(user.getUsername());
		return userVO;
	}
	
	*//**drx****//*
	public static UserVO changeToVO2(User user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setUsername(user.getUsername());
		return userVO;
	}
	
	*//***********************************************//*
	//			GETTER AND SETTER METHOD     	   //
	*//***********************************************//*
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isDeveloper() {
		return developer;
	}
	public void setDeveloper(boolean developer) {
		this.developer = developer;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
}
*/