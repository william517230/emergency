DepartmentShowTreeWindow = Ext.extend(Ext.Window, {
			myid : null,
			btnStore : null,
			constructor : function(config) {
				DepartmentShowTreeWindow.superclass.constructor.call(this, {
					width : '300',
					items : [{
								id : 'Treewindow',
//								columnWidth : 0.5,
								height : Ext.getCmp('MainTab').getActiveTab()
										.getInnerHeight(),
								layout : 'fit',
								items : [new DepartmentShowTreepanel()]
							}]
				})
			}
		});