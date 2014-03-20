/**
 * @author 缘梦
 * @date 2012年9月15日
 * @class UserAddFormPanel
 * @extends Ext.form.FormPanel
 * @description 用户添加表单
 */
UserAddFormPanel = Ext.extend(Ext.form.FormPanel, {
			sexStore : null, // 资源类型数据源
			constructor : function(config) {
				// 初始化信息提示功能
				Ext.QuickTips.init();
				// 统一指定错误信息提示浮动显示方式
				Ext.form.Field.prototype.msgTarget = 'side';
				Ext.apply(this, config);
				var data = {
					"list" : [{ // 资源类型选择数据
						sexType : '男 ',
						value : '男'
					}, {
						sexType : '女 ',
						value : "女"
					}]
				};
				this.sexStore = new Ext.data.JsonStore({ // 资源类型数据源
					data : data,// 这里是用来本地测试调用
					root : "list", // json数据根节点
					fields : ['sexType', 'value']
				});
				UserAddFormPanel.superclass.constructor.call(this, {
							id : config.myid,
							bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
							autoScroll : true,
							border : false,
							autoScroll : true,
							labelWidth : 70,
							defaults : {
								xtype : 'textfield',
								allowBlank : false, // 不允许为空
								anchor : "90%" // 本组件所占的长度，即是列宽columnWidth的值乘以anchor的值
							},
							items : [{
										xtype : 'textfield',
										name : 'user.id',
										hidden : true,
										hideLabel : true,
										allowBlank : true
									}, {
										fieldLabel : "用户名称",
										name : 'user.username',
										emptyText : "请输入用户姓名"
									}, {
										fieldLabel : "真实姓名",
										name : 'user.realname',
										emptyText : "请输入真实姓名"
									}, {
										xtype : 'combo',
										fieldLabel : "用户性别",
										forceSelection : true,
										store : this.sexStore,
										displayField : 'sexType',
										editable : false,
										valueField : 'value',
										selectOnFocus : true,
										triggerAction : 'all',
										mode : 'local',
										hiddenName : 'user.sex',
										emptyText : "请选择用户性别"
									}, {
										fieldLabel : '联系电话',
										name : 'user.phoneNumber',
										regex : /^[0-9]{0,12}$/,
										regexText : '联系电话格式错误',
										emptyText : "请输入联系电话"
									}, {
										fieldLabel : "联系地址",
										name : "user.address",
										emptyText : "请输入联系地址"
									}, {
										fieldLabel : '状态',
										cls : "backgroundColor:#DFE8F6;padding:10px;",
										xtype : 'radiogroup',
										allowBlank : false,
										anchor : "80%",
										blankText : '该项必选！',
										items : [{
													xtype : 'radio',
													boxLabel : "启用",
													name : "status",
													inputValue : 1,
													checked : true
												}, {
													xtype : 'radio',
													boxLabel : "禁用",
													name : "status",
													inputValue : 0
												}]
									}]
						});
			}
		});