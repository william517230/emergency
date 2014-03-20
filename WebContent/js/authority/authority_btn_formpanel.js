/**
 * @author ZWZ
 * @class AuthorityBtnFormpanel
 * @extends Ext.form.FormPanel
 * @description 按钮分配面板
 */
AuthorityBtnFormpanel = Ext.extend(Ext.form.FormPanel, {
	btnStore : null, // 角色数据源
	currentBtnStore : null, // 已选数据源
	currentBtnArray : new Array(String, String),

	constructor : function(config) {
		this.btnStore = new Ext.data.JsonStore({ // 资源数据存储器
			storeId : "btnstore",
			url : "resource!findBtn.action",
			root : "list",
			autoLoad : false,
			fields : ["id", "text", {
						name : "checked",
						type : 'bool'
					}],
			listeners : {
				'load' : function(object) {
					object.each(function(record) {
								Ext.getCmp("authoritybtnfieldset")
										.add(new Ext.form.Checkbox({
													fieldLabel : record
															.get("text"),
													id : record.get("id"),
													checked : record
															.get("checked"),
													boxLabel : record
															.get("text"),
													value : record.get("id")
												})); // 根据数据加载菜单项
								Ext.getCmp("authoritybtnfieldset").doLayout(); // 添加组件后必须进行的重新布局
							});
				}
			}
		});

		AuthorityBtnFormpanel.superclass.constructor.call(this, {
					id : 'authoritybtnformpanel',
					items : [new Ext.form.FieldSet({
								id : "authoritybtnfieldset",
								layout : "column",
								height : 500,
//								width : 300,
								autoWidth : true,
								bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
								baseCls : 'x-panel',
								columnWidth : 50,
								itemCls : 'x-check-group-alt',
								listeners : {
									'show' : function() {
										console.log(this);
									}
								}
							})]

				});

	}

});