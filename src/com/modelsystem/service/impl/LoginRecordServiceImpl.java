package com.modelsystem.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.modelsystem.dao.LoginRecordDao;
import com.modelsystem.po.LoginRecord;
import com.modelsystem.service.LoginRecordService;

@Service
public class LoginRecordServiceImpl extends BaseServiceImpl<LoginRecord> implements LoginRecordService {
	@SuppressWarnings("unused")
	@Resource
	private LoginRecordDao loginRecordDao;
	
	@Resource
	public void setBaseDao(LoginRecordDao loginRecordDao) {
		super.setBaseDao(loginRecordDao);
	}
	
}
