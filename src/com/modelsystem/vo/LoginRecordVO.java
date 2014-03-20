package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.modelsystem.po.LoginRecord;

public class LoginRecordVO {
	
	private String id;	//日志Id
	private String ip;	//用户登陆Ip
	private Date loginTime;	//用户登陆时间
	private String reason;	//用户登陆事件
	private String username;	//登陆用户名
	
	/**
	 * 将记录PO集合转换成VO集合
	 * @param loginRecordList 需要转换的记录集合
	 * @return 记录VO集合
	 */
	public static List<LoginRecordVO> changeToVO(List<LoginRecord> loginRecordList) {
		List<LoginRecordVO> loginRecordVOList = new ArrayList<LoginRecordVO>();
		for (LoginRecord loginRecord : loginRecordList) {
			LoginRecordVO loginRecordVO = new LoginRecordVO();
			loginRecordVO.setId(loginRecord.getId());
			loginRecordVO.setIp(loginRecord.getIp());
			loginRecordVO.setLoginTime(loginRecord.getTime());
			loginRecordVO.setReason(loginRecord.getReason());
			loginRecordVOList.add(loginRecordVO);
		}
		return loginRecordVOList;
	}
	
	/**
	 * 将记录PO转换成VO
	 * @param loginRecordList 需要转换的记录PO
	 * @return 记录VO
	 */
	public static LoginRecordVO changeToVO(LoginRecord loginRecord) {
		LoginRecordVO loginRecordVO = new LoginRecordVO();
		loginRecordVO.setId(loginRecord.getId());
		loginRecordVO.setIp(loginRecord.getIp());
		loginRecordVO.setLoginTime(loginRecord.getTime());
		loginRecordVO.setReason(loginRecord.getReason());
		return loginRecordVO;
	}
	
	/***********************************************/
	//			GETTER AND SETTER METHOD     	   //
	/***********************************************/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
