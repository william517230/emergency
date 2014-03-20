/**
 * @author ZWZ
 * @class AuthorityAssignTabpanel
 * @extends Ext.TabPanel
 * @description 角色授权面板
 */
AuthorityBtnTabpanel = Ext.extend(Ext.TabPanel, {
			myid : null,
			constructor : function(config) {
				AuthorityBtnTabpanel.superclass.constructor.call(this, {
							id : 'authoritybtntabpanel',
							border : false,
							autoScroll : true,
							activeTab : 0,
							items : [{
								id : 'btnTab',
								title : '按钮列表',
								height : Ext.getCmp('MainTab').getActiveTab()
										.getInnerHeight(),
								items : [new AuthorityBtnFormpanel()],
								buttonAlign : 'center'
							}]
						});
			}
		});