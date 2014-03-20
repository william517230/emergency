package com.modelsystem.vo;

import java.util.ArrayList;
import java.util.List;

import com.modelsystem.po.Users;

public class AddUserTreeNodeVO {
	private String id;			//用户ID
	private String text;
	private boolean leaf;//用户名 
	private boolean checked;
	private String icon;
	/**
	 * 提供批量添加联系人的页面数据转化
	 * 将用户PO集合转换成VO集合
	 * @param loginRecordList 需要转换的用户集合
	 * @return 用户VO集合
	 */
	public static List<AddUserTreeNodeVO> changeToVO(List<Users> userList) {
		List<AddUserTreeNodeVO> userVOList = new ArrayList<AddUserTreeNodeVO>();
		for (Users user : userList) {
			AddUserTreeNodeVO userVO = new AddUserTreeNodeVO();
			userVO.setId(user.getId());
			userVO.setLeaf(true);
			userVOList.add(userVO);
		}
		return userVOList;
	}
	
	/**
	 * 将用户PO转换成VO
	 * @param loginRecordList 需要转换的用户PO
	 * @return 用户VO
	 */
	public static UserTreeNodeVO changeToVO(Users user) {
		UserTreeNodeVO userVO = new UserTreeNodeVO();
		userVO.setId(user.getId());
		userVO.setLeaf(true);
		return userVO;
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
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
