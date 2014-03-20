package com.modelsystem.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @class LoginRecord
 * @declare
 * @author ZWZ
 * @time 2013-12-11 下午04:14:28
 */
@Entity
@Table(name="loginRecord")
public class LoginRecord {
	private String id; // 日志Id
	private String ip; // 用户登陆Ip
	private Date time; // 用户登陆时间
	private String reason; // 用户登陆缘由
	private Users user;

	public LoginRecord() {
	}

	public LoginRecord(String id, String ip, Date time, String reason, Users user) {
		super();
		this.id = id;
		this.ip = ip;
		this.time = time;
		this.reason = reason;
		this.user = user;
	}

	/***********************************************
	 * GETTER AND SETTER METHOD /
	 ***********************************************/

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
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

	@Column(name="login_time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public Users getUser() {
		return user;
	}
}
