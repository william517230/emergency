package com.modelsystem.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.modelsystem.dao.UserDao;
import com.modelsystem.po.Users;

@Repository
public class UserDaoImpl extends BaseDaoImpl<Users> implements UserDao {

	
	// 获取没有被删除的用户
	public List<Users> queryExistUser(final int start, final int limit) {
		@SuppressWarnings("unchecked")
		List<Users> dataSet = hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from User u where u.status = ?").setBoolean(0, false);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
		});
		return dataSet;
	}
	
	//获取未删除用户的数量
	public int getExistUserCount(){
		return Integer.parseInt(getSession().createQuery("select count(*) from User u where u.status = ?" ).setBoolean(0, false).uniqueResult().toString());
	}
}
