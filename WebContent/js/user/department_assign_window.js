DepartmentAssignWindow = Ext.extend(Ext.Window, {
	mytree : null,
	userId : null,
	parentid : null,
	url : null,
	//构造方法
	constructor : function(config) {
		this.url = config.url;
		this.userId = config.id;
		this.parentid = config.parentid;
		this.mytree = new DepartmentAssignTreepanel({myid : "departmentassigntreepanel",id : config.id, userId : this.userId});
		RoleAssignWindow.superclass.constructor.call(this, {
			id : "departmentassignwindow",
			title : '用户部门分配------' + config.realname,
			width :300,
			height : 272,
			autoScroll : true,
			items : [this.mytree],
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
		var me = this;
		Ext.Msg.confirm("温馨提示", "确定要给用户分配部门吗？", function(btn){
			if (btn == "yes") {
				var checkedString = this.mytree.getAllChecked();
				Ext.Ajax.request({
					   url: this.url,
					   "method" : "POST",
					   params: {
						   userId: this.userId,
						   deptIds : checkedString
					   },
					   success: function() {
						   Ext.Msg.alert("温馨提示", "分配成功");
						   Ext.getCmp("departmentassignwindow").close();
						   Ext.getCmp(me.parentid).getStore().reload();
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