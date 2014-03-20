PSDChangeWindow = Ext.extend(Ext.Window, {
	myForm : null,
	constructor : function(config) {
		this.myForm = new Ext.FormPanel({
			bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
			defaults : {
				xtype : 'textfield',
				allowBlank : false, // 不允许为空
				anchor : "90%"	//本组件所占的长度，即是列宽columnWidth的值乘以anchor的值
			},
			items : [{fieldLabel : '新密码 ', name : 'password', inputType:'password', id : 'password'},
			         {fieldLabel : '密码确认', inputType:'password', name : 'password1', id : 'password2',
					   listeners : {
			            	'blur' : function(textfield) {
			            		var password2 = Ext.getCmp('password2').getValue();
			            		var password = Ext.getCmp('password').getValue();
			            		if(password != password2) {
			            			textfield.markInvalid();
			            			Ext.Msg.alert("温馨提示", "两次密码输入不一致，请重新输入");
			            		}
			            	}
			            }}]
		});
		PSDChangeWindow.superclass.constructor.call(this, {
			title : '密码修改',
			id : 'PSDChangeWindow',
			items :[this.myForm],
			width : 300,
			height : 150,
			layout : 'fit',
			buttonAlign : 'center',
			buttons : [{text : '确定', handler : this.onSubmit, scope : this},
			           {text : '取消', handler : this.onClose, scope : this}]
		});
	},

	onClose : function() {
		this.close();
	},
	
	onSubmit : function() {
		if(this.myForm.getForm().isValid()) {
			Ext.Msg.confirm("温馨提示", "确认要修改密码吗？", function(btn){
				if(btn == 'yes') {
					this.myForm.getForm().submit({
						 waitTitle : "请稍候",  
			             waitMsg : "正在提交表单数据，请稍候.......",  
			             url : "user!changePassword.action",
			             method : "POST",
			             success : function(form, action) {  
			            	 Ext.MessageBox.alert('提示信息', '数据已经成功提交！');
			            	 Ext.getCmp("PSDChangeWindow").close();
				         },  
				         failure : function(form, action) {  
				             Ext.MessageBox.alert('提交失败', "保存失败");  
				         }  
					});
				}
			}, this);
		}
		else {
			Ext.Msg.alert("温馨提示", "请重新填写密码");
		}
	}
});