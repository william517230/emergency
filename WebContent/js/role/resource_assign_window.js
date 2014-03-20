/**
 * @author 缘梦
 * @date 2012年9月12日
 * @class ResourceAssignWindow
 * @extends Ext.Window
 * @description 资源分配窗口
 */
ResourceAssignWindow = Ext.extend(Ext.Window, {
	myPanel : null,
	url : null,
	roleId : null, // 需要分配的用户的Id
	// 构造方法
	constructor : function(config) {
		this.url = config.url;
		this.myPanel = new ResourceAssignTreePanel({
			myid : "resourceAssignTreePanel",
			roleId : config.id
		});
		this.roleId = config.id;
		ResourceAssignWindow.superclass.constructor.call(this, {
			id : "resourceAssignWindow",
			title : '权限分配-----' + config.roleName,
			width : 320,
			height : 400,
			autoScroll : true,
			constrainHeader : true,
			items : [ this.myPanel ],
			buttonAlign : 'center',
			buttons : [ {
				text : '确定',
				handler : this.submitForm,
				scope : this
			}, {
				text : '取消',
				handler : this.onClose,
				scope : this
			} ],
			listeners : {
				'show' : function() { // 加载后显示加载整棵树
					this.myPanel.root.expand(true, true);

				}
			}
		});
	},

	onClose : function() {
		this.close();
	},

	submitForm : function() {
		Ext.Msg.confirm("温馨提示", "确定要给角色分配权限吗？", function(btn) {
			if (btn == "yes") {
				var checkedString = this.myPanel.getAllChecked();
				Ext.Ajax.request({
					url : this.url,
					"method" : "POST",
					params : {
						'resourceIds' : checkedString,
						'roleId' : this.roleId,
						'type' : 'menu'
					},
					success : function(form, action) {
						Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
						Ext.getCmp("resourceAssignWindow").close();
						roleCheckGridPanel.getStore().reload();
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('提示信息', '表单提交失败！');
					}
				});
			} else {
				return;
			}
		}, this);

	}
});