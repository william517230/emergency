/**
 * @author 缘梦
 * @date 2012年9月15日
 * @class UserEditWindow
 * @extends Ext.Window
 * @description 资源添加窗口
 */
UserEditWindow = Ext.extend(Ext.Window, {
	myForm : null,
	url : null,
	//构造方法
	constructor : function(config) {
		this.myForm = new UserAddFormPanel({myid : "userEditFormPanel"});
		UserEditWindow.superclass.constructor.call(this, {
			id : "userEditWindow",
			title : '用户信息修改',
			width : 300,
			height : 260,
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
		this.close();
	},
	
	submitForm : function() {
	    if (this.myForm.getForm().isValid()) {
			this.myForm.getForm().submit({
				 waitTitle : "请稍候",  
	             waitMsg : "正在提交表单数据，请稍候.......",  
	             url : "user!update.action",
	             method : "POST",
	             success : function(form, action) {  
	            	 Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
	            	 Ext.getCmp("userEditWindow").close();
	            	 userGridPanel.getStore().reload();
		         },  
		         failure : function(form, action) {  
		           Ext.MessageBox.alert('提示信息', '表单提交失败！');  
		         }  
			});
	    } else {
	    	Ext.Msg.alert("温馨提示", "请检查填写表格");
	    }
	}
});