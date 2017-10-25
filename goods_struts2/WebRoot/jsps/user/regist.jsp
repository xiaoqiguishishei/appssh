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
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jsps/css/user/regist.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/jsps/js/user/regist.js"></script>
	

  </head>
                                                                                                                                                                                                              
  <body>
  	<!-- DIV + CSS  -->
  	<div class="regist">
  		<div class="top">
  			<span>新用户注册</span>
  		</div>
  		<div class="registBody">
  			<form action="${pageContext.request.contextPath }/user/registerAction" method="post">
  				<!-- <input type="hidden" name="method" value="regist"> -->
  				<table>
  					<tr>
  						<td class="left">用户名:</td>
  						<td><input type="text" name="loginname" value="${error.loginname }"></td>
  						<td><span class="error" id="loginnameError"></span></td>
  					</tr>
  					<tr>
  						<td class="left" >登录密码:</td>
  						<td><input type="password" name="loginpass"></td>
  						<td><span class="error" id="loginpassError"></span></td>
  					</tr>
  					<tr>
  						<td class="left" >确认密码:</td>
  						<td><input type="password" name="reloginpass"></td>
  						<td><span class="error" id="reloginpassError"></span></td>
  					</tr>
  					<tr>
  						<td class="left" >Email:</td>
  						<td><input type="text" name="email" value=""></td>
  						<td><span class="error" id="emailError"></span></td>
  					</tr>
  					<tr>
  						<td class="left" >图形验证码:</td>
  						<td><input type="text" name="verifycode" value=""></td>
  						<td><span class="error" id="verifycodeError"></span></td>
  					</tr>
  					<tr>
  						<td class="left"></td>
  						<td><span class="verify"><img src="${pageContext.request.contextPath }/user/verifyCodeAction"></span></td>
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
