<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->
<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/list.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
<script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
</head>

<body>
	<div class="divMain">
		<div class="divTitle">
			<span style="margin-left: 150px;margin-right: 280px;">商品信息</span> 
			<span style="margin-left: 40px;margin-right: 38px;">金额</span> 
			<span style="margin-left: 50px;margin-right: 40px;">订单状态</span> 
			<span style="margin-left: 50px;margin-right: 50px;">操作</span>
		</div>
		<br />
		<table align="center" border="0" width="100%" cellpadding="0"
			cellspacing="0">

			<c:forEach items="${pageBean.list }" var="order">

				<tr class="tt">
					<td width="320px">订单号：<a
						href="<c:url value='/jsps/order/desc.jsp'/>">${order.oid }</a></td>
					<td width="200px">下单时间：${order.orderTime }</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr style="padding-top: 10px; padding-bottom: 10px;">
					<td colspan="2"><c:forEach items="${order.orderItems }"
							var="item">
							<a class="link2" href="<c:url value='/jsps/book/desc.jsp'/>">
								<img border="0" width="70" src="/goods/${item.image_b }" />
							</a>
						</c:forEach></td>
					<td width="115px"><span class="price_t">&yen;${order.total }</span></td>
					<td width="142px">
						<c:if test="${order.status==1 }">(等待付款)</c:if>
						<c:if test="${order.status==2 }">(准备发货)</c:if> 
						<c:if test="${order.status==3 }">(等待确认)</c:if> 
						<c:if test="${order.status==4 }">(交易成功)</c:if> 
						<c:if test="${order.status==5 }">(已取消)</c:if> 
							<!--  当order对象的 status = 1
							(准备发货) status = 2
							(等待确认) status = 3
							(交易成功) status = 4
							(已取消)  status = 5
 							--></td>
					<td>
						<a href="<c:url value='/jsps/order/desc.jsp'/>">查看</a><br />
						<c:if test="${order.status==1 }">
							<a href="<c:url value='/jsps/order/desc.jsp'/>">支付</a><br />
							<a href="<c:url value='/jsps/order/desc.jsp'/>">取消</a><br />
						</c:if> 
						<c:if test="${order.status==3 }">
							<a href="<c:url value='/jsps/order/desc.jsp'/>">确认收货</a><br />
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<%@include file="/jsps/pager/pager.jsp"%>
	</div>
</body>
</html>
