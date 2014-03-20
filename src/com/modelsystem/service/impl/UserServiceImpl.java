package com.modelsystem.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.modelsystem.dao.LoginRecordDao;
import com.modelsystem.dao.RoleDao;
import com.modelsystem.dao.UserDao;
import com.modelsystem.po.LoginRecord;
import com.modelsystem.po.Role;
import com.modelsystem.po.Users;
import com.modelsystem.service.UserService;
import com.modelsystem.util.MD5Encryption;

@Service
public class UserServiceImpl extends BaseServiceImpl<Users> implements
		UserService {

	@Resource
	private UserDao userDao;

	@Resource
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	@Resource
	private LoginRecordDao loginRecordDao;
	@Resource
	private RoleDao roleDao;
	private Logger log = Logger.getLogger("UserServiceImpl");

	// 用户登陆验证
	public String isLoginSuccess(String username, String password) {
		String[] propertyArray = { "username", "password"};
		String[] valueArray = { username, MD5Encryption.getMD5(password)};
		List<Users> userList = userDao.findAll(propertyArray, valueArray);
		if (userList == null || userList.isEmpty()) {
			return "false";
		}
		Users user = userList.get(0);
		if(!user.getStatus()) {
			return "noright";
		}
		String ip = getRequest().getRemoteAddr();
		LoginRecord loginRecord = new LoginRecord("", ip, new Date(), "用户登陆", user);
		loginRecordDao.save(loginRecord);

		Set<Role> roleSet = user.getRoleSet();
		Role r = null;
		Set<com.modelsystem.po.Resource> resourceSet = null;
		Set<String> roles = null;
		if (roleSet.size() != 0) {
			resourceSet = new HashSet<com.modelsystem.po.Resource>();
			roles = new HashSet<String>(roleSet.size());
			int n = 0;
			for (java.util.Iterator<Role> i = roleSet.iterator(); i.hasNext(); n++) {
				r = i.next();
				roles.add(r.getRoleName());
				for(Iterator<com.modelsystem.po.Resource> ii = r.getResourceSet().iterator(); ii.hasNext();) {
					resourceSet.add(ii.next());
				}
			}
		}
		getHttpSession().setAttribute("RESOURCES", resourceSet);
		getHttpSession().setAttribute("ROLES", roles); // 保存登录用户的角色信息
		getHttpSession().setAttribute("loginUser", user); // 将登陆用户记录到Session里面
		return "true";
	}

	// 用户退出
	public boolean isExitSuccess() {
		try {
			Users user = (Users) getHttpSession().getAttribute("loginUser");
			String ip = getRequest().getRemoteAddr();
			LoginRecord loginRecord = new LoginRecord("", ip, new Date(),
					"用户退出", user);
			loginRecordDao.save(loginRecord);
			getHttpSession().removeAttribute("loginUser");
			return true;
		} catch (Exception e) {
			log.error("[用户退出]异常信息 ： ", e);
		}
		return false;
	}

	// 用户删除
	public boolean isDeleteSuccess(Users user) {
		try {
			user = userDao.get(user.getId());
			Set<Role> roleSet = user.getRoleSet();
			for (Role role : roleSet) {
				role.getUserSet().remove(user);
				roleDao.update(role);
			}
			Set<LoginRecord> loginRecordSet = user.getLoginRecordSet();
			for (LoginRecord loginRecord : loginRecordSet) {
				loginRecordDao.delete(loginRecord);
			}
			userDao.delete(user);
			return true;
		} catch (Exception e) {
			log.error("[用户删除]异常信息 ：", e);
		}
		return false;
	}

	// 用户信息更新
	public boolean isUpdateSuccess(Users user) {
		try {
			Users persistent = userDao.get(user.getId());
			String[] ignoreProperties = null;
			System.out.println("password = " + user.getPassword());
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				ignoreProperties = new String[] { "lastLoginIP",
						"lastLoginTime", "roleSet", "loginRecordSet",
						"developer" };
				user.setPassword(MD5Encryption.getMD5(user.getPassword()));
			} else {
				ignoreProperties = new String[] { "lastLoginIP",
						"lastLoginTime", "roleSet", "loginRecordSet",
						"developer", "password" };
			}
			BeanUtils.copyProperties(user, persistent, ignoreProperties);
			userDao.update(persistent);
			return true;
		} catch (Exception e) {
			log.error("[用户更新]异常信息 ：", e);
		}
		return false;
	}
}
