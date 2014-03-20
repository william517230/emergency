package com.modelsystem.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.modelsystem.bean.Pager;
import com.modelsystem.bean.Pager.OrderType;
import com.modelsystem.service.LoginRecordService;
import com.modelsystem.vo.LoginRecordVO;

/**
 * 操作日志Action
 * @Title: LoginRecordAction.java 
 * @Description: 主要用来处理对操作日志的查询
 * @author	huangjj
 * @date 2012-10-6 上午11:13:03 
 * @version V1.0
 */
public class LoginRecordAction extends BaseAction {

	private static final long serialVersionUID = -408821340281603214L;
	
	@Resource
	private LoginRecordService loginRecordService;
	
	//分页查询
	@SuppressWarnings("unchecked")
	public String findByPage() {
		Pager pager =new Pager();
		int limit = Integer.parseInt(getLimit()); 
		int start = Integer.parseInt(getStart());
		System.out.println("start = " + start + "\n" + "limit = " + limit);
		int pagerNumer = (start / limit) + 1;
		pager.setPageSize(limit);	pager.setPageNumber(pagerNumer);
		pager.setOrderBy("time");	pager.setOrderType(OrderType.desc);
		pager = loginRecordService.findByPager(pager);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalCount", pager.getTotalCount());
		List<LoginRecordVO> list = LoginRecordVO.changeToVO(pager.getList());
		return ajaxJsonByListAndMap(list, jsonMap, null);
	}
}
