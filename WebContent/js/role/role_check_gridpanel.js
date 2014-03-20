/**
 * @author 缘梦
 * @date 2012年9月11日
 * @class RoleCheckGridPanel
 * @extends Ext.grid.GridPanel
 * @description 角色查看列表
 */
RoleCheckGridPanel = Ext.extend(Ext.grid.GridPanel, {
			myid : "default", // 唯一Id
			roleStore : null,// 资源存储器

			// 构造方法
			constructor : function(config) {
				this.myid = config.myid.substring(4, 36);
				this.roleStore = new Ext.data.JsonStore({ // 资源数据存储器
					storeId : "roleStore",
					url : "role!findByPage.action",
					root : "list",
					totalProperty : 'totalCount',
					fields : ["id", "roleName", "roleDescription", "sort"]
				});
				Ext.StoreMgr.register(this.roleStore);
				// 拷贝父类的构造方法
				RoleCheckGridPanel.superclass.constructor.call(this, {
							id : this.myid,
							title : "角色列表",
							stripeRows : true, // 交替行效果
							viewConfig : {
								forceFit : true
							},
							store : this.roleStore,
							columns : [new Ext.grid.CheckboxSelectionModel({ // 列
										header : ""// 头
									}), {
										header : "角色名称",
										width : 35,
										sortable : true,
										dataIndex : "roleName"
									}, {
										header : "角色描述",
										width : 80,
										sortable : true,
										dataIndex : "roleDescription"
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
								store : this.roleStore,
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
													var text = record
															.get('text');
													console.log(text)
													var url = record
															.get('linkUrl');
													if (text == '删除角色') {
														toolbar
																.addButton(me
																		.newBtn(
																				id,
																				text,
																				url,
																				'delete'));
													} else if (text == '添加角色') {
														toolbar.addButton(me
																.newBtn(id,
																		text,
																		url,
																		'add'));
													} else if (text == '编辑角色') {
														toolbar
																.addButton(me
																		.newBtn(
																				id,
																				text,
																				url,
																				'update'));
													} else if (text == '分配权限') {
														toolbar
																.addButton(me
																		.newBtn(
																				id,
																				text,
																				url,
																				'assign'));
													}
													me.add(toolbar);
													Ext.getCmp(me.myid)
															.doLayout(); // 添加组件后必须进行的重新布局
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
										me.deleteRoleConfirm(url);
									} else if (fn == 'add') {
										me.addRoleWin(url);
									} else if (fn == 'update') {
										me.checkRoleWin(url);
									} else if (fn == 'assign') {
										me.assignResource(url);
									}
								}
							}
						});
			},

			// 增加角色
			addRoleWin : function(url) {
				var win = new RoleAddWindow();
				win.url = url;
				win.show();
			},

			// 编辑资源
			checkRoleWin : function(url) {
				var _sm = this.getSelectionModel(); // 获取当前表格行选择模式

				if (_sm.getCount() > 0) { // 如果选择了一行记录
					var nowRecord = _sm.getSelected(); // 获取表格当前选择行的record
					var editWin = new RoleEditWindow();
					editWin.myForm.getForm().reset(); // 重置this.checkWin.myForm
					editWin.url = url;
					editWin.show(); // 显示窗体
					var record = this.createNewRecord(nowRecord); // 重构一个新的record
					editWin.myForm.getForm().loadRecord(record); // 加载数据

				} else {
					Ext.Msg.alert("温馨提示", "请您选择一条您要编辑的资源信息!");
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
				var record = new Ext.data.Record({
							"role.id" : nowRecord.get("id"),
							"role.roleName" : nowRecord.get("roleName"),
							"role.sort" : nowRecord.get("sort"),
							"role.roleDescription" : nowRecord
									.get("roleDescription")
						});
				return record;
			},

			// 删除资源
			deleteRoleConfirm : function(url) {
				var _sm = this.getSelectionModel(); // 获取当前选择模板
				if (_sm.getCount() > 0) {
					Ext.Msg.confirm("系统消息", "您确定要删除当前行的角色信息吗?", function(_btn) {
								this.onDeleteConfrimBtnClick(_btn, url);
							}, this);
				} else {
					Ext.Msg.show({
								title : "系统提示",
								msg : "请您选择一条您要删除的角色信息",
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
									roleCheckGridPanel.getStore().reload(); // 刷新资源列表
								},
								failure : function() {
									Ext.Msg.alert("温馨提示", "删除错误……");
								},
								scope : this,
								params : {
									'role.id' : _pkid
								}
							});
				}
			},

			// 权限分配
			assignResource : function(url) {
				// 获取当前表格的选择模式
				var _sm = this.getSelectionModel();
				if (_sm.getCount() > 0) {
					// 获取当前表格主键id
					var roleName = _sm.getSelected().get("roleName");
					var id = _sm.getSelected().get("id");
					role = {
						'roleName' : roleName,
						'id' : id,
						'url' : url
					}
					new ResourceAssignWindow(role).show();
				} else {
					Ext.Msg.alert("温馨提示", "请选择一个需要权限分配的角色");
				}
			}

		});