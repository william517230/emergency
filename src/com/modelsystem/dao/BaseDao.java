package com.modelsystem.dao;

import java.io.Serializable;
import java.util.List;

import com.modelsystem.bean.Pager;

/**
 * dao基类接口
 * @Title: BaseDao.java 
 * @Description: 实现了一般情况下对实体的常用操作
 * @author	缘梦
 * @date 2012-10-5 下午8:15:22 
 * @version V1.0  
 * @param <T> 泛型
 */
public interface BaseDao<T> {
	
	/**
	 * 保存
	 * @param entity 需要持久化的对象
	 * @return	保存成功 ： 对象持久化的Id; <br>
	 * 			保存失败 ： null
	 */
	public Serializable save(T entity);
	
	/**
	 * 根据Id获取对象
	 * @param id 对象的Id
	 * @return	获取成功 ： 根据Id获取的对象;  <br>
	 *          获取失败 ：  null
	 */
	public T get(Serializable id);
	
	/**
	 * 根据Id获取对象
	 * @param id 对象的Id
	 * @return	获取成功 ： 根据Id获取的对象;  <br>
	 *          获取失败 ： null
	 */
	public T load(Serializable id);
	
	/**
	 * 修改对象信息
	 * @param entity 需要修改信息的对象	
	 * @return 修改成功 ： true; 	<br>
	 * 	                   修改失败 ： false		
	 */
	public boolean update(T entity);
	
	/**
	 * 删除对象
	 * @param entity 需要删除的对象
	 * @return 删除成功 ： true;	<br>
	 * 		        删除失败 ： false
	 */
	public boolean delete(T entity);
	
	/**
	 * 根据Id删除对象
	 * @param id 需要删除的对象的Id
	 * @return   删除成功 ： true;	<br>
	 * 		             删除失败 ： false
	 */
	public boolean delete(Serializable id);
	
	/**
	 * 查询所有记录
	 * @return 查询成功 ： 对象数据库里的所有对象集合;	<br>
	 *         查询失败 ： null
	 */
	public List<T> findAll();
	
	/**
	 * 根据属性名集合和属性值集合获取实体对象集合
	 * @param propertyNameArray 属性名称集合
	 * @param valueArray	           属性值集合
	 * @return 查询成功 ： 对象数据库里的所有对象集合;	<br>
	 *         查询失败 ： null
	 */
	public List<T> findAll(String[] propertyNameArray, Object[] valueArray);
	
	/**
	 * 根据属性名和属性值数组获取实体对象集合.
	 * 查询的是某属性值在一定范围内的对象的集合
	 * @param propertyName 属性名称集合
	 * @param valueArray 属性值范围集合
	 * @return 查询成功 ： 对象数据库里的所有对象集合;	<br>
	 *         查询失败 ： null
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String propertyName, List valueList);
	
	/**
	 * 获取数据库表中所有记录的计数
	 * @return 查找结果总条数
	 */
	public Integer getAllCounts();
	
	/**
	 * 获取数据库表中符合条件的记录的计数
	 * @return 记录总条数
	 */
	public Integer getCounts(String[] params, String[] values);
	
	/**
	 * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager  Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager);
}
