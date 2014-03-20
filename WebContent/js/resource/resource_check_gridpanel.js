/**
 * @author 缘梦
 * @date 2012年9月11日
 * @class ResourceCheckGridPanel
 * @extends Ext.grid.GridPanel
 * @description 资源查看列表
 */
ResourceCheckGridPanel = Ext.extend(Ext.grid.GridPanel, {
			myid : "default", // 唯一Id
			resourceStore : null,// 资源存储器
			btnStore : null, // 当前页面权限按钮存储器

			// 构造方法
			constructor : function(config) {
				this.myid = config.myid.substring(4, 36);
				this.resourceStore = new Ext.data.JsonStore({ // 资源数据存储器
					storeId : "resourceStore",
					url : "resource!findByPage.action",
					root : "list",
					totalProperty : 'totalCount',
					fields : ["id", "linkUrl", 'enName', {
								name : "sort",
								type : "int"
							}, {
								name : "leaf",
								type : "bool"
							}, {
								name : "systemed",
								type : "bool"
							}, "descn", "type", "parent", "text"]
				});
				Ext.StoreMgr.register(this.resourceStore);
				var me = this;
				// 拷贝父类的构造方法
				ResourceCheckGridPanel.superclass.constructor.call(this, {
							id : this.myid,
							title : "资源列表",
							stripeRows : true, // 交替行效果
							viewConfig : {
								forceFit : true
							},
							store : this.resourceStore,
							columns : [new Ext.grid.CheckboxSelectionModel({ // 列
										header : ""// 头
									}), {
										header : "资源名称",
										sortable : true,
										dataIndex : "text"
									}, {
										header : "资源英文字段",
										sortable : true,
										dataIndex : "enName"
									}, {
										header : "资源类型",
										sortable : true,
										dataIndex : "type",
										renderer : function(value) {
											if (value == 'TreePanel') {
												return '菜单面板';
											} else if (value == "TreeNode") {
												return '树节点';
											} else if (value == "TreeLeaf") {
												return '树叶子';
											} else if (value == "Root") {
												return "根节点";
											} else if (value == "Button") {
												return "按钮";
											} else if (value == "Other") {
												return "其他";
											}
										}
									}, {
										header : "显示顺序",
										sortable : true,
										dataIndex : "sort"
									}, {
										header : "资源链接",
										sortable : true,
										dataIndex : "linkUrl"
									}, {
										header : "父节点",
										sortable : true,
										dataIndex : "parent",
										renderer : this.formatParentId
									}, {
										header : "是否系统资源",
										sortable : true,
										dataIndex : "systemed",
										renderer : function(value) {
											if (value == true) {
												return "<font color=red>是</font>";
											} else {
												return "<font color=green>否</font>";
											}
										}
									}],
							selModel : new Ext.grid.RowSelectionModel({
										// 单行选择模式
										singleSelect : true
									}),
							loadMask : {
								msg : "正在加载数据,请稍候......"
							},
							bbar : new Ext.PagingToolbar({ // 底部分页工具栏
								pageSize : 20,
								store : this.resourceStore,
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
													var url = record
															.get('linkUrl');
													if (text == '删除资源') {
														toolbar
																.addButton(me
																		.newBtn(
																				record
																						.get('id'),
																				text,
																				url,
																				'delete'));
													} else if (text == '添加资源') {
														toolbar
																.addButton(me
																		.newBtn(
																				record
																						.get('id'),
																				text,
																				url,
																				'add'));
													} else if (text == '编辑资源') {
														toolbar
																.addButton(me
																		.newBtn(
																				record
																						.get('id'),
																				text,
																				url,
																				'update'));
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
										me.deleteResourceConfirm(url);
									} else if (fn == 'add') {
										me.addResourceWin(url);
									} else if (fn == 'update') {
										me.checkResourceWin(url);
									}
								}
							}
						});
			},

			// 增加资源
			addResourceWin : function(url) {
				var win = new ResourceAddWindow();
				win.url = url;
				win.show();
			},

			// 编辑资源
			checkResourceWin : function(url) {
				var _sm = this.getSelectionModel(); // 获取当前表格行选择模式
				if (_sm.getCount() > 0) { // 如果选择了一行记录
					var nowRecord = _sm.getSelected(); // 获取表格当前选择行的record
					if (nowRecord.get("resourceType") == 'Root') {
						Ext.Msg.alert("系统消息", "该资源不允许修改");
						return;
					}
					var editWin = new ResourceEditWindow();
					editWin.url = url;
					var record = this.createNewRecord(nowRecord); // 重构一个新的record
					editWin.myForm.getForm().loadRecord(record); // 加载数据
					editWin.myForm.findById("resourceParentEditCombo")
							.setValue(record.get("resource.resourceParentId"));
					editWin.myForm.findById("resourceIdEditCombo")
							.setValue(record.get("resourceType"));
					editWin.show(); // 显示窗体
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
				var name = "";
				if (nowRecord.get("parent") == 0) {
					name = "无父类";
				} else {
					var store = Ext.StoreMgr.lookup("resourceStore");
					store.each(function(record) {
								if (record.get("id") == nowRecord.get("parent")) {
									name = record.get("text");
								}
							});
				}
				var resourceType = nowRecord.get("type");
				if (resourceType == 'TreePanel') {
					resourceType = '0';
				} else if (resourceType == "TreeNode") {
					resourceType = '1';
				} else if (resourceType == "TreeLeaf") {
					resourceType = '2';
				} else if (resourceType == "Button") {
					resourceType = '3';
				} else if (resourceType == "Other") {
					resourceType = '4';
				}
				var record = new Ext.data.Record({
							"resource.id" : nowRecord.get("id"),
							"resource.text" : nowRecord.get("text"),
							"resource.linkUrl" : nowRecord.get("linkUrl"),
							"resource.sort" : nowRecord.get("sort"),
							"resourceType" : resourceType,
							"resource.systemed" : nowRecord.get("systemed"),
							"resource.enName" : nowRecord.get("enName"),
							"resource.resourceParentId" : nowRecord
									.get("parent"),
							"resource.descn" : nowRecord.get("descn")
						});
				return record;
			},

			// 删除资源
			deleteResourceConfirm : function(url) {
				var _sm = this.getSelectionModel(); // 获取当前选择模板
				if (_sm.getCount() > 0) {
					var isSystem = _sm.getSelected().get("systemed");
					if (isSystem == true) {
						Ext.Msg.alert("系统消息", "系统资源不可删除！");
						return;
					}
					Ext.Msg.confirm("系统消息", "您确定要删除当前行的资源信息吗?", function(_btn) {
								this.onDeleteConfrimBtnClick(_btn, url);
							}, this);
				} else {
					Ext.Msg.show({
								title : "系统提示",
								msg : "请您选择一条您要删除的资源信息",
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
									resourceCheckGridPanel.getStore().reload();
								},
								failure : function() {
									Ext.Msg.alert("温馨提示", "删除错误……");
								},
								scope : this,
								params : {
									'resource.id' : _pkid
								}
							});
				}
			},

			// 根据情况更改父节点显示的样式.record传送的当前行的record记录
			formatParentId : function(value, cellmeta, record) {
				if (value == 0) {
					return "菜单面板";
				} else if (value == 1) {
					return "根节点";
				} else {
					var store = Ext.StoreMgr.lookup("resourceStore");
					var name = "";
					store.each(function(record) {
								if (record.get("id") == value) {
									name = record.get("text");
								}
							});
					return name;
				}
			}
		});