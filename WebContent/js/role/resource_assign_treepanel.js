/**
 * @author 缘梦
 * @date 2012年9月14日
 * @class ResourceAssignTreePanel
 * @extends Ext.tree.TreePanel
 * @description 权限模块
 */
ResourceAssignTreePanel = Ext.extend(Ext.tree.TreePanel, {
	checkedArray : null,
	//构造方法
	constructor : function(config) {
		ResourceAssignTreePanel.superclass.constructor.call(this, {
			id : "mytree" + config.id,
//			title : config.get("text"),
			autoScroll : true, // 自动添加滚动条
			bodyStyle : "backgroundColor:#DFE8F6;padding:10px;",
			border : false, // 不要边框
			header : false,	//隐藏头部
			footer : false,	//隐藏底部
			tools : [{
						id : 'refresh', // 刷新按钮
						handler : this.onRefreshTreeNodes,
						scope : this
					}],
			// 树的加载器
			loader : new Ext.tree.TreeLoader({
						// tree数据的远程服务器地址,相当于proxy,每次请求会将node的id值传递给服务器
						dataUrl : "resource!findResourceTree.action",
						baseParams : {
							roleId : config.roleId
						}
					}),
			// 根节点
			root : new Ext.tree.AsyncTreeNode({
						text : "系统权限",
						checked : true,
//						iconCls : "houfei-treeRootIcon",
						id : "0"
					}),
			// 监听事件
			listeners : {
				"click" : {
					fn : this.onTreeNodeClick,
					scope : this
				},
				"checkchange" : {
					fn : this.onCheckChange,
					scope : this
				}
			}
		});
	},
	
	//树节点单击事件，treeNode传送的是当前的事件
	onTreeNodeClick : function(treeNode) {
		
	},
	
	//当节点的checkbox的状态被改变时触发的事件
	onCheckChange : function(node, checkedState) {   
		checkParentNode(node, checkedState);
		if(!node.isLeaf()) {	//如果不是叶子，则继续选择子节点
			checkChildNode(node, checkedState);
		}
    },
	
    //获取所有已经选择了的节点,返回所有已选结点的Id并用逗号隔开
	getAllChecked : function(CheckedNodes) {
		this.checkedArray = new Array;
		this.findAllChecked(this.root);
		return (this.checkedArray.join(","));
	},
    
	//遍历树获取所有已经选择了的结点的Id
    findAllChecked : function(nodes) {
    	for(var i = 0; i < nodes.childNodes.length; i++) {
    		if(nodes.childNodes[i].ui.isChecked()) {
    			this.checkedArray.push(nodes.childNodes[i].id);
    		}
    		if(!nodes.childNodes[i].isLeaf()){
    			this.findAllChecked(nodes.childNodes[i]);
    		}
    	}
    }

});

//父节点的级联选择
function checkParentNode(node, checkedState) {
	if(checkedState == true) {	//如果是选择，则级联选择父节点
		while(node) {	
			node.ui.checkbox.checked = checkedState;
			node.attributes.checked = checkedState;
			node = node.parentNode;
		}
		return;
	}
	
	if(checkedState == false) {	//如果是取消，则级联检查是否取消选择父节点
		var hasChildChecked = false;
		parentNode = node.parentNode;
		while(parentNode) {
			for(var i = 0; i < parentNode.childNodes.length; i++) {
				if(parentNode.childNodes[i].ui.isChecked() == true) {
					hasChildChecked = true;
				}
			}
			if(!hasChildChecked) {
				parentNode.ui.checkbox.checked = checkedState;
				parentNode.attributes.checked = checkedState;
				parentNode = parentNode.parentNode;
			} else {
				return;
			}
		}
	}
}

//子节点的级联选择
function checkChildNode(node, checkedState) {
	if(!node.isExpanded()) {
		node.expand(true, true, function(node) {
				toggleChildNode(node, checkedState);
		});
	} 
	else {
		toggleChildNode(node, checkedState);	
	}

}

function toggleChildNode(node, checkedState) {
	for(var i = 0; i < node.childNodes.length; i++) {
		 node.childNodes[i].ui.checkbox.checked = checkedState;
		 node.attributes.checked = checkedState;
		 if(!node.childNodes[i].isLeaf()) {
			 checkChildNode(node.childNodes[i], checkedState);
		 }
	}
}