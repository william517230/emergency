/**
 * @author 缘梦
 * @date 2012年9月10日
 * @class RoleAddFormPanel
 * @extends Ext.form.FormPanel
 * @description 资源添加表单
 */
RoleAddFormPanel = Ext.extend(Ext.form.FormPanel, {

			constructor : function(config) {
				Ext.apply(this, config);

				RoleAddFormPanel.superclass.constructor.call(this, {
							id : config.myid,
							bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
							autoScroll : true,
							border : false,
							labelWidth : 70,
							defaults : {
								allowBlank : false, // 不允许为空
								anchor : "90%" // 本组件所占的长度，即是列宽columnWidth的值乘以anchor的值
							},
							items : [{
										xtype : 'textfield',
										name : 'role.id',
										hidden : true,
										hideLabel : true,
										allowBlank : true
									}, {
										xtype : 'textfield',
										fieldLabel : "角色名称",
										name : 'role.roleName',
										emptyText : "请输入正确的角色名称"
									}, {
										xtype : 'textarea',
										fieldLabel : "角色描述",
										name : 'role.roleDescription',
										allowBlank : true
								}]
						});
			}
		});