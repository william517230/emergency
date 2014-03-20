/**
 * @author ZWZ
 * @class AuthorityRoleTreepanel
 * @extends Ext.tree.TreePanel
 * @description 权限左部：角色树
 */
AuthorityRoleTreepanel = Ext.extend(Ext.tree.TreePanel, {
			constructor : function(config) {
				AuthorityRoleTreepanel.superclass.constructor.call(this, {
							id : "authorityroletreepanel",
							title : "角色",
							height : 500,
							autoScroll : true, // 自动添加滚动条
							bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
							border : false,
							footer : true,
							// 树的加载器
							loader : new Ext.tree.TreeLoader({
										dataUrl : "role!findRoleTree.action",
										autoload : true
									}),
							// 根节点
							root : new Ext.tree.AsyncTreeNode({
										expanded : true,
										text : "角色列表",
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
								}
							}
						});
			},

			reloadTree : function() {
				this.root.reload();
			},

			// 树节点单击事件，treeNode传送的是当前的事件
			onTreeNodeClick : function(_node, _e) {
				var tabpanel = Ext.getCmp('authorityassigntabpanel');
				tabpanel.roleId = _node.id;
				if(tabpanel.activeTab.title == '模块授权') {
					var treepanel = Ext.getCmp('authorityassignresctreegrid');
					treepanel.roleId = _node.id;
					treepanel.reload();
				} else if(tabpanel.activeTab.title == '按钮授权') {
					var treepanel = Ext.getCmp('authorityresctreepanel');
					treepanel.getLoader().baseParams = {"roleId" : _node.id};
					treepanel.reloadTree();
					//将第三个按钮面板重置为空
					Ext.getCmp('authoritybtnformpanel').findById('authoritybtnfieldset').removeAll();
				}
			}
		});
