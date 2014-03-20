/**
 * 
 */
package com.modelsystem.po;


/**
 * @class RoleResource
 * @declare
 * @author ZWZ
 * @time 2013-12-25 下午03:12:21
 */
//@Table(name="role_resc")
public class RoleResource {

	private String roleId;
	private String resourceId;

	public RoleResource() {}
	
	public RoleResource(String roleId, String resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

//	@Id
//	@Column(name="role_id")
	public String getRoleId() {
		return roleId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

//	@Id
//	@Column(name="resc_id")
	public String getResourceId() {
		return resourceId;
	}

}
