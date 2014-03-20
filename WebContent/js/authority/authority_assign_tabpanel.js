/**
 * @author ZWZ
 * @class AuthorityAssignTabpanel
 * @extends Ext.TabPanel
 * @description 角色授权面板
 */
AuthorityAssignTabpanel = Ext.extend(Ext.TabPanel, {
			roleId : null,
			myid : null,
			constructor : function(config) {
				AuthorityAssignTabpanel.superclass.constructor.call(this, {
							id : 'authorityassigntabpanel',
							border : false,
							autoScroll : true,
							activeTab : 0,
							items : [
//								{
//								id : 'assignMenu',
////								xtype : 'treepanel',
//								title : '模块授权',
//								height : Ext.getCmp('MainTab').getActiveTab()
//										.getInnerHeight(),
//								items : [new AuthorityAssignRescTreegrid(this.roleId)]
//							}, 
								{
								id : 'assignBtn',
								title : '按钮授权',
								xtype : 'panel',
								height : Ext.getCmp('MainTab').getActiveTab()
										.getInnerHeight(),
								items : [new AuthorityAssignBtnPanel()]
							}],
							listeners : {
								'tabchange' : function() {
									if (this.activeTab.title == '模块授权') {

									} else if (this.activeTab.title == '按钮授权') {
										var treepanel = Ext
												.getCmp('authorityresctreepanel');
										treepanel.getLoader().baseParams = {
											"roleId" : this.roleId
										};
										treepanel.reloadTree();
										Ext.getCmp('authoritybtnformpanel')
												.show();
									}
								}
							}
						});
			}
		});