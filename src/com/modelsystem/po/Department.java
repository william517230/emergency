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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * @class Department   
 * @declare 部门类   
 * @author ZWZ   
 * @time 2014-1-7 下午04:45:20
 */
@Entity
@Table(name = "department")
public class Department implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String descn;
	private String text;
	private String parent;
	private Boolean leaf;
	private boolean checked;
	
	private Set<Users> userSet = new HashSet<Users>();

	public Department() {
	}
	
	public Department(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public Department(String text, Boolean leaf) {
		this.text = text;
		this.leaf = leaf;
	}

	public Department(String descn, String text,
			String parent, Boolean leaf) {
		this.descn = descn;
		this.text = text;
		this.parent = parent;
		this.leaf = leaf;
	}

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "descn")
	public String getDescn() {
		return descn;
	}

	@Column(name = "text", nullable = false, length = 32)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "parent", length = 32)
	public String getParent() {
		return parent;
	}

	@Column(name = "leaf", nullable = false)
	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
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
			name="user_dept",
			joinColumns={@JoinColumn(name="dept_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")}
	)
	public Set<Users> getUserSet() {
		return userSet;
	}
}