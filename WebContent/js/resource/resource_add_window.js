/**
 * @author 缘梦
 * @date 2012年9月11日
 * @class ResourceAddWindow
 * @extends Ext.Window
 * @description 资源添加窗口
 */
ResourceAddWindow = Ext.extend(Ext.Window, {
	myForm : null,
	url : null,
	//构造方法
	constructor : function(config) {
		this.url = config;
		this.myForm = new ResourceAddFormPanel({myid : "resourceAddFormPanel"});
		ResourceAddWindow.superclass.constructor.call(this, {
			id : "resourceAddWindow",
			title : '资源添加',
			width : 400,
			height : 300,
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
	             url : this.url,
	             method : "POST",
	             success : function(form, action) {
	            	 Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
	            	 form.reset();	//form表单重置
	            	 Ext.getCmp("resourceAddWindow").close();//隐藏资源添加窗口
	            	 resourceCheckGridPanel.getStore().reload();	//刷新资源列表
		         },
		         failure : function(form, action) {  
		             Ext.MessageBox.alert('提交失败', action.result.errors);
		         }  
			});
	    } else {
	    	Ext.Msg.alert("温馨提示", "请检查填写表格");
	    }
	}
});