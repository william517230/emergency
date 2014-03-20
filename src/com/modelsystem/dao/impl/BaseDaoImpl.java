package com.modelsystem.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.modelsystem.bean.Pager;
import com.modelsystem.bean.Pager.OrderType;
import com.modelsystem.dao.BaseDao;

/**
 * dao基类接口实现类
 * @Title: BaseDaoImpl.java 
 * @Description: 实现了一般情况下对实体的常用操作
 * @author	缘梦
 * @date 2012-10-5 下午8:21:21 
 * @version V1.0  
 * @param <T> 代表需要操作的具体对象
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	protected HibernateTemplate hibernateTemplate;
	private Class<T> entityClass;
	
	public HibernateTemplate geth() {
		return hibernateTemplate;
	}
	
	//保存
	public Serializable save(T entity) {
		try {
			Serializable id = hibernateTemplate.save(entity);
			return id;
		} catch (Exception e) {
			log.error(getEntityClass() + " [保存]异常信息  : ", e);
		}
		return null;
	}
	
	//根据Id获取对象
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		try {
			return (T) hibernateTemplate.get(getEntityClass(), id);
		} catch (Exception e) {
			log.error(getEntityClass() + " [获取]异常信息 ： ", e);
		}
		return null;
	}
	
	//根据Id获取对象
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		try {
			return (T) hibernateTemplate.load(getEntityClass(), id);
		} catch (Exception e) {
			log.error(getEntityClass() + " [获取]异常信息 ： ", e);
		}
		return null;
	}
	
	//修改对象信息 
	public boolean update(T entity) {
		try {
			hibernateTemplate.update(entity);
			return true;
		} catch (Exception e) {
			log.error(getEntityClass() + " [对象修改]异常信息 ： ", e);
		}
		return false;
	}
	
	//删除对象
	public boolean delete(T entity) {
		try {
			hibernateTemplate.delete(entity);
			return true;
		} catch (Exception e) {
			log.error(getEntityClass() + " [删除]异常信息 ： ", e);
		}
		return false;
	}
	
	//删除对象
	public boolean delete(Serializable id) {
		try {
			hibernateTemplate.delete(hibernateTemplate.load(getEntityClass(), id));
			return true;
		} catch (Exception e) {
			log.error(getEntityClass() + " [删除]异常信息 ： ", e);
		}
		return false;
	}
	
	//查询所有记录
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> list = null;
		try {
			list = hibernateTemplate.find("from " + getEntityClass().getName());
		} catch (Exception e) {
			log.error(getEntityClass() + " [查询所有记录]异常信息 ： ", e);
		}
		return list;
	}
	
	//根据属性名集合和属性值集合获取实体对象集合
	@SuppressWarnings("unchecked")
	public List<T> findAll(String[] propertyNameArray, Object[] valueArray) {
		List<T> list = null;
		String hql = "from " + getEntityClass().getName() + " as model where model." + propertyNameArray[0] + " = ?";
		
		for(int i = 1; i < propertyNameArray.length; i++) {
			hql = hql + " and  model." + propertyNameArray[i] + " = ?";
		}
		
		try {
			list = hibernateTemplate.find(hql, valueArray);
		} catch (Exception e) {
			log.error(getEntityClass() + " [根据属性名集合和属性值集合查询所有记录]异常信息 ： ", e);
		}
		return list;
	}
	
	//根据属性名和属性值集合获取实体对象集合，查询的是某属性值在一定范围内的对象的集合
		@SuppressWarnings({ "unchecked"})
		public List<T> findAll(String propertyName, List valueList) {
			List<T> list = null;
			String hql = "from " + getEntityClass().getName() + " as model where model." + propertyName + " in (:valueList)";
			try {
				list = getSession().createQuery(hql).setParameterList("valueList", valueList).list();
			} catch (Exception e) {
				log.error(getEntityClass() + " [查询某属性值在一定范围内的记录]异常信息 ： ", e);
			}   
			return list;
		}
	
	//获取数据库表中所有记录的计数
	public Integer getAllCounts() {
		int count = 0;
		try {
			String countString = getSession()
					.createQuery("select count(*) from " + getEntityClass().getName()).uniqueResult().toString();
			count = Integer.parseInt(countString);
		} catch (Exception e) {
			log.error(getEntityClass() + " [获取数据库表中所有记录的计数]异常信息 ： ", e);
		}
		return count;
		
	}
	
	//根据条件返回记录数
	@Deprecated
	public Integer getCounts(String[] params, String[] values) {
		int count = 0;
		String hql = "select count (*) from " + getEntityClass().getName() + " as model where model." + params[0] + " = '" + values[0] + "'";
		for(int i = 1; i < params.length; i++) {
			hql = hql + " and  model." + params[i] + " = '" + values[i] + "'";
		}
		String countString = getSession().createQuery(hql).uniqueResult().toString();
		count = Integer.parseInt(countString);
		return count;
	}
	
	//根据Pager对象进行查询(提供分页、查找、排序功能)
	public Pager findByPager(Pager pager) {
		if(pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getEntityClass());
		Integer pageNumber = pager.getPageNumber();	//当前页码
		Integer pageSize = pager.getPageSize();	//每页记录数
		String property = pager.getProperty();	//查找属性名称
		String keyword = pager.getKeyword();	//查询关键字
		String orderBy = pager.getOrderBy();	//排列字段
		OrderType orderType = pager.getOrderType();	//排列方法
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();	//获取总记录数
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);	//这是每页第一个记录的序号
		criteria.setMaxResults(pageSize);	//设置每页显示最大数
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		
		pager.setList(criteria.list());
		return pager;
	}
	
	/**
	 * 获取Hibernate当前所用的Session
	 * @return Hibernate当前所用的Session
	 */
	protected Session getSession(){
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	/**
	 * 获取泛型的实际类型
	 * @return 泛型的实际类型class
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		if (this.entityClass == null) {
			this.entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return this.entityClass;
	}
}
