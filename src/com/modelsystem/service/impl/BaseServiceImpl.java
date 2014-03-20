package com.modelsystem.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.modelsystem.bean.Pager;
import com.modelsystem.dao.BaseDao;
import com.modelsystem.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{
	
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	// 获取HttpSession
	public HttpSession getHttpSession(){
		return getRequest().getSession();
	}

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	//保存
	public Serializable save(T entity) {
		return baseDao.save(entity);
	}

	//通过Id获取对象
	public T get(Serializable id) {
		return baseDao.get(id);
	}

	//通过Id获取对象
	public T load(Serializable id) {
		return baseDao.load(id);
	}

	//更新对象
	public boolean update(T entity) {
		return baseDao.update(entity);
	}

	//删除对象
	public boolean delete(T entity) {
		return baseDao.delete(entity);
	}

	//删除对象
	public boolean delete(Serializable id) {
		return baseDao.delete(id);
	}

	//查询所有记录
	public List<T> findAll() {
		return baseDao.findAll();
	}

	//根据属性名集合和属性值集合获取实体对象集合
	public List<T> findAll(String[] propertyNameArray, Object[] valueArray) {
		return baseDao.findAll(propertyNameArray, valueArray);
	}
	
	//根据属性名和属性值数组获取实体对象集合, 查询的是某属性值在一定范围内的对象的集合
	@SuppressWarnings("unchecked")
	public List<T> findAll(String propertyName, List valueList) {
		return baseDao.findAll(propertyName, valueList);
	}

	//获取数据库记录总数
	public Integer getAllCounts() {
		return baseDao.getAllCounts();
	}

	//获取数据库符合条件的记录总数
	public Integer getCounts(String[] params, String[] values) {
		return baseDao.getCounts(params, values);
	}

	//分页查询记录
	public Pager findByPager(Pager pager) {
		return baseDao.findByPager(pager);
	}
}
