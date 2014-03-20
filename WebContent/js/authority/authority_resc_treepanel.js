/**
 * @author ZWZ
 * @class AuthorityRescTreepanel
 * @extends Ext.tree.TreePanel
 * @description 权限左部：角色树
 */
AuthorityRescTreepanel = Ext.extend(Ext.tree.TreePanel, {
			constructor : function(config) {
				AuthorityRescTreepanel.superclass.constructor.call(this, {
							id : "authorityresctreepanel",
							title : "模块列表",
							height : 500,
							autoScroll : true, // 自动添加滚动条
							bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
							border : false,
							footer : true,
							// 树的加载器
							loader : new Ext.tree.TreeLoader({
										dataUrl : "resource!findRescTree.action"
									}),
							// 根节点
							root : new Ext.tree.AsyncTreeNode({
										text : "模块",
										id : "0"
									}),
							tools : [{
										id : 'refresh', // 刷新按钮
										handler : this.reloadTree,
										scope : this
									}],
							// 监听事件
							listeners : {
								"click" : {
									fn : this.onTreeNodeClick,
									scope : this
								},
								'load' : function() {
									this.expandAll();
								}
							}
						});
			},

			reloadTree : function() {
				this.root.reload();
			},

			// 树节点单击事件，treeNode传送的是当前的事件
			onTreeNodeClick : function(_node, _e) {
				var formpanel = Ext.getCmp('authoritybtnformpanel');
				formpanel.btnStore.baseParams = {
					"roleId" : Ext.getCmp('authorityassigntabpanel').roleId,
					"node" : _node.id
				};
				formpanel.findById('authoritybtnfieldset').removeAll();
				formpanel.btnStore.load();
			}
		});