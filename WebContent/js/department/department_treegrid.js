DepartmentTreeGrid = Ext.extend(Ext.ux.tree.TreeGrid, {
	
	constructor : function(config) {
		DepartmentTreeGrid.superclass.constructor.call(this, {
		        enableDD: true,
		        tbar : [{
		        	text : '部门增加',
		        	handler : this.departmentAdd,
		        	scope : this
		        },"-", {
		        	text : '部门编辑',
		        	handler : this.departmentEdit,
		        	scope : this
		        },"-", {
		        	text : '部门删除',
		        	handler : this.departmentDelete,
		        	scope : this
		        }],
		        columns:[{
		            header: '部门名称',
		            dataIndex: 'text',
		            width: 230
		        },{
		            header: '部门描述',
		            width: 350,
		            dataIndex: 'departmentDescription'
		        }],
		        dataUrl: 'department!findAllTreeNode.action'
		});
	},

	//部门增加
	departmentAdd : function() {
		new DepartmentAddWindow().show();
	},
	
	//部门编辑
	departmentEdit : function() {
		var checkedNode = this.getCheckedNode();
		var record = this.createNewRecord(checkedNode);
		var departmentEditWin = new  DepartmentEditWindow();
		departmentEditWin.show();
		departmentEditWin.myForm.getForm().loadRecord(record);	// 加载数据
	},
	
	//获取当前选中行结点
	getCheckedNode :  function() {
		var node = this.getSelectionModel().getSelectedNode();;
		if(node == null) {
			Ext.Msg.alert("温馨提示", "请选择您想要编辑的部门");
		}
		var record =  node.attributes;
		return record;
	},
	
	/**
	 * 创建一个新数据集 
	 * @param {} nowRecord 表格的当前选择行的record
	 */
	createNewRecord : function(nowRecord) {	
		var record = new Ext.data.Record({
					"department.id" : nowRecord.id,
					"department.text" : nowRecord.text,
					"node" : nowRecord.departmentParent,
					"department.departmentDescription" : nowRecord.departmentDescription
				});
		return record;
	},
	
	//部门删除
	departmentDelete : function() {
		var checkedNode = this.getCheckedNode();
		if(checkedNode == null) {
			Ext.Msg.alert("温馨提示", "请选择您要删除的部门");
		} 
		else {
			Ext.Msg.confirm("系统消息", "您确定要删除当前行的资源信息吗?", this.onDeleteConfrimBtnClick, this);
		}
	},
	
	/**
	 * Ext.confirm(删除Confirm窗体按钮)单击事件
	 * @param {} _btn
	 */
	onDeleteConfrimBtnClick : function(_btn) {
		if (_btn == "yes") {
			var checkedNode = this.getCheckedNode();
			if(checkedNode == null) {
				Ext.Msg.alert("温馨提示", "请选择您要删除的部门");
				return;
			} 
			checkedNode = this.getCheckedNode();
			var _pkid = checkedNode.id;
			// 进行异步Ajax请求
			Ext.Ajax.request({
						url : "department!delete.action",
						method : "post",
						success : function() {
							departmentTreeGrid.root.reload();	//刷新部门列表
						},
						failure : function() {
							Ext.Msg.alert("温馨提示", "删除错误……");
						},
						scope : this,
						params : {
							'id' : _pkid
						}
					});
		}
	}
});