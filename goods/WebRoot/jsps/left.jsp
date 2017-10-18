<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    <base target="body"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
	
	<script language="javascript">
	
		var bar = new Q6MenuBar("bar", "网上书城");
	$(function() {
		bar.colorStyle = 4;	//颜色样式
		bar.config.imgDir = "<c:url value='/menu/img/'/>"; //加减号
		bar.config.radioButton=true;	//互斥  标题互斥展开
	
			  /*一级标题                  二级标题                                      点击跳到这个界面                                   界面 要显示的位置   */
		//bar.add("程序设计", "Java Javascript", "/goods/jsps/book/list.jsp", "body");
		
		<c:forEach items="${list }" var="parent">
			<c:forEach items="${parent.children }" var="child">
				bar.add("${parent.cname }", "${child.cname }", "/goods/servlet/BookServlet?method=findBooksByCid&cid=${child.cid}", "body");
			</c:forEach>
		</c:forEach>
			
		$("#menu").html(bar.toString());
		/* $("#menu").html("<h1>1111</h1>"); */
	});
	</script>
</head>
  
<body>  

  <div id="menu"></div>
</body>
</html>
