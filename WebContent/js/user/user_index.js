/**
 * @author 缘梦
 * @date 2012年9月8日
 * @class UserIndex
 * @extends Ext.Viewport
 * @description 主页布局
 */
UserIndex = Ext.extend(Ext.Viewport, {
	user : null,
	//菜单栏加载数据源
	resourceStore : new Ext.data.JsonStore({
		storeId : "resourceStoreOfMenu",
	    url : 'resource!findResourcePanelList.action',	//获取资源的url
	    root : "list",	//json数据根节点
	    fields : ['id', 'resourceParent', 'linkUrl', 'text',	//字段映射
	             {name:'leaf', type: 'bool'}, 'resourceType'
	    ],
	    autoLoad : true,	//自动加载
	    listeners :{
	    	'load' : function(object){
	    	 	object.each(function(record) {
	    	 		 Ext.getCmp("menuPanel").add(new UserMenu(record));	//根据数据加载菜单项
	    	 		 Ext.getCmp("menuPanel").doLayout();	//添加组件后必须进行的重新布局
	    	 	}); 
	    	}
	    }
	}),

	// 中间区域
	center : new Ext.TabPanel({
		id : "MainTab",
		region : "center",
		activeTab : 0,
		autoScroll : true, // 自动添加滚动条
		height : 80,
		split : true, // 添加分割线
		collapsible : true,
//		plugins : new Ext.ux.TabCloseMenu(),
		items : {
			closeable : true,
			title : "首页"
		}
	}),
	
	west : new Ext.Panel({
		id : "menuPanel",
		region : "west",	//西部布局
		layout : "accordion",	// 手风琴布局
		title : "工作菜单",
		split : true,	// 添加分割线
		collapsible : true,	// 自动伸缩
		width : 200,	// 宽度
		layoutConfig : {
			animate : true	// 展开折叠是否有动画效果
		}
	}),

	// 构造方法
	constructor : function(config) {
		this.user = config;
		config = null;
		// 将父类的构造方法复制过来
		UserIndex.superclass.constructor.call(this, {
			layout : "border", // 指定布局为border布局
			items : [ {
				region : "north", // 北部布局
				xtype : "panel", // 指定容器为panel
				height : 84,
				bodyCssClass : "iconFirst",
				frame : false
			}, this.west, this.center, {
				region : 'south',
				xtype : 'panel',
				border : false,	// 不要边框
				frame : true,	// 强制背景色
				height : 40,
				/*html : "<div align=\"right\"><font color=\"#15428b\">" +
						"版权归<font color=red>中国移动</font>所有</font></div>",*/
				autoLoad : {url:'pages/user_index_top.jsp', scripts:true}
			}]
		});
	},

	// 面板展开事件
	onExpandPanel : function(_nowCmp) {
		// alert('展开之后触发');
		this.onActiveTabSize();
	},
	
	// 面板闭合事件
	onCollapsePanel : function(_nowCmp) {
		// alert('面板关闭后触发');
		this.onActiveTabSize();
	},
	
	//激活TAB页时改变内部容器的大小
	onActiveTabSize : function() {
		// 获取当前活动的tab页的body元素的宽度 (不含任何框架元素)
		var w = Ext.getCmp('MainTab').getActiveTab().getInnerWidth();
		// 获取当前活动的tab页的body元素的高度 (不含任何框架元素)
		var h = Ext.getCmp('MainTab').getActiveTab().getInnerHeight();
		// 获取当活动的tab页的id
		var Atab = Ext.getCmp('MainTab').getActiveTab().id;

		// 获取组件
		var submain = Ext.getCmp(Atab + "_main");

		if (submain) {
			submain.setWidth(w);
			submain.setHeight(h);
		}
	},
	
	//用户退出
	exit : function() {
		Ext.Ajax.request({
			url : 'user!exit',
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
			fields : ["id", "username", "realName", "password", "roles", "departments", 
			          "sex", "targetSale", "baseSalary",
			          {name : "joiningDate", type : 'Date', mapping : 'joiningDate.time', dateFormat : "time"}]
		});
		var userPersonalEditWin = new UserPersonalEditWindow().show();
		personalStore.load({"scope " : this,"callback" : function(record) {
			var newrecord = new Ext.data.Record({
				"user.id" : record[0].get("id"),
				"user.username" : record[0].get("username"),
				"user.realName" : record[0].get("realName"),
				"user.password" : record[0].get("password"),
				"password" : record[0].get("password"),
				"user.roles" : record[0].get("roles"),
				"user.sex" : record[0].get("sex")
			});
			userPersonalEditWin.myForm.getForm().loadRecord(newrecord);
		}});
		
		
	},
	
	/**
	 * 创建一个新数据集 
	 * @param {} _nowRecord 表格的当前选择行的record
	 */
	createNewRecord : function(nowRecord) {	
		return record;
	}
});