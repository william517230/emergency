<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- 动态生成div,用来存放要被渲染的自定义组件 --%>
<div id=${param.subMainId}p style="width: 100%; height: 100%"></div>

<script type="text/javascript">
         //获取ajax请求参数subMainId
         var subMainId ='${param.subMainId}';
         // 获取当前活动的tab页的body元素的宽度 (不含任何框架元素)
	     var w = Ext.getCmp('MainTab').getActiveTab().getInnerWidth();
	     // 获取当前活动的tab页的body元素的高度 (不含任何框架元素)
	     var h = Ext.getCmp('MainTab').getActiveTab().getInnerHeight();
		 //实例化角色编辑面板
		 var roleCheckGridPanel = new RoleCheckGridPanel({myid:subMainId});
		 //设置宽度
		 roleCheckGridPanel.width = w;
		 //设置高度
		 roleCheckGridPanel.height = h;
		 //渲染组件
		 roleCheckGridPanel.render(subMainId+'p');
</script>