/**
 * @author 缘梦
 * @date 2012年9月10日
 * @class ResourceEditFormPanel
 * @extends Ext.form.FormPanel
 * @description 资源添加表单
 */
ResourceEditFormPanel = Ext.extend(Ext.form.FormPanel, {
	myid : null,
	resourceTypeStore : null, // 资源类型数据源
	resourceParentStore : null, // 资源父类数据源
	rewrite : true,
	constructor : function(config) {
		this.myid = config;
		Ext.apply(this, config);
		var data = {
			"list" : [{ // 资源类型选择数据
				resourceType : '菜单面板 ',
				value : 0
			}, {
				resourceType : '树结点 ',
				value : 1
			}, {
				resourceType : '树叶子 ',
				value : 2
			}, {
				resourceType : '按钮',
				value : 3
			}, {
				resourceType : '其他 ',
				value : 4
			}]
		};
		this.resourceTypeStore = new Ext.data.JsonStore({ // 资源类型数据源
			data : data,// 这里是用来本地测试调用
			root : "list", // json数据根节点
			fields : ['resourceType', 'value']
		});
		this.resourceParentStore = new Ext.data.JsonStore({ // 资源数据存储器
			storeId : "resourceParentStore",
			url : "resource!findResourceParent.action",
			root : "list",
			autoLoad : true,
			fields : ["id", "linkUrl", {
						name : "sort",
						type : "int"
					}, {
						name : "leaf",
						type : "bool"
					}, "descn", "resourceType", "parent", "text"],
			listeners : {
				'beforeload' : function() {
					var value = Ext.getCmp("resourceEditWindow").myForm
							.findById("resourceIdEditCombo").getValue();
					if (value == 3) {
						this.baseParams = {
							'type' : 'btn'
						};
					} else {
						this.baseParams = {
							'type' : 'menu'
						};
					}
				},
				'load' : function(object) {
					var me = Ext.getCmp('resourceEditFormPanel');
					if (me.rewrite == true) {
						object.each(function(record) {
									value = Ext.getCmp("resourceEditWindow").myForm
											.findById("resourceParentEditCombo")
											.getValue();
									Ext.getCmp("resourceEditWindow").myForm
											.findById("resourceParentEditCombo")
											.setValue(value);
								});
						me.rewrite = false;
					}
				}
			}
		});
		ResourceEditFormPanel.superclass.constructor.call(this, {
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
								name : 'resource.id',
								hidden : true,
								hideLabel : true,
								allowBlank : false
							}, {
								xtype : 'textfield',
								name : 'resource.systemed',
								hidden : true,
								hideLabel : true,
								allowBlank : false
							}, {
								xtype : 'textfield',
								fieldLabel : "资源名称",
								name : 'resource.text',
								emptyText : "请输入正确的资源名称"
							}, {
								xtype : 'textfield',
								fieldLabel : "英文字段",
								name : 'resource.enName',
								emptyText : "请输入资源对应的英文字段"
							}, {
								xtype : 'textfield',
								fieldLabel : "对应链接",
								name : 'resource.linkUrl',
								allowBlank : true
								// 允许为空
						}	, {
								xtype : 'numberfield',
								fieldLabel : "显示顺序",
								name : 'resource.sort',
								allowDecimals : false, // 不允许输入小数
								nanText : '请输入有效整数', // 无效数字提示
								allowNegative : false
							}, {
								id : "resourceIdEditCombo",
								xtype : 'combo',
								fieldLabel : "资源类型",
								forceSelection : true, // 要求输入值必须在列表中存在
								store : this.resourceTypeStore,
								displayField : 'resourceType',
								editable : false,
								valueField : 'value',
								selectOnFocus : true,
								triggerAction : 'all',
								mode : 'local',
								hiddenName : 'type',
								emptyText : "请选择资源类型",
								listeners : {
									'select' : function() {
										// 根据选项加载资源负累选项
										var combo = Ext.getCmp('resourceParentEditCombo');
										combo.setValue('');
										var store = combo.store;
										if (Ext.getCmp('resourceIdEditCombo')
												.getValue() == 3) {
											store.baseParams = {
												'type' : 'btn'
											};
										} else {
											store.baseParams = {
												'type' : 'menu'
											};
										}
										store.load();
									},
									scope : this
								}
							}, {
								id : "resourceParentEditCombo",
								xtype : 'combo',
								fieldLabel : "资源父类",
								forceSelection : true, // 要求输入值必须在列表中存在
								store : this.resourceParentStore,
								displayField : 'text',
								editable : false,
								valueField : 'id',
								selectOnFocus : true,
								triggerAction : 'all',
								hiddenName : 'resource.parent',
								name : 'resource.parent',
								emptyText : "请选择资源父类",
								listeners : {
									beforequery : function(qe) {
										delete qe.combo.lastQuery;
									}
								}
							}, {
								xtype : 'textarea',
								fieldLabel : "资源描述",
								name : 'resource.descn',
								emptyText : "请输入对该资源的正确描述",
								allowBlank : true
							}]
				});
	}
});