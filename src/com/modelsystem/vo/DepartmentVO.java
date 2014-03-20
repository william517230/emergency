/**
 * 
 */
package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.List;

import com.modelsystem.po.Department;

/**
 * @class DepartmentVO
 * @declare
 * @author ZWZ
 * @time 2014-1-7 下午01:32:48
 */
public class DepartmentVO {

	private String id;
	private String text;
	private Boolean leaf;
	private String parent;
	private String descn;

	public DepartmentVO() {

	}

	public DepartmentVO(String id, String text, Boolean leaf, String parent, String descn) {
		this.id = id;
		this.text = text;
		this.leaf = leaf;
		this.parent = parent;
		this.descn = descn;
	}

	public static DepartmentVO changeToVO(Department dept) {

		DepartmentVO deptVo = new DepartmentVO();
		deptVo.setId(dept.getId());
		deptVo.setLeaf(dept.getLeaf());
		deptVo.setText(dept.getText());
		deptVo.setParent(dept.getParent());
		deptVo.setDescn(dept.getDescn());
		return deptVo;
	}
	
	public static List<DepartmentVO> changeToVO(List<Department> deptList) {
		List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		for(Department dept : deptList) {
			deptVOList.add(changeToVO(dept));
		}
		return deptVOList;
	}

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

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getDescn() {
		return descn;
	}

}
