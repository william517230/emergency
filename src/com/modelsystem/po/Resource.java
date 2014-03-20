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
 * @class Resource
 * @declare
 * @author ZWZ
 * @time 2013-12-11 下午04:19:58
 */
@Entity
public class Resource implements java.io.Serializable {

	private static final long serialVersionUID = -9043216340772212378L;

	// 资源分类：树面板，树结点，树叶子,根节点,按钮, 其他, 检索信息
	public enum ResourceType {
		TreePanel, TreeNode, TreeLeaf, Root, Button, Other, Search
	}

	// Fields
	private String id;
	private Boolean leaf; // 是否为叶子节点
	private String text; // 名称
	private String enName;
	private Integer sort; // 排序
	private String parent; // 父节点
	private ResourceType type; // 资源类型
	private String linkUrl; // 链接URL
	private String descn; // 描述
	private Boolean systemed;

	private boolean checked;

	private Set<Role> roleSet = new HashSet<Role>();

	// Constructors

	/** default constructor */
	public Resource() {
	}

	public Resource(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	/** full constructor */
	public Resource(Boolean leaf, String text, Integer sort,
			String resourceParent, ResourceType resourceType, String linkUrl,
			String resourceDescription, String enName, Boolean systemed) {
		this.leaf = leaf;
		this.text = text;
		this.sort = sort;
		this.parent = resourceParent;
		this.type = resourceType;
		this.linkUrl = linkUrl;
		this.descn = resourceDescription;
		this.systemed = systemed;
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

	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnName() {
		return enName;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "type")
	public ResourceType getType() {
		return this.type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	@Column(name = "link_url")
	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "descn")
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Boolean getSystemed() {
		return this.systemed;
	}

	public void setSystemed(Boolean systemed) {
		this.systemed = systemed;
	}

	@Transient
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="role_resc",
			joinColumns={@JoinColumn(name="resc_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")}
	)
	public Set<Role> getRoleSet() {
		return roleSet;
	}
}