<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>班级管理</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="resources/css/jquery-ui-themes.css" type="text/css"
	rel="stylesheet">
<link href="resources/css/axure_rp_page.css" type="text/css"
	rel="stylesheet">
<link href="ClassManage_files/axurerp_pagespecificstyles.css"
	type="text/css" rel="stylesheet">
<!--[if IE 6]>
    <link href="ClassManage_files/axurerp_pagespecificstyles_ie6.css" type="text/css" rel="stylesheet">
<![endif]-->
<script src="data/sitemap.js"></script>
<script src="resources/scripts/jquery-1.7.1.min.js"></script>



<style type="text/css">
#t1,#t1 th,#t1 td {
	border: 1px solid gray;
}

#sel {
	width: 80px;
}

table td,table th {
	width: 12.5%;
	text-align: center;
}

select {
	width: 90px;
}
</style>
</head>
<script type="text/javascript">
	$(function() {

		//设置4个搜索文本框的样式
		$('input[class=one]').each(function() {
			$(this).width("90px");

			$(this).bind('focus', function() {
				$(this).val("");
			});

		});

		$('select[name=sel]')
				.each(
						function() {
							$(this)
									.bind(
											'change',
											function() {
												//获取选择框的内容
												var content = $(this).val();
												alert(content);

												//处理字符串
												var infos = content.split("-");

												//中文处理
												//infos = encodeURIComponent(infos) ;

												if ("请选择" != infos[1]) {
													$('#status').html(infos[1]);

													alert("${pageContext.request.contextPath}/admin/modifyCourseStatus?courseStatus="
															+ infos[1]
															+ "&cid="
															+ infos[0]);

													//向服务端发送ajax请求，修改数据库信息
													$
															.get(
																	"${pageContext.request.contextPath}/admin/modifyCourseStatus?courseStatus="
																			+ infos[1]
																			+ "&cid="
																			+ infos[0],
																	function(
																			data) {
																		alert(data.info);
																	}, "json");
												}
											});
						});

		//搜索功能
		$('#btnSearch')
				.click(
						function() {
							//获取6个文本框的值
							//var info =  $('#form').serialize() ;

							var className = $('#className').val();
							var classType = $('#classType').val();
							var teacher = $('#teacher').val();
							var lecturer = $('#lecturer').val();
							var courseStatus = $('#courseStatus').val();
							var startTime = $('#startTime').val();

							var info = "className=" + className
									+ "&classType.type=" + classType
									+ "&teacher.teacherName=" + teacher
									+ "&lecturer.lecturerName=" + lecturer
									+ "&courseStatus=" + courseStatus;

							//将数据发送到服务端
							window.location = "${pageContext.request.contextPath }/admin/searchClasses?"
									+ info;
						});
	});
</script>
<body>
	<form id="form">
		<table width="97%" id="t">
			<tr>
				<td width="100%"><input name="className" value="班级名称"
					class='one' id="className">&nbsp;&nbsp;&nbsp;</td>
				<td><input name="classType" value="班级类型" class='one'
					id="classType">&nbsp;&nbsp;&nbsp;</td>
				<td><input name="teacher" value="班主任" class='one' id="teacher">&nbsp;&nbsp;&nbsp;
				</td>
				<td><input name="lecturer" value="讲师" class='one' id="lecturer">&nbsp;&nbsp;&nbsp;
				</td>
				<td><input name="startTime" value="开班时间" class='one'
					id="startTime">&nbsp;&nbsp;&nbsp;</td>
				<td><input name="courseStatus" value="状态" class='one'
					id="courseStatus">&nbsp;&nbsp;&nbsp;</td>
				<td><input type="button" value="搜索" id="btnSearch">&nbsp;&nbsp;&nbsp;
				</td>
				<td><input type="button" value="添加" id="btnAdd">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<!-- 一次循环班级信息 -->
	<table width="97%" id="t1">
		<tr>
			<th>班级名称</th>
			<th>班级类型</th>
			<th>班主任</th>
			<th>讲师</th>
			<th>开班时间</th>
			<th>学习时长</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty page.list }">
				<c:forEach items="${page.list }" var="c">
					<tr>
						<td>${c.className }</td>
						<td>${c.classType.type }</td>
						<td>${c.teacher.teacherName }</td>
						<td>${c.lecturer.lecturerName }</td>
						<td><fmt:formatDate value="${c.startTime }"
								pattern="yyyy年MM月dd日" /></td>
						<td>${c.timeLength }月</td>
						<td id="status">${c.courseStatus }</td>
						<td><select name="sel">
								<option value="请选择">请选择</option>
								<option value="${c.cid }-未开课">未开课</option>
								<option value="${c.cid }-学习">学习</option>
								<option value="${c.cid }-结课">结课</option>
						</select></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="8" align = "center">没有你需要的数据</td>
				</tr>
			</c:otherwise>
		</c:choose>

	</table>
	<c:if test="${page.sumPage >1 }">
		<%@ include file="page.jsp"%>
	</c:if>
</body>