/**
 * @author ZWZ
 * @class AuthorityMultiPanel
 * @extends Ext.Panel
 * @description 多级面板，用于角色管理和授权
 */
AuthorityMultiPanel = Ext.extend(Ext.Panel, {
	myid : null,
	constructor : function(config) {
		Ext.apply(this, config);
		this.myid = config.myid.substring(4, 36);
		AuthorityMultiPanel.superclass.constructor.call(this, {
					id : 'authoritymultipanel',
					layout : "column",
					items : [{
								id : 'roleTreepanel',
								xtype : 'panel',
								columnWidth : 0.3,
								height : Ext.getCmp('MainTab').getActiveTab().getInnerHeight(),
								items : [ new AuthorityRoleTreepanel() ]

							}, {
								id : 'assignResource',
								xtype : 'panel',
								columnWidth : 0.7,
								height : Ext.getCmp('MainTab').getActiveTab().getInnerHeight(),
								items : [ new AuthorityAssignTabpanel() ]
							}]
				});
	}
});