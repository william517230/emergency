package com.modelsystem.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.modelsystem.bean.Pager;
import com.modelsystem.po.Resource;
import com.modelsystem.po.Role;
import com.modelsystem.po.Users;
import com.modelsystem.po.Resource.ResourceType;
import com.modelsystem.service.ResourceService;
import com.modelsystem.service.RoleService;
import com.modelsystem.service.UserService;

/**
 * 权限资源Action
 * @Title: ResourceAction.java 
 * @Description: 主要处理对权限资源的操作
 * @author	huangjj
 * @date 2012-9-7
 * @version V1.0
 */
public class ResourceAction extends BaseAction {

	private static final long serialVersionUID = -168023599588560164L;

	private String node; // 菜单树节点的Id
	private String type; // 资源类型
	private Resource resource; // 资源
	private String roleId;
	private String resourceIds;
	private String rescType; // 菜单树类型

	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private UserService userService;
	Logger log = Logger.getLogger("ResourceAction");

	// 获取权限范围内的菜单面板
	public String findResourcePanelList() {
		Users user = (Users) getSessionAttribute("loginUser");
		Set<Role> roleSet = userService.get(user.getId()).getRoleSet();
		List<Resource> list = new ArrayList<Resource>();
		for (Role role : roleSet) {
			Set<Resource> resourceSet = role.getResourceSet();
			for (Resource resource : resourceSet) {
				if (!list.contains(resource)
						&& resource.getType().equals(ResourceType.TreePanel)) {
					list.add(resource);
				}
			}
		}
		Resource[] resourcesARrray = {};
		resourcesARrray = list.toArray(resourcesARrray);
		resourcesARrray = sort(resourcesARrray);
		list.clear();
		for (Resource resource : resourcesARrray) {
			list.add(resource);
		}
		String[] filterNames = { "checked", "roleSet" }; // 需要过滤的属性
		return ajaxJsonByListAndMap(list, null, filterNames);
	}

	// 获取该菜单面板中的菜单树或者菜单树下的子树
	public String findMenuTree() {
		Users user = (Users) getSessionAttribute("loginUser");
		Set<Role> roleSet = userService.get(user.getId()).getRoleSet();
		List<Resource> list = new ArrayList<Resource>();
		for (Role role : roleSet) {
			Set<Resource> resourceSet = role.getResourceSet();
			for (Resource resource : resourceSet) {
				if ((!list.contains(resource))
						&& isOwnerOfTheNode(resource, node)) {
					list.add(resource);
				}
			}
		}
		Resource[] resourcesArray = {};
		resourcesArray = list.toArray(resourcesArray);
		resourcesArray = sort(resourcesArray);
		list.clear();
		for (Resource resource : resourcesArray) {
			list.add(resource);
		}
		String[] filterNames = { "checked", "roleSet" }; // 需要过滤的属性
		return ajaxJsonByListDirecdt(list, filterNames);
	}

