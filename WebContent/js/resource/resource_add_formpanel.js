/**
 * @author 缘梦
 * @date 2012年9月10日
 * @class ResourceAddFormPanel
 * @extends Ext.form.FormPanel
 * @description 资源添加表单
 */
ResourceAddFormPanel = Ext.extend(Ext.form.FormPanel, {
	rescTypeStore : null, // 资源类型数据源
	rescParentStore : null, // 资源父类数据源，根据传参获取不同的数据（树节点或者是树叶子，参数为btn 或者是menu）

	constructor : function(config) {
		Ext.apply(this, config);
		var data = {
			"list" : [{ // 资源类型选择数据
				type : '菜单面板 ',
				value : 0
			}, {
				type : '树结点 ',
				value : 1
			}, {
				type : '树叶子 ',
				value : 2
			}, {
				type : '按钮 ',
				value : 3
			}, {
				type : '其他 ',
				value : 4
			}]
		};
		this.rescTypeStore = new Ext.data.JsonStore({ // 资源类型数据源
			data : data,// 这里是用来本地测试调用
			root : "list", // json数据根节点
			fields : ['type', 'value']
		});
		this.rescParentStore = new Ext.data.JsonStore({ // 资源数据存储器
			storeId : "rescParentStore",
			url : "resource!findResourceParent.action",
			root : "list",
			fields : ["id", "linkUrl", {
						name : "sort",
						type : "int"
					}, {
						name : "leaf",
						type : "bool"
					}, "descn", "type", "parent", "text"]
		});
		ResourceAddFormPanel.superclass.constructor.call(this, {
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
								fieldLabel : "资源名称",
								name : 'resource.text',
								emptyText : "请输入正确的资源名称"
							}, {
								xtype : 'textfield',
								fieldLabel : "英文字段",
								name : 'resource.enName',
								allowBlank : true,
								emptyText : "请输入资源对应的英文字段"
							}, {
								xtype : 'textfield',
								fieldLabel : "对应链接",
								name : 'resource.linkUrl',
								allowBlank : true
							}, {
								xtype : 'numberfield',
								fieldLabel : "显示顺序",
								name : 'resource.sort',
								allowDecimals : false, // 不允许输入小数
								nanText : '请输入有效整数', // 无效数字提示
								allowNegative : false
							}, {
								xtype : 'combo',
								id : 'typeCombo',
								fieldLabel : "资源类型",
								forceSelection : true, // 要求输入值必须在列表中存在
								store : this.rescTypeStore,
								displayField : 'type',
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
										var combo = Ext.getCmp('rescCombo');
										combo.enable().reset();
										var store = combo.store;
										if (Ext.getCmp('typeCombo').getValue() == 3) {
											store.baseParams = {
												'type' : 'btn'
											};
										} else {
											store.baseParams = {
												'type' : 'menu'
											};
										}
									},
									scope : this
								}
							}, {
								xtype : 'combo',
								id : 'rescCombo',
								fieldLabel : "资源父类",
								store : this.rescParentStore,
								forceSelection : true, // 要求输入值必须在列表中存在
								displayField : 'text',
								editable : false,
								valueField : 'id',
								selectOnFocus : true,
								triggerAction : 'all',
								hiddenName : 'resource.parent',
								name : 'resource.parent',
								emptyText : "请选择资源父类",
								disabled : true,
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
							}, {
								xtype : 'textarea',
								name : 'resource.systemed',
								hidden : true,
								value : 'false',
								allowBlank : true
							}]
				});
	}
});