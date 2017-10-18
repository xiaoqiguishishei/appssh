function _change() {
	$("#vCode").attr("src", "/goods/servlet/VerifyCodeServlet?" + new Date().getTime());
}