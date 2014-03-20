/**
 * 
 */
package com.modelsystem.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.modelsystem.dao.DepartmentDao;
import com.modelsystem.po.Department;
import com.modelsystem.service.DepartmentService;

/**     
 * @class DepartmentServiceImpl   
 * @declare    
 * @author ZWZ   
 * @time 2014-1-7 上午10:33:52
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	@SuppressWarnings("unused")
	@Resource
	private DepartmentDao deptDao;
	
	@Resource
	public void setBaseDao(DepartmentDao deptDao) {
		super.setBaseDao(deptDao);
	}
}
