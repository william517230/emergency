Ext.onReady(function() {
	
	Ext.getDom("username").focus();
	/**
	 * 键盘
	 */
	 document.onkeydown = function(e){
	        var ev = document.all ? window.event : e;
	        if (ev.keyCode == 13) {
	            checkLogin();
	        }
	 };
	 
	 /**
	  * 提交
	  */
	 var btn = Ext.get("login");
	 Ext.EventManager.on(btn, "click", checkLogin, true);
});

function checkLogin() {
	var username = Ext.getDom("username").value;
	var password = Ext.getDom("password").value;
	if(username == '' || password == ''){
		Ext.Msg.alert("温馨提示", "用户名及密码不能为空");
		return;
	}
	Ext.Ajax.request({  
		"url" : "user!login.action",
		"params" : {
			'username' : username,
			'password' : password
		},
		"method" : "POST",
		"callback" : function(options, success, response) {
			var responseText = Ext.util.JSON.decode(response.responseText); 
			if(responseText.success == true) {
				location.href = "pages/user_index.jsp";
			}
			else {
				Ext.Msg.alert("温馨提示", responseText.message);
				return;
			}
		}
	}); 
}