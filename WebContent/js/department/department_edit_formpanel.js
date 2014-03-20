/**
 * @author 缘梦
 * @date 2012年9月10日
 * @class DepartmenteEditFormPanel
 * @extends Ext.form.FormPanel
 * @description 部门编辑表单
 */
DepartmenteEditFormPanel = Ext.extend(Ext.form.FormPanel, {
	departmentParentStore : null,	//资源父类数据源
	node : null,
	constructor : function(config) {
		this.node = config.node;
		this.departmentParentStore = new Ext.data.JsonStore({	//资源数据存储器
			storeId : "departmentParentStore",
			url : "department!findParent.action",
			baseParams : {
				node : this.node
			},
			fields : ["id", "text"],
			autoLoad : true,
	  	    listeners :{
		    	'load' : function(object){
		    		value = Ext.getCmp("departmentEditWindow").myForm.findById("departmentParentCombo").getValue();	
	    	 		Ext.getCmp("departmentEditWindow").myForm.findById("departmentParentCombo").setValue(value);
		    	}
		    }
		});
		DepartmenteEditFormPanel.superclass.constructor.call(this, {
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
				hidden : true,
				hideLabel : true,
				name : 'dept.id',
				allowBlank : true
			},{
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