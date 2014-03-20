/**
 * @author 缘梦
 * @date 2012年9月16日
 * @class RoleAssignFormPanel
 * @extends Ext.form.FormPanel
 * @description 角色分配面板
 */
RoleAssignFormPanel = Ext.extend(Ext.form.FormPanel,{
	roleStore : null,	//角色数据源
	currentRoleStore : null, //已选数据源
	currentRoleArray : new Array(String,String),
	
	constructor : function(config) {
		this.roleStore = new Ext.data.JsonStore({	//资源数据存储器
			storeId : "roleStore",
			url : "role!findAllRole.action",
			root : "list",
			autoLoad : true,
			baseParams : {
				id : config.id
			},
			fields : ["id", "roleName", {name : "checked", type : 'bool'}],
			listeners : {
		    	'load' : function(object){
		    	 	object.each(function(record) {
		    	 		 Ext.getCmp("roleFieldSet").add(new Ext.form.Checkbox ({
		    	 			fieldLabel : record.get("roleName"),
		    	 			id : record.get("id"),
		    	 			checked : record.get("checked"),
		    	 			boxLabel : record.get("roleName"),
		    	 			value : record.get("id")
		    	 		 }));	//根据数据加载菜单项
		    	 		 Ext.getCmp("roleFieldSet").doLayout();	//添加组件后必须进行的重新布局
		    	 	}); 
		    	}
			}
		});
		RoleAssignFormPanel.superclass.constructor.call(this, {
			items : [new Ext.form.FieldSet({
				id: "roleFieldSet",
				layout : "column",
				width :300,
				height : 202,
				baseCls : 'x-panel',
				columnWidth:60,
				itemCls : 'x-check-group-alt'

			})]
		});
	}
});