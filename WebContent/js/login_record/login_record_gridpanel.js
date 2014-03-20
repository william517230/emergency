LoginRecordGridPanel = Ext.extend(Ext.grid.GridPanel, {
	loginRecordStore : null,
	
	constructor : function(config) {
		this.loginRecordStore =  new Ext.data.JsonStore({	//数据源
			storeId : "loginRecord",
			url : "login_record!findByPage.action",	//调用action
			root : "list",	//json数据的根节点
			totalProperty : 'totalCount',	//总数量
			fields : ["id", "ip", "username", "reason",
			          {name : "loginTime", type : 'Date', mapping : 'loginTime.time', dateFormat : "time"}]
		});
		LoginRecordGridPanel.superclass.constructor.call(this, {
			title : "用户登陆日志列表",
			stripeRows : true, // 交替行效果
			viewConfig : {
				forceFit : true
			},
		/*	tbar : [{xtype:'label', text:'搜索类型 ：'},
			        {xtype : 'textfield',emptyText : '请输入搜索关键词'},"-",
			        {text : '搜索', handler : function(){Ext.Msg.alert("系统消息","开发中...");}}],*/
			store : this.loginRecordStore,
			columns : [
			           new Ext.grid.CheckboxSelectionModel({		// 列
							header : ""// 头
						}), {
			        	   	header : "用户名",
							sortable : true,
							dataIndex : "username"
						},{
							header : "IP地址",
							sortable : true,
							dataIndex : 'ip'
						},{
							header : "时间",
							sortable : true,
							dataIndex : 'loginTime',
							renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
						},{
							header : "事件",
							sortable : true,
							dataIndex : 'reason',
							renderer : function(value) {
								if(value == '用户退出') {
									return '<font color=red>' + value + '</font>';
								}
								return '<font color=green>' + value + '</font>';
							}
						}],
						selModel : new Ext.grid.RowSelectionModel({
							// 单行选择模式
							singleSelect : true
						}),
						loadMask : {
							msg : "正在加载数据,请稍候......"
						},
						bbar : new Ext.PagingToolbar({	//底部分页工具栏
							pageSize : 20,
							store : this.loginRecordStore,
							displayInfo : true,
							displayMsg : '第 {0} - {1} 条  共 {2}条',
							emptyMsg : "没有记录"
						})
		});
		
		// 加载数据
		this.getStore().load({
			params :{
				start: 0, limit: 20
			}
		});
	}
});