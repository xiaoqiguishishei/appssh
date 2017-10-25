

function change(){
	//找到切换验证码的图片
	
	$(".verify img").attr("src","/goods_struts2/user/verifyCodeAction?time="+new Date().getTime());
	
}                         

//当网页全部加载完成后就会调用$()
//将其包装成jQuery对象

$(function(){
	//获取到所有显示错误信息的span
	$(".error").each(function(){
		
		if($(this).text().length != 0)
		{
			//如果span中有内容,则显示错误
			$(this).css("visibility","visible");
		}else{
			
			$(this).css("visibility","hidden");
		}
	});
	
	//this是时间源, focus是谁, this就是谁
	//输入框被点击时,隐藏错误
	$("input[type != image]").focus(function (){
		//判断点击的输入框
		var name = $(this).attr("name");
		//隐藏错误
		var id = "#" + name + "Error";
		$(id).css("visibility","hidden");
		
	});
	
	//校验
	$("[name=loginname]").blur(function(){
		
		//this, 是"[name=loginname]
		
		var value = $(this).val();
		if(value.trim().length == 0) {
			//用户名不能为空
			$("#loginnameError").css("visibility","visible");
			$("#loginnameError").text("用户名不能为空");
		} else {
			$("#loginnameError").css("visibility","hidden");
			//发送ajax请求, 查询用户名是否已经注册
			$.ajax({
				url:"/goods_struts2/user/validateLoginnameAction",
				data:{method:"validateLoginname",loginname:value},
				type:"post",
				dataType:"json",
				success:function(result){
					console.log(result);
					if(!result) {
						$("#loginnameError").css("visibility","visible");
						$("#loginnameError").text("用户名已经被注册,请更换");	
					}
				}
			});
		}
	});
	
	//校验密码
	$("[name=loginpass]").blur(function(){
		
		var value = $(this).val();
		if(value.trim().length == 0) {
			//用户名不能为空
			$("#loginpassError").css("visibility","visible");
			$("#loginpassError").text("密码不能为空");
		} else {
			//$("#loginpassError").hide();
			$("#loginpassError").css("visibility","hidden");
		}
	});
	
	//校验确认密码
	$("[name=reloginpass]").blur(function(){
		
		//确认密码
		var value = $(this).val();
		//密码
		var psw = $("[name=loginpass]").val();
		
		if(value.trim().length == 0){
			//用户名不能为空
			$("#reloginpassError").css("visibility","visible");
			$("#reloginpassError").text("确认密码不能为空");
		}else if(value !=  psw){
			$("#reloginpassError").css("visibility","visible");
			$("#reloginpassError").text("两次密码不一致");
			
		}else{
			
		}
	});
	
	//校验email
	$("[name=email]").blur(function(){
		
		var value = $(this).val();
		if(value.trim().length == 0) {
			$("#emailError").css("visibility","visible");
			$("#emailError").text("邮件不能为空");
		} else {
			//$("#emailError").hide();
			$("#emailError").css("visibility","hidden");
		}
	});
	
	//校验验证码
	$("[name=verifycode]").blur(function(){
		
		var value = $(this).val();
		if(value.trim().length == 0) {
			$("#verifycodeError").css("visibility","visible");
			$("#verifycodeError").text("验证码不能为空");
		} else {
			//$("#verifycodeError").hide();
			//$("#verifycodeError").css("visibility","hidden");
			
			$.ajax({
				url:"${contextPage.request.contextPath}/user/registerAction",
				data:{method:"validateVerifycode", verifycode:value},
				type:"post",
				dataType:"json",
				success:function(result){
					console.log(result);
					if(!result) {
						$("#verifycodeError").css("visibility","visible");
						$("#verifycodeError").text("验证码错误");
					}
				}
			});
		}
	});
});
