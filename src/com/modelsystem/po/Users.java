package com.modelsystem.po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @class Users   
 * @declare    
 * @author ZWZ   
 * @time 2013-12-11 下午04:12:48
 */
@Entity
@Table(name="users")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = -468192286338249216L;
	
	private String id;
	private String username;
	private String password;
	private String realname;
	private String sex;
	private String phoneNumber;
	private String address;
	private Boolean status;		//是否启用
	private Set<LoginRecord> loginRecordSet = new HashSet<LoginRecord>();	//登陆日志记录
	private Set<Role> roleSet = new HashSet<Role>();
	private Set<Department> departmentSet = new HashSet<Department>();

	public Users() {
	}

	public Users(String username) {
		this.username = username;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setLoginRecordSet(Set<LoginRecord> loginRecordSet) {
		this.loginRecordSet = loginRecordSet;
	}

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	public Set<LoginRecord> getLoginRecordSet() {
		return loginRecordSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_role",
			joinColumns={@JoinColumn(name="user_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")}
	)
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setDepartmentSet(Set<Department> departmentSet) {
		this.departmentSet = departmentSet;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_dept",
			joinColumns={@JoinColumn(name="user_id")},
			inverseJoinColumns={@JoinColumn(name="dept_id")}
	)
	public Set<Department> getDepartmentSet() {
		return departmentSet;
	}
}