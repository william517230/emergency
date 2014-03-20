/**
 * 
 */
package com.modelsystem.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.modelsystem.bean.Pager;
import com.modelsystem.po.Department;
import com.modelsystem.po.Users;
import com.modelsystem.service.DepartmentService;
import com.modelsystem.service.UserService;
import com.modelsystem.vo.DepartmentVO;

/**
 * 部门Action
 * @Title: DepartmentAction.java 
 * @Description: 主要处理对部门的操作
 * @author	huangjj
 * @date 2012-9-7
 * @version V1.0
 */
public class DepartmentAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Department dept;
	private String node;
	private String oldParent;
	private String userId;
	private String deptIds;
	
	@Resource
	private UserService userService;

	@Resource
	private DepartmentService deptService;

	@SuppressWarnings("unchecked")
	public String findByPage() {
		Pager pager = deptService.findByPager(getPager());
		List<DepartmentVO> list = DepartmentVO.changeToVO(pager.getList());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", pager.getTotalCount());
		return ajaxJsonByListAndMap(list, jsonMap, null);
	}

	public String findParent() {
		List<Department> list = deptService.findAll();
		String[] filterNames = { "userSet" };
		if (node != null && !"".equals(node)) {
			List<Department> l = new ArrayList<Department>();
			for (Department d : list) {
				if (!node.equals(d.getId())) {
					l.add(d);
				}
			}
			return ajaxJsonByListDirecdt(l, filterNames);
		}
		return ajaxJsonByListDirecdt(list, filterNames);
	}

	public String findDeptTree() {
		if (userId == null || "".equals(userId)) {
			return ajaxJsonByListDirecdt(new ArrayList<Department>(), null);
		}
		String[] propertyArray = { "parent" };
		Object[] valuesArray = { node };
		List<Department> list = deptService.findAll(propertyArray, valuesArray);
		Set<Department> ds = userService.get(userId).getDepartmentSet();
		for(Department d : list) {
			if(ds.contains(d)) {
				d.setChecked(true);
			}
		}
		String[] filterNames = { "userSet" };
		return ajaxJsonByListDirecdt(list, filterNames);
	}

	public String save() {
		try {
			setParentLeaf();
			dept.setLeaf(true); // 新增部门默认为叶子，没有子部门
			deptService.save(dept);
			return ajaxJsonAfterSubmitForm(true, "保存成功！");
		} catch (Exception e) {
			return ajaxJsonAfterSubmitForm(false, "保存失败！");
		}
	}

	public String update() {
		try {
			if (!dept.getParent().equals(oldParent)) {
				// 判断添加的父节点是否为非叶子，否则更改为非叶子
				setParentLeaf();
				// 判断就结点是否只拥有一个孩子（当前被移走的），如果是则更改该结点为叶子
				if (deptService.getCounts(new String[] { "parent" }, new String[] { oldParent }) == 1) {
					// 没有孩子，修改为叶子
					Department d = deptService.get(oldParent);
					System.out.println("d.getText = " + d.getText());
					d.setLeaf(true);
					deptService.update(d);
				}
			}
			// 不管该结点下还有没有子树或者叶子节点，都转移到新的父节点上
			Department d = deptService.get(dept.getId());
			String[] ignoreProperties = new String[] { "id", "leaf", "userSet" };
			BeanUtils.copyProperties(dept, d, ignoreProperties);
			deptService.update(d);
			return ajaxJsonAfterSubmitForm(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonAfterSubmitForm(false, "修改失败！");
		}
	}

	public String delete() {
		if (deptService.delete(dept.getId())) {
			return ajaxJsonAfterSubmitForm(true, "成功删除！");
		}
		return ajaxJsonAfterSubmitForm(false, "删除失败！");
	}

	public String assignDept() {
		Boolean flag = false;
		String[] deptArray = null;
		if (deptIds == null || "".equals(deptIds)) {
			flag = true;
		}
		Users u = userService.get(userId);
		Set<Department> deptSet = u.getDepartmentSet();
		deptSet.clear();
		if(!flag) {
			deptArray = deptIds.split(",");
			for(String deptId : deptArray) {
				dept = deptService.get(deptId);
				deptSet.add(dept);
			}
		}
		userService.update(u);
		return ajaxJsonAfterSubmitForm(true, "分配成功");
	}
	
	
	/**
	 * 检测附加的父节点是否为叶子节点，如果是则改为树结点
	 * 
	 * @return true ：修改成功<br/>
	 *         false ：修改失败
	 */
	private Boolean setParentLeaf() {
		try {
			Department d = deptService.get(dept.getParent());
			if (d.getLeaf()) { // 附加的部门没有子部门，则先修改为非叶子再添加部门进去
				d.setLeaf(false);
				deptService.update(d);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Department getDept() {
		return dept;
	}

	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}

	public DepartmentService getDeptService() {
		return deptService;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getNode() {
		return node;
	}

	public void setOldParent(String oldParent) {
		this.oldParent = oldParent;
	}

	public String getOldParent() {
		return oldParent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptIds() {
		return deptIds;
	}

}