	// 查找节点下的按钮URL资源
	public String findBtnList() {
		Users user = (Users) getSessionAttribute("loginUser");
		Set<Role> roleSet = userService.get(user.getId()).getRoleSet();
		List<Resource> list = new ArrayList<Resource>();
		for (Role role : roleSet) {
			Set<Resource> resourceSet = role.getResourceSet();
			for (Resource resource : resourceSet) {
				if ((!list.contains(resource))
						&& resource.getParent().equals(node)) {
					list.add(resource);
				}
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", list.size());
		String[] filterNames = { "checked", "roleSet", "descn", "leaf",
				"type", "parent", "systemed" }; // 需要过滤的属性
		return ajaxJsonByListAndMap(list, jsonMap, filterNames);
	}

	// 查询总资源树
	public String findRescTree() {
		if (roleId == null || "".equals(roleId)) {
			return ajaxJsonByListDirecdt(new ArrayList<Resource>(), null);
		}
		List<Resource> rescList = new ArrayList<Resource>();
		Set<Resource> resourceSet = roleService.get(roleId).getResourceSet();
		String[] propertyNameArray = { "parent" };
		Object[] valueArray = { node };
		List<Resource> list = resourceService.findAll(propertyNameArray,
				valueArray);
		for (Resource resource : list) {
			System.out.println("resource = " + resource.getText());
			if (resourceSet.contains(resource)) {
				rescList.add(resource);
			}
		}
		String[] filterNames = { "roleSet", "checked" };
		return ajaxJsonByListDirecdt(rescList, filterNames);
	}

	// 查找树叶子上的按钮
	public String findBtn() {
		if (node == null || "".equals(node)) {
			return ajaxJsonByListAndMap(new ArrayList<Resource>(), null, null);
		}
		Set<Resource> resourceSet = roleService.get(roleId).getResourceSet();
		String[] propertyNameArray = { "parent", "type" };
		Object[] valueArray = { node, ResourceType.Button };
		List<Resource> list = resourceService.findAll(propertyNameArray,
				valueArray);
		for (Resource resource : list) {
			if (resourceSet.contains(resource)) {
				resource.setChecked(true);
			}
		}
		String[] propertyNames = { "roleSet" };
		return ajaxJsonByListAndMap(list, null, propertyNames);
	}

	// 查找该系统的资源
	public String findResourceTree() {
		if(roleId == null || "".equals(roleId)) {
			return ajaxJsonByListDirecdt(new ArrayList<Resource>(), null);
		}
		Set<Resource> resourceSet = roleService.get(roleId).getResourceSet();
		String[] propertyNameArray = { "parent" };
		Object[] valueArray = { node };
		List<Resource> list = resourceService.findAll(propertyNameArray,
				valueArray);
		for (Resource resource : list) {
			if (resourceSet.contains(resource)) {
				resource.setChecked(true);
			}
		}
		String[] filterNames = { "roleSet" }; // 需要过滤的属性
		return ajaxJsonByListDirecdt(list, filterNames);
	}

	// 查找父类
	public String findResourceParent() {
		List<ResourceType> valueList = new ArrayList<Resource.ResourceType>();
		if (type != null && !"".equals(type) && type.equals("btn")) {
			valueList.add(ResourceType.TreeLeaf);
		} else {
			valueList.add(ResourceType.TreePanel);
			valueList.add(ResourceType.TreeNode);
		}
		List<Resource> list = resourceService.findAll("type", valueList);
		Resource resource = new Resource("0", "无父类");
		list.add(0, resource);
		String[] propertyNames = { "checked", "roleSet" }; // 需要过滤的属性
		return ajaxJsonByListAndMap(list, null, propertyNames);
	}

	// 分页查询对象
	public String findByPage() {
		Pager pager = resourceService.findByPager(getPager());
		String[] filterNames = { "roleSet" };
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", pager.getTotalCount());
		return ajaxJsonByListAndMap(pager.getList(), jsonMap, filterNames);
	}

	// 保存资源
	public String save() {
		int resourceTypeNumber = Integer.parseInt(type);
		switch (resourceTypeNumber) {
		case 0:
			resource.setType(ResourceType.TreePanel);
			resource.setLeaf(false);
			break;
		case 1:
			resource.setType(ResourceType.TreeNode);
			resource.setLeaf(false);
			break;
		case 2:
			resource.setType(ResourceType.TreeLeaf);
			resource.setLeaf(true);
			break;
		case 3:
			resource.setType(ResourceType.Button);
			resource.setLeaf(false);
			break;
		case 4:
			resource.setType(ResourceType.Other);
			resource.setLeaf(true);
			break;
		case 5:
			resource.setType(ResourceType.Search);
			resource.setLeaf(true);
			break;
		default:
			break;
		}
		resourceService.save(resource);
		return ajaxJsonAfterSubmitForm(true, "保存成功");
	}

	// 删除资源
	public String delete() {
		resource = resourceService.get(resource.getId());
		Set<Role> roleSet = resource.getRoleSet();
		for (Role role : roleSet) {
			role.getResourceSet().remove(resource);
			roleService.update(role);
		}
		resource.getRoleSet().clear();
		resourceService.update(resource);
		resourceService.delete(resource);
		return ajaxJsonAfterSubmitForm(true, "删除成功！");
	}

	// 更新资源
	public String update() {
		int resourceTypeNumber = Integer.parseInt(type);
		switch (resourceTypeNumber) {
		case 0:
			resource.setType(ResourceType.TreePanel);
			resource.setLeaf(false);
			break;
		case 1:
			resource.setType(ResourceType.TreeNode);
			resource.setLeaf(false);
			break;
		case 2:
			resource.setType(ResourceType.TreeLeaf);
			resource.setLeaf(true);
			break;
		case 3:
			resource.setType(ResourceType.Button);
			resource.setLeaf(false);
			break;
		case 4:
			resource.setType(ResourceType.Other);
			resource.setLeaf(true);
			break;
		case 5:
			resource.setType(ResourceType.Search);
			resource.setLeaf(true);
			break;
		default:
			break;
		}
		Resource resc = resourceService.get(resource.getId());
		String[] ignoreProperties = null;
		ignoreProperties = new String[] { "systemed", "checked", "roleSet" };
		BeanUtils.copyProperties(resource, resc, ignoreProperties);
		resourceService.update(resc);
		return ajaxJsonAfterSubmitForm(true, "更新成功");
	}

	// 资源分配：按钮资源 / 模块资源
	public String assignResource() {
		Boolean flag = false;
		if (resourceIds == null || "".equals(resourceIds)) {
			flag = true;
		}
		String[] resourceIdArray = null;
		Role role = roleService.get(roleId);
		Set<Resource> rescSet = role.getResourceSet();
		if (type.equals("btn")) {
			resourceIds = resourceIds.replaceAll("=on&", ",")
					.replace("=on", "");
			resourceIdArray = resourceIds.split(",");
			// 记录权限中是菜单的字符串
			for (java.util.Iterator<Resource> i = rescSet.iterator(); i
					.hasNext();) {
				resource = i.next();
				if (resource.getType().compareTo(ResourceType.Button) != 0
						|| !resource.getParent().equals(node)) {
					// 资源是非按钮或者非该节点下的按钮
					if (flag) {
						resourceIds += resource.getId();
						flag = false;
					} else {
						resourceIds += "," + resource.getId();
					}
				}
			}
		} else if (type.equals("menu")) {
			resourceIdArray = resourceIds.split(",");
			// 记录权限中是新权限按钮的字符串
			for (java.util.Iterator<Resource> i = rescSet.iterator(); i
					.hasNext();) {
				resource = i.next();
				if (resource.getType().compareTo(ResourceType.Button) == 0) {
					// 资源是按钮
					if (isExists(resourceIdArray, resource.getParent())) {
						if (flag) {
							resourceIds += resource.getId();
							flag = false;
						} else {
							resourceIds += "," + resource.getId();
						}
					}
				}
			}
		}
		role.getResourceSet().clear();
		if (!flag) {
			resourceIdArray = resourceIds.split(",");
			for (String resourceId : resourceIdArray) {
				log.info("分配的权限的ID ： " + resourceId);
				resource = resourceService.get(resourceId);
				log.info("添加了：" + resource.getText());
				role.getResourceSet().add(resource);
			}
		}
		roleService.update(role);
		return ajaxJsonAfterSubmitForm(true, "分配成功");
	}

	// 在数组中查询字符串是否存在
	private Boolean isExists(String[] container, String str) {
		for (int i = 0; i < container.length; i++) {
			if (str.equals(container[i])) {
				return true;
			}
		}
		return false;
	}

	// 将菜单从小到达排序
	private Resource[] sort(Resource[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = 0; j < array.length - i; j++) {
				if (array[j].getSort() > array[j + 1].getSort()) {
					Resource temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		return array;
	}

	// 查看该目录是否是属于该结点的
	private boolean isOwnerOfTheNode(Resource resource, String node) {
		boolean isOwner = (resource.getType().equals(ResourceType.TreeLeaf) || resource
				.getType().equals(ResourceType.TreeNode))
				&& (resource.getParent().equals(node));
		return isOwner;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRescType(String rescType) {
		this.rescType = rescType;
	}

	public String getRescType() {
		return rescType;
	}
}
