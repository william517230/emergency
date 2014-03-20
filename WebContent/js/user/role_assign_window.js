/**
 * @author 缘梦
 * @date 2012年9月16日
 * @class RoleAssignWindow
 * @extends Ext.Window
 * @description 角色分配窗口
 */
RoleAssignWindow = Ext.extend(Ext.Window, {
	myForm : null,
	userId : null,
	url : null,
	//构造方法
	constructor : function(config) {
		this.url = config.url;
		this.userId = config.id;
		this.myForm = new RoleAssignFormPanel({myid : "roleAssignFormPanel",id : config.id});
		RoleAssignWindow.superclass.constructor.call(this, {
			id : "roleAssignWindow",
			title : '用户角色分配------' + config.realname,
			width :300,
			height : 272,
			autoScroll : true,
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
		Ext.Msg.confirm("温馨提示", "确定要给用户分配角色吗？", function(btn){
			if (btn == "yes") {
				var selected = this.myForm.getForm().getValues(true);
				Ext.Ajax.request({
					   url: this.url,
					   "method" : "POST",
					   params: {
						   id: this.userId,
						   idsString : selected
					   },
					   success: function() {
						   Ext.Msg.alert("温馨提示", "分配成功");
						   Ext.getCmp("roleAssignWindow").close();
						   userGridPanel.getStore().reload();
					   },
					   failure: function() {
						   Ext.Msg.alert("温馨提示", "分配失败");
					   }
				});
			}
			else {
				return;
			}
		}, this);
	}
});