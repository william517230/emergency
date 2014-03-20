/**
 * @author ZWZ
 * @class AuthorityAssignBtnPanel
 * @extends Ext.TabPanel
 * @description 角色授权面板
 */
AuthorityAssignBtnPanel = Ext.extend(Ext.Panel, {
			myid : null,
			btnStore : null,
			constructor : function(config) {
				AuthorityAssignBtnPanel.superclass.constructor.call(this, {
							id : 'authorityassignbtnpanel',
							layout : "column",
							tbar : [],
							items : [{
								id : 'rescTreepanel',
								columnWidth : 0.5,
								height : Ext.getCmp('MainTab').getActiveTab()
										.getInnerHeight(),
								layout : 'fit',
								items : [new AuthorityRescTreepanel()]
							}, {
								id : 'assignBtn',
								columnWidth : 0.5,
								height : Ext.getCmp('MainTab').getActiveTab()
										.getInnerHeight(),
								layout : 'fit',
								items : [new AuthorityBtnTabpanel()]
							}],
							listeners : {
								'afterrender' : function() {
									this.createBtn();
								}
							}
						});
			},

			createBtn : function() {
				var toolbar = this.getTopToolbar();
				me = this;
				if (this.btnStore == null) {
					store = new Ext.data.JsonStore({
								storeId : Ext.getCmp('authoritymultipanel').myid
										+ "_store",
								url : "resource!findBtnList.action",
								baseParams : {
									node : Ext.getCmp('authoritymultipanel').myid
								},
								root : 'list',
								method : 'POST',
								autoLoad : true,
								fields : ['id', 'text', 'linkUrl', 'sort'],
								listeners : {
									'load' : function(object) {
										object.each(function(record) {
													var id = record.get('id');
													var text = record
															.get('text');
													console.log(text)
													var url = record
															.get('linkUrl');
													if (text == '授权') {
														toolbar
																.addButton(me
																		.newBtn(
																				id,
																				text,
																				url,
																				'assign'));
													}
//													me.add(toolbar);
													me.doLayout(); // 添加组件后必须进行的重新布局
												});
									}
								}
							});
				}
			},

			newBtn : function(id, text, url, fn) {
				var me = this;
				return new Ext.Button({
							id : id,
							text : text,
							listeners : {
								'click' : function() {
									if (fn == 'assign') {
										me.submit(url);
									}
								}
							}
						});
			},

			submit : function(url) {
				var selected = this.findById('authoritybtnformpanel').getForm()
						.getValues(true);
				var roleId = Ext.getCmp('authorityassigntabpanel').roleId;
				var node = this.findById('authorityresctreepanel')
						.getSelectionModel().getSelectedNode();
				Ext.Ajax.request({
							'url' : url,
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