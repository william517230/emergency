UserAddWindow = Ext.extend(Ext.Window, {
	myForm : null,
	//构造方法
	constructor : function(config) {
		this.myForm = new UserAddFormPanel({myid : "userAddFormPanel"});
		UserAddWindow.superclass.constructor.call(this, {
			id : "userAddWindow",
			title : '用户添加',
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
		this.myForm.getForm().reset();
		this.close();
	},
	
	submitForm : function() {
	    if (this.myForm.getForm().isValid()) {
			this.myForm.getForm().submit({
				 waitTitle : "请稍候",  
	             waitMsg : "正在提交表单数据，请稍候.......",  
	             url : "user!save.action",
	             method : "POST",
	             success : function(form, action) {  
	            	 Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
	            	 form.reset();	//form表单重置
	            	 Ext.getCmp("userAddWindow").close();
	            	 userGridPanel.getStore().reload();
		         },  
		         failure : function(form, action) {  
		             Ext.MessageBox.alert('提交失败', "保存失败");  
		         }  
			});
	    } else {
	    	Ext.Msg.alert("温馨提示", "请检查填写表格");
	    }
	}
});