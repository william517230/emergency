/**
 * @author ZWZ
 * @class DepartmentCheckGridPanel
 * @extends Ext.grid.GridPanel
 */
DepartmentCheckGridPanel = Ext.extend(Ext.grid.GridPanel, {
			myid : "default",
			departmentStore : null,

			constructor : function(config) {
				this.myid = config.myid.substring(4, 36);
				this.departmentStore = new Ext.data.JsonStore({
					storeId : "departmentStore",
					url : "department!findByPage.action",
					root : "list",
					totalProperty : 'totalCount',
					fields : ["id", "text", "descn", "parent"]
				});
				Ext.StoreMgr.register(this.departmentStore);
				// 拷贝父类的构造方法
				DepartmentCheckGridPanel.superclass.constructor.call(this, {
							id : this.myid,
							title : "部门列表",
							stripeRows : true, // 交替行效果
							viewConfig : {
								forceFit : true
							},
							store : this.departmentStore,
							columns : [new Ext.grid.CheckboxSelectionModel({
										header : ""
									}), {
										header : "部门名称",
										width : 35,
										sortable : true,
										dataIndex : "text"
									}, {
										header : "部门描述",
										width : 80,
										sortable : true,
										dataIndex : "descn"
									}],
							selModel : new Ext.grid.RowSelectionModel({
										singleSelect : true
									}),
							loadMask : {
								msg : "正在加载数据,请稍候......"
							},
							bbar : new Ext.PagingToolbar({
								pageSize : 20,
								store : this.departmentStore,
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
									if (text == '删除部门') {
										toolbar.addButton(me.newBtn(record.get('id'), text, url, 'delete'));
									} else if (text == '添加部门') {
										toolbar.addButton(me.newBtn(record.get('id'), text, url, 'add'));
									} else if (text == '编辑部门') {
										toolbar.addButton(me.newBtn(record.get('id'), text, url, 'update'));
									}

									me.add(toolbar);
									Ext.getCmp(me.myid).doLayout();
								});
							}
						}
					});
				}
				toolbar.addButton(
					new Ext.Button({
						id : 'test',
						text : 'test',
						listeners : {
							'click' : function() {
								me.showTree();
							}
						}
					})
				);
				me.add(toolbar);
				Ext.getCmp(me.myid).doLayout();
				
			},

			newBtn : function(id, text, url, fn) {
				var me = this;
				return new Ext.Button({
							id : id,
							text : text,
							listeners : {
								'click' : function() {
									if (fn == 'delete') {
										me.deleteDepartmentConfirm(url);
									} else if (fn == 'add') {
										me.addDepartmentWin(url);
									} else if (fn == 'update') {
										me.checkDepartmentWin(url);
									}
								}
							}
						});
			},
			
			showTree : function() {
				new DepartmentShowTreeWindow().show();
			},

			// 增加部门
			addDepartmentWin : function(url) {
				var win = new DepartmentAddWindow();
				win.url = url;
				win.show();
			},

			// 编辑部门
			checkDepartmentWin : function(url) {
				var _sm = this.getSelectionModel();

				if (_sm.getCount() > 0) {
					var nowRecord = _sm.getSelected();
					var editWin = new DepartmentEditWindow();
					editWin.url = url;
					editWin.parentId = this.myid;
					editWin.oldParent = nowRecord.get('parent');
					editWin.myForm.getForm().reset();
					editWin.show();
					var record = this.createNewRecord(nowRecord); 
					editWin.myForm.getForm().loadRecord(record);
				} else {
					Ext.Msg.alert("温馨提示", "请您选择一条您要编辑的部门信息!");
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
							"dept.id" : nowRecord.get("id"),
							"dept.text" : nowRecord.get("text"),
							"dept.descn" : nowRecord.get("descn"),
							"dept.parent" : nowRecord.get("parent")
						});
				return record;
			},

			// 删除部门
			deleteDepartmentConfirm : function(url) {
				var _sm = this.getSelectionModel();
				if (_sm.getCount() > 0) {
					Ext.Msg.confirm("温馨提示", "此操作将级联删除关联该部门所有信息！<font color=red>请谨慎操作！</font><br/>您确定执行该操作?",
							function() {
								this.onDeleteConfrimBtnClick(_btn, url);	
							}, this);
				} else {
					Ext.Msg.show({
								title : "温馨提示",
								msg : "请您选择一条您要删除的部门信息",
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
				var me = this;
				if (_btn == "yes") {
					// 获取当前表格的选择模式
					var _sm = this.getSelectionModel();
					// 获取当前表格主键id
					var id = _sm.getSelected().get("id");
					// 进行异步Ajax请求
					Ext.Ajax.request({
								url : url,
								method : "post",
								params : {
									'department.id' : id
								},
								success : function() {
									me.getStore().reload();
								},
								failure : function() {
									Ext.Msg.alert("温馨提示", "删除错误……");
								},
								scope : this
							});
				}
			}

//			// 权限分配
//			assignResource : function() {
//				var _sm = this.getSelectionModel();
//				if (_sm.getCount() > 0) {
//					var departmentName = _sm.getSelected()
//							.get("departmentName");
//					var id = _sm.getSelected().get("id");
//					department = {
//						'departmentName' : departmentName,
//						'id' : id
//					}
//					new ResourceAssignWindow(department).show();
//				} else {
//					Ext.Msg.alert("温馨提示", "请选择一个需要权限分配的角色");
//				}
//
//			}

		});