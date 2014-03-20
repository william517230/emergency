/**
 * @author 缘梦
 * @date 2012年9月10日
 * @class DepartmenteAddFormPanel
 * @extends Ext.form.FormPanel
 * @description 部门添加表单
 */
DepartmenteAddFormPanel = Ext.extend(Ext.form.FormPanel, {
	departmentParentStore : null,
	
	constructor : function(config) {

		this.departmentParentStore = new Ext.data.JsonStore({
			storeId : "departmentParentStore",
			url : "department!findParent.action",
			fields : ["id", "text"]
		});
		DepartmenteAddFormPanel.superclass.constructor.call(this, {
			id : config.myid,
			bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
			autoScroll : true,
			border : false,
			labelWidth : 70,
			defaults : {
				allowBlank : false,
				anchor : "90%"	//本组件所占的长度，即是列宽columnWidth的值乘以anchor的值
			},
			items: [{
				xtype : 'textfield',
				fieldLabel : "部门名称",
				name : 'dept.text',
				emptyText : "请输入正确的部门名称"
			},{
				xtype : 'combo',
				fieldLabel : "所属部门",
				id : 'departmentParentCombo',
				forceSelection : true,
				store : this.departmentParentStore,   
                displayField : 'text',  
                editable : false,
                valueField : 'id',
                selectOnFocus : true,
                triggerAction : 'all',
                hiddenName : 'dept.parent',
				emptyText : "请选择部门所属"
			},{
					xtype : 'textarea',
					fieldLabel : "部门描述",
					name : 'dept.descn',
					allowBlank : true
			}]
		});
	}
});