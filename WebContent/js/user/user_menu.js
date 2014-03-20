/**
 * @author 缘梦
 * @date 2012年9月9日
 * @class UserMenu
 * @extends Ext.tree.TreePanel
 * @description 菜单项模块
 */
UserMenu = Ext.extend(Ext.tree.TreePanel, {
	id : null,
	myid : null,
	stores : [],
	// 构造方法
	constructor : function(config) {
		this.id = config.id;
		this.myid = "mymenutree";
		UserMenu.superclass.constructor.call(this, {
					id : "mytree" + config.id,
					title : config.get("text"),
					autoScroll : true, // 自动添加滚动条
					enableAllCheck : true,
					border : false, // 不要边框
					tools : [{
								id : 'refresh', // 刷新按钮
								handler : this.onRefreshTreeNodes,
								scope : this
							}],
					// 树的加载器
					loader : new Ext.tree.TreeLoader({
								// tree数据的远程服务器地址,相当于proxy,每次请求会将node的id值传递给服务器
								dataUrl : "resource!findMenuTree.action"
							}),
					// 根节点
					root : new Ext.tree.AsyncTreeNode({
								expanded : true,
								text : config.get("text"),
								id : config.id
							}),
					rootVisible : false, // 显示根节点
					// 监听事件
					listeners : {
						"click" : {
							fn : this.onTreeNodeClick,
							scope : this
						}
						//根据菜单树叶子加载按钮资源
//						"load" : function() {
//							var me = this;
//							var nodes = me.root.childNodes;
//							var length = nodes.length;
//							var store;
//							var num = me.stores.length;
//							for (var i = 0; i < length; i++) {
//								// 获取id动态创建store
//								if (nodes[i].attributes.leaf == true) {
//									store = new Ext.data.JsonStore({
//												storeId : nodes[i].attributes.id + '_store',
//												url : "resource!findBtnList.action",
//												baseParams : {
//													node : nodes[i].attributes.id
//												},
//												root : 'list',
//												method : 'POST',
//												autoLoad : true,
//												fields : ['id', 'text', 'linkUrl', 'sort']
//											});
//									store.sort('sort', 'ASC');
//									me.stores[num++] = store;
//								}
//							}
//						}
					}
				});
	},

	/**
	 * 树的节点单击事件
	 * 
	 * @param {}
	 *            _node 节点
	 * @param {}
	 *            _e
	 */
	onTreeNodeClick : function(_node, _e) {
		// 如果当前节点为叶子节点
		if (_node.isLeaf()) {
			// 获取当前节点的值
			var _nodeText = _node.attributes.text;
			// 获取当前节点的连接
			var _nodeLink = _node.attributes.linkUrl;
			// 获取当前节点的id
			var _nodeId = _node.attributes.id;
			// 调用动态添加Tab页方法
			this.addTab(_nodeText, _nodeId, '_css', _nodeLink);
		} else {
			_node.toggle();
		}
	},

	/**
	 * 动态添加标签页
	 * 
	 * @param {}
	 *            _name 节点的text值
	 * @param {}
	 *            _id 节点id
	 * @param {}
	 *            _css css
	 * @param {}
	 *            _link 节点的链接
	 */
	addTab : function(_name, _id, _css, _link) {
		// 动态创建tab标签的id
		var tabId = "tab_" + _id;
		// 动态创建tab标签的标题
		var tabTitle = _name;
		// 动态获取tree的某个节点的链接
		var tabLink = _link;
		// 获取主tab组件
		var centerPanel = Ext.getCmp("MainTab");
		// 根据id获取centerPanel组件的直接子组件的引用
		var tab = centerPanel.getComponent(tabId);
		// 这个要传到load页去，很关键，以后的布局要靠它
		var subMainId = 'tab_' + _id + '_main';
		/**
		 * 如果可以获取到tab页,就不用再次添加tab页了 (获取不到时tab=undefined)
		 * (也就是说如果当前tab页已经渲染就不用再次添加该tab了)
		 */
		if (!tab) {
			// 动态创建tab
			var newTab = centerPanel.add(new Ext.Panel({
						id : tabId, // tab的唯一id
						title : tabTitle, // tab的标题
						// iconCls : "houfei-treeNodeLeafIcon", // 图片
						layout : 'fit', // 填充布局,它不会让load进来的东西改变大小
						border : false, // 无边框
						closable : true, // 有关闭选项卡按钮
						autoWidth : true,
						listeners : {
							// 侦听tab页被激活里触发的动作
							activate : this.onActiveTabSize,
							scope : this
						}
					}));
			centerPanel.setActiveTab(newTab); // 激活加入的tab页(将新创建的tab页获取焦点)
			newTab.load({
				url : tabLink, // 请求服务器的地址
				method : "post", // post请求方式
				params : {
					subMainId : subMainId
					// 这里是关键的一个参数，传给load页，布局的关键
				},
				scope : this, // 范围
				discardUrl : true, // 丢弃url
				nocache : true, // 不缓存
				text : "加载中,请稍候……",
				timeout : 3000, // 延时3秒
				scripts : true
					// 允许执行script
				});
		} else {
			// 激活已存在的tab页
			centerPanel.setActiveTab(tab);
		}
	},

	/**
	 * treePanel的刷新按钮事件
	 * 
	 * @param {}
	 *            _treePanel
	 */
	onRefreshTreeNodes : function(event, toolEl, panel) {
		Ext.getCmp(panel.id).root.reload();
	},

	// 激活TAB页时改变内部容器的大小
	onActiveTabSize : function() {
		// 获取当前活动的tab页的body元素的宽度 (不含任何框架元素)
		var w = Ext.getCmp('MainTab').getActiveTab().getInnerWidth();
		// 获取当前活动的tab页的body元素的高度 (不含任何框架元素)
		var h = Ext.getCmp('MainTab').getActiveTab().getInnerHeight();
		// 获取当活动的tab页的id
		var Atab = Ext.getCmp('MainTab').getActiveTab().id;

		// 获取组件
		var submain = Ext.getCmp(Atab + "_main");

		if (submain) {
			submain.setWidth(w);
			submain.setHeight(h);
		}
	}

});