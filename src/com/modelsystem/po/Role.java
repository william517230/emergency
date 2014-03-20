package com.modelsystem.po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @class Role   
 * @declare    
 * @author ZWZ   
 * @time 2013-12-11 下午04:19:51
 */
@Entity
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = -5313665471398804351L;
	
	private String id;
	private String roleName;
	private String roleDescn;
	
	private Set<Users> userSet = new HashSet<Users>();
	private Set<Resource> resourceSet = new HashSet<Resource>();
	
	private boolean checked;

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleName) {
		this.roleName = roleName;
	}

	/** full constructor */
	public Role(String roleName, String roleDescn,
			Set<Users> userSet, Set<Resource> resourceSet) {
		this.roleName = roleName;
		this.userSet = userSet;
		this.roleDescn = roleDescn;
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

	@Column(name = "role_name")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_description")
	public String getRoleDescn() {
		return this.roleDescn;
	}

	public void setRoleDescn(String roleDescn) {
		this.roleDescn = roleDescn;
	}

	@Transient
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setUserSet(Set<Users> userSet) {
		this.userSet = userSet;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_role",
			joinColumns={@JoinColumn(name="role_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")}
	)
	public Set<Users> getUserSet() {
		return userSet;
	}

	public void setResourceSet(Set<Resource> resourceSet) {
		this.resourceSet = resourceSet;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="role_resc",
			joinColumns={@JoinColumn(name="role_id")},
			inverseJoinColumns={@JoinColumn(name="resc_id")}
	)
	public Set<Resource> getResourceSet() {
		return resourceSet;
	}

}