AuthorityAssignRescTreegrid = Ext.extend(Ext.ux.tree.TreeGrid, {
			roleId : null,
			constructor : function(config) {
				this.roleId = config;
				Ext.QuickTips.init();
				AuthorityAssignRescTreegrid.superclass.constructor.call(this, {
							id : 'authorityassignresctreegrid',
							// multiSelect : true,
							// collapsible : false,
							// rootVisible : false,
							// singleExpand : true,
							// autoScroll : true,
							// viewConfig : {
							// forceFit : true
							// },
							width : 600,
							height : 600,
							enableDD : true,
//							selModel : {
//								selType : 'checkboxmodel',
//								mode : 'single'
//							},
							tbar : [{
										id : 'refresh',
										text : '刷新',
										handler : this.reload,
										scope : this
									}, '-', {
										id : 'submit',
										text : '授权',
										handler : this.submit,
										scope : this
									}],
							columns : [{
//										xtype : 'tgcolumn',
										header : '模块名称',
										dataIndex : 'text',
										width : 300
									}, {
										header : '模块描述',
										dataIndex : 'descn',
										align : 'center',
										width : 300
									}],
							loader : new Ext.tree.TreeLoader({
										dataUrl : 'resource!findResourceTree.action'
									}),
							root : new Ext.tree.AsyncTreeNode({
										id : '0',
										checked : true,
										expanded : true
									}),
							listeners : {
								'show' : function() { // 加载后显示加载整棵树
									this.myPanel.root.expand(true, true);
								}
//								'load' : function() {
//									var root = this.getRootNode();
//									var forEach = function(node) {
//										node.eachChild(function(node) {
//											node.expand();
//											if(node.data.depth > 1) {
//												node.set('checked', false);
//												forEach(node);
//											}
//										})
//									}
//									forEach(root);
//								}
							}
						});
						console.log(this);
			},

			reload : function() {
				this.loader.dataUrl = 'resource!findResourceTree.action?roleId='
						+ this.roleId;
				this.root.reload();
			},

			submit : function() {
				var selected = this.findById('authoritybtnformpanel').getForm()
						.getValues(true);
				var roleId = Ext.getCmp('authorityassigntabpanel').roleId;
				var node = this.findById('authorityresctreepanel')
						.getSelectionModel().getSelectedNode();
				Ext.Ajax.request({
							url : 'resource!assignResource.action',
							"method" : "POST",
							params : {
								'resourceIds' : selected,
								'roleId' : roleId,
								'node' : node.id,
								'type' : 'btn'
							},
							success : function() {
								Ext.Msg.alert("温馨提示", "分配成功");
							},
							failure : function() {
								Ext.Msg.alert("温馨提示", "分配失败");
							}
						});
			}
		});