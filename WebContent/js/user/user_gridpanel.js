/**
 * @author 缘梦
 * @date 2012年9月15日
 * @class UserGridPanel
 * @extends Ext.grid.GridPanel
 * @description 用户查看列表
 */
UserGridPanel = Ext.extend(Ext.grid.GridPanel, {
	myid : null,
	userStore : null,
	btnStore : null,
	// 构造方法
	constructor : function(config) {
		this.myid = config.myid.substring(4, 36);
		this.userStore = new Ext.data.JsonStore({ // 资源数据存储器
			storeId : "userStore",
			url : "user!findByPage.action",
			root : "list",
			totalProperty : 'totalCount',
			fields : ["id", "icon", "username", "realname", "password",
					"roles", "address", "sex", "status", "lastLoginIP",
					"phoneNumber"]
		});
		Ext.StoreMgr.register(this.userStore);
		// 拷贝父类的构造方法
		UserGridPanel.superclass.constructor.call(this, {
					id : this.myid,
					title : "人员列表",
					stripeRows : true, // 交替行效果
					viewConfig : {
						forceFit : true
					},
					store : this.userStore,
					columns : [new Ext.grid.CheckboxSelectionModel({
										header : ""
									}), {
								header : "用户名称",
								sortable : true,
								dataIndex : "username"
							}, {
								header : "真实姓名",
								sortable : true,
								dataIndex : "realname"
							}, {
								header : "性别",
								sortable : true,
								dataIndex : 'sex',
								renderer : function(value) {
									if (value == '男')
										return "<font color=blue>男</font>";
									else
										return "<font color=red>女</font>";
								}
							}, {
								header : "角色",
								sortable : true,
								dataIndex : 'roles'
							}, {
								header : "联系方式",
								sortable : true,
								dataIndex : 'phoneNumber'
							}, {
								header : "联系地址",
								sortable : true,
								dataIndex : 'address'
							}, {
								header : "状态",
								sortable : true,
								dataIndex : 'status',
								renderer : function(value) {
									if (value == false) {
										return "<font color = #FF0000>已禁用</font>";
									} else {
										return "<font color = #3ADF00>已启用</font>";
									}
								}
							}],
					selModel : new Ext.grid.RowSelectionModel({
								// 单行选择模式
								singleSelect : true
							}),
					// 填充加载时间
					loadMask : {
						msg : "正在加载数据,请稍候......"
					},
					bbar : new Ext.PagingToolbar({ // 底部分页工具栏
						pageSize : 20,
						store : this.userStore,
						displayInfo : true,
						displayMsg : '第 {0} - {1} 条  共 {2}条',
						emptyMsg : "没有记录"
					}),
					listeners : {
						'afterrender' : function() {
							this.createBtn();
						}
					}
				});

		// 加载数据
		this.getStore().load({
					params : {
						start : 0,
						limit : 20
					}
				});
	},

	createBtn : function() {
		var toolbar = new Ext.Toolbar({});
		me = this;
		if (this.btnStore == null) {
			store = new Ext.data.JsonStore({
						storeId : this.myid + '_store',
						url : "resource!findBtnList.action",
						baseParams : {
							node : this.myid
						},
						root : 'list',
						method : 'POST',
						autoLoad : true,
						sortInfo : {field : 'sort', direction : 'ASC'},
						fields : ['id', 'text', 'linkUrl', 'sort'],
						listeners : {
							'load' : function(object) {
								object.each(function(record) {
											var id = record.get('id');
											var text = record.get('text');
											var url = record.get('linkUrl');
											if (text == '删除用户') {
												toolbar.addButton(me.newBtn(id,
														text, url, 'delete'));
											} else if (text == '添加用户') {
												toolbar.addButton(me.newBtn(id,
														text, url, 'add'));
											} else if (text == '编辑用户') {
												toolbar.addButton(me.newBtn(id,
														text, url, 'update'));
											} else if (text == '分配角色') {
												toolbar.addButton(me.newBtn(id,
														text, url, 'assignRole'));
											} else if(text == '分配部门') {
												toolbar.addButton(me.newBtn(id,
														text, url, 'assignDept'));
											}
											me.add(toolbar);
											Ext.getCmp(me.myid).doLayout(); // 添加组件后必须进行的重新布局
										});
							}
						}
					});
		}
	},

	newBtn : function(id, text, url, fn) {
		var me = this;
		return new Ext.Button({
					id : id,
					text : text,
					listeners : {
						'click' : function() {
							if (fn == 'delete') {
								me.deleteUserConfirm(url);
							} else if (fn == 'add') {
								me.addUserWin(url);
							} else if (fn == 'update') {
								me.checkUserWin(url);
							} else if (fn == 'assignRole') {
								me.assignRole(url);
							} else if(fn == 'assignDept') {
								me.assignDepartment(url);
							}
						}
					}
				});
	},

	// 增加角色
	addUserWin : function(url) {
		var win = new UserAddWindow();
		win.url = url;
		win.show();
	},

	// 编辑资源
	checkUserWin : function(url) {
		var _sm = this.getSelectionModel(); // 获取当前表格行选择模式
		if (_sm.getCount() > 0) { // 如果选择了一行记录
			var nowRecord = _sm.getSelected(); // 获取表格当前选择行的record
			var editWin = new UserEditWindow();
			editWin.myForm.getForm().reset(); // 重置this.checkWin.myForm
			editWin.url = url;
			editWin.show(); // 显示窗体
			var record = this.createNewRecord(nowRecord); // 重构一个新的record
			editWin.myForm.getForm().loadRecord(record); // 加载数据

		} else {
			Ext.Msg.alert("温馨提示", "请您选择一条您要编辑的用户信息!");
			return;
		}
	},

	/**
	 * 创建一个新数据集
	 * 
	 * @param {}
	 *            _nowRecord 表格的当前选择行的record
	 */
	createNewRecord : function(nowRecord) {
		var status;
		if (nowRecord.get('status') == false) {
			status = 0;
		} else {
			status = 1;
		}
		var record = new Ext.data.Record({
					"user.id" : nowRecord.get("id"),
					"user.username" : nowRecord.get("username"),
					"user.realname" : nowRecord.get("realname"),
					"user.sex" : nowRecord.get("sex"),
					"user.phoneNumber" : nowRecord.get("phoneNumber"),
					"user.address" : nowRecord.get("address"),
					"status" : status
				});
		return record;
	},

	// 删除资源
	deleteUserConfirm : function(url) {
		var _sm = this.getSelectionModel(); // 获取当前选择模板
		if (_sm.getCount() > 0) {
			Ext.Msg.confirm("系统消息", "删除该用户将删除所有相关信息，<br>您确定要删除吗?", function(
							_btn) {
						this.onDeleteConfrimBtnClick(_btn, url);
					}, this);
		} else {
			Ext.Msg.show({
						title : "系统提示",
						msg : "请您选择一条您要删除的用户信息",
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.INFO
					});
		}
	},

	/**
	 * Ext.confirm(删除Confirm窗体按钮)单击事件
	 * 
	 * @param {}
	 *            _btn
	 */
	onDeleteConfrimBtnClick : function(_btn, url) {
		if (_btn == "yes") {
			// 获取当前表格的选择模式
			var _sm = this.getSelectionModel();
			// 获取当前表格主键id
			var _pkid = _sm.getSelected().get("id");
			// 进行异步Ajax请求
			Ext.Ajax.request({
						url : url,
						method : "post",
						success : function() {
							userGridPanel.getStore().reload(); // 刷新资源列表
						},
						failure : function() {
							Ext.Msg.alert("温馨提示", "删除错误……");
						},
						scope : this,
						params : {
							'user.id' : _pkid
						}
					});
		}
	},

	// 角色分配
	assignRole : function(url) {
		// 获取当前表格的选择模式
		var _sm = this.getSelectionModel();
		if (_sm.getCount() > 0) {
			// 获取当前表格主键id
			user = {
				'realname' : _sm.getSelected().get("realname"),
				'id' : _sm.getSelected().get("id"),
				'url' : url
			};
			new RoleAssignWindow(user).show();
		} else {
			Ext.Msg.alert("温馨提示", "请选择一个需要分配角色的用户");
		}

	},

	// 部门分配
	assignDepartment : function(url) {
		// 获取当前表格的选择模式
		var _sm = this.getSelectionModel();
		if (_sm.getCount() > 0) {
			// 获取当前表格主键id
			user = {
				'realname' : _sm.getSelected().get("realname"),
				'id' : _sm.getSelected().get("id"),
				'url' : url,
				'parentid' : this.myid
			};
			new DepartmentAssignWindow(user).show();
		} else {
			Ext.Msg.alert("温馨提示", "请选择一个需要分配部门的用户");
		}
	}

});