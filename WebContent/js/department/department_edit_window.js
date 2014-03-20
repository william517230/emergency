/**
 * @author 缘梦
 * @date 2012年9月11日
 * @class DepartmentAddWindow
 * @extends Ext.Window
 * @description 资源添加窗口
 */
DepartmentEditWindow = Ext.extend(Ext.Window, {
	myForm : null,
	url : null,
	parentId : null,
	oldParent : null,
	//构造方法
	constructor : function(config) {
		this.url = config;
		this.myForm = new DepartmenteEditFormPanel({myid : "departmenteEditFormPanel", node : this.oldParent});
		DepartmentEditWindow.superclass.constructor.call(this, {
			id : "departmentEditWindow",
			title : '部门编辑',
			width : 400,
			height : 205,
			items : [this.myForm],
			buttonAlign : 'center',
			buttons : [{
				text : '确定',
				handler : this.submitForm,
				scope : this
			}, {
				text : '取消',
				handler : this.onClose,
				scope : this
			}]
		});
	},

	onClose : function(){
		this.myForm.getForm().reset();
		this.close();
	},
	
	submitForm : function() {
		var me = this;
	    if (this.myForm.getForm().isValid()) {
			this.myForm.getForm().submit({
				 waitTitle : "请稍候",  
	             waitMsg : "正在提交表单数据，请稍候.......",  
	             url : this.url,
	             method : "POST",
	             params : {
	             	'oldParent' : this.oldParent
	             },
	             success : function(form, action) {  
	            	 Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
	            	 Ext.getCmp("departmentEditWindow").close();
	            	 Ext.getCmp(me.parentId).getStore().reload();
		         },  
		         failure : function(form, action) {  
		             Ext.MessageBox.alert('提交失败', '数据保存失败！'); 
		         }  
			});
	    } else {
	    	Ext.Msg.alert("温馨提示", "请检查填写表格");
	    }
	}
});