<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'regist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="jsps/css/user/regist.css">
	<script type="text/javascript" src="jquery/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="jsps/js/user/regist.js"></script>
	

  </head>
  
  <body>
  	<!-- DIV + CSS  -->
  	<div class="regist">
  		<div class="top">
  			<span>新用户注册</span>
  		</div>
  		<div class="registBody">
  			<form action="servlet/UserServlet?method=regist" method="post">
  				<input type="hidden" name="method" value="regist">
  				<table>
  					<tr>
  						<td class="left">用户名:</td>
  						<td><input type="text" name="loginname" value="${formBean.loginname }"></td>
  						<td><span class="error" id="loginnameError">${formBean.errors.loginname }</span></td>
  					</tr>
  					<tr>
  						<td class="left" >登录密码:</td>
  						<td><input type="password" name="loginpass" value="${formBean.loginpass }"></td>
  						<td><span class="error" id="loginpassError">${formBean.errors.loginpass }</span></td>
  					</tr>
  					<tr>
  						<td class="left" >确认密码:</td>
  						<td><input type="password" name="reloginpass" value="${formBean.reloginpass }"></td>
  						<td><span class="error" id="reloginpassError">${formBean.errors.reloginpass }</span></td>
  					</tr>
  					<tr>
  						<td class="left" >Email:</td>
  						<td><input type="text" name="email" value="${formBean.email }"></td>
  						<td><span class="error" id="emailError">${formBean.errors.email }</span></td>
  					</tr>
  					<tr>
  						<td class="left" >图形验证码:</td>
  						<td><input type="text" name="verifycode" value="${formBean.verifycode }"></td>
  						<td><span class="error" id="verifycodeError">${formBean.errors.verifycode }</span></td>
  					</tr>
  					<tr>
  						<td class="left"></td>
  						<td><span class="verify"><img src="servlet/VerifyCodeServlet"></span></td>
  						<td><a href="javascript:change()">换张图</a></td>
  					</tr>
  					<tr>
  						<td class="left"></td>
  						<td><input id="registBtn" type="image" src="images/regist1.jpg"></td>
  						<td></td>
  					</tr>  					  					  					  					  					
  				</table>
  			
  			</form>
  		</div>
  	</div>
  </body>
</html>
