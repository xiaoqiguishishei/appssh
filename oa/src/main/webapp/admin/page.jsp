<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	function fun(){
		if("${page.currentPageIndex}" == 1){
			alert("当前已经是第一页了") ;
			return ;
		}
		window.location = "${pageContext.request.contextPath }/admin/page?currentPageIndex=${page.currentPageIndex -1 }&pageCount=5" ;
	}
	
	function fun1(){
		if("${page.currentPageIndex}" == "${page.sumPage}"){
			alert("当前已经是最后一页了") ;
			return ;
		}
		window.location = "${pageContext.request.contextPath }/admin/page?currentPageIndex=${page.currentPageIndex + 1 }&pageCount=5" ;
	}
</script>

<table width = "100%">
	<tr>
		<td width = "20%" align = "left">
			  第${page.currentPageIndex }页/共${page.sumPage }页
		</td>
		<td width = "40%">
			
		</td>
		<td width = "30%">
			<a href = "${pageContext.request.contextPath }/admin/page?currentPageIndex=1&pageCount=5">首页</a>&nbsp;&nbsp;
			<a href = "javascript:fun()">上一页</a>&nbsp;&nbsp;
			<a href = "javascript:fun1()">下一页</a>&nbsp;&nbsp;
			<a href = "${pageContext.request.contextPath }/admin/page?currentPageIndex=${page.sumPage }&pageCount=5">尾页</a>&nbsp;&nbsp;
		</td>
		<td width = "10%">
			<select id = "pageSel" width = "30">
				<c:forEach begin="1" end="${page.sumPage }" var="n">
					<option value = "${n }" ${page.currentPageIndex == n ? "selected":""}>第${n }页</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>