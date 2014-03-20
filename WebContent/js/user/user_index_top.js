UserIndexTop = Ext.extend(Ext.Panel, {
	config : null,
	id:'loginid',
	constructor : function(config) {
		this.config = config;
		UserIndexTop.superclass.constructor.call(this, {
			region : "north", // 北部布局
			xtype : "panel", // 指定容器为panel
			bbar : ["->", {xtype : 'label' ,html :'欢迎你，<font color="red">' +this.config.username + "</font>"},
			        "-", {text : '修改密码', handler : this.changePassword, scope : this},
			        "-", {text : '个人资料', handler : this.personalInfo, scope : this},
			        "-", {text : '安全退出', handler : this.exit, scope : this}],
			border : false, // 不要边框
			baseCls : "x-plain"
		});
	},
	
	//用户退出
	exit : function() {
		Ext.Ajax.request({
			url : 'user!exit.action',
			method : 'POST',
			success : function() {
				window.location.href = 'user_login.jsp';
			},
			failure : function() {
				Ext.Msg.alert("温馨提示","退出失败");
			}
		});
	},
	
	//密码修改
	changePassword : function() {
		new PSDChangeWindow().show();
	},
	
	//个人信息
	personalInfo : function() {
		var personalStore = new Ext.data.JsonStore({	//资源数据存储器
			storeId : "userStore",
			url : "user!queryPersonalInfo.action",
			root : "list",
			fields : ["id", "username", "realname", "password", "roles", "address", 
			          "sex", "developer", "lastLoginIP", "phoneNumber"]
		});
		var userPersonalEditWin = new UserPersonalEditWindow().show();
		personalStore.load({"scope " : this,"callback" : function(record) {
			var newrecord = new Ext.data.Record({
				"user.id" : record[0].get("id"),
				"user.username" : record[0].get("username"),
				"user.realname" : record[0].get("realname"),
				"user.sex" : record[0].get("sex"),
				"user.phoneNumber" : record[0].get("phoneNumber"),
				"user.address" : record[0].get("address"),
				"user.developer" : record[0].get("developer")
			});
			userPersonalEditWin.myForm.getForm().loadRecord(newrecord);
		}});
	}

});