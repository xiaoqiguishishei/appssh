package com.huike.utils.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/*
 * 请求中的数据中文乱码的问题
 * 		1. get请求, 浏览器传过来的数据在请求行中
 * 				byte[] bs = request.getParameter("username").getBytes("iso-8859-1");
 * 				String username = new String(bs, "utf-8");
 * 		2. post请求, 浏览器传过来的数据在请求体中
 * 				req.setCharacterEncoding("utf-8");
 * 
 * 因为你get方法拿的中文是乱码, 我就按我自己的方式自己重写一个方法, 中文就不会乱码
 */
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	private String charset = "UTF-8";
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response ;
//		if(req.getMethod().equalsIgnoreCase("GET")) {//忽略大小写
//			
//			if(!(req instanceof GetRequest)) {
//				req = new GetRequest(req, charset);//处理get请求编码
//			}
//			
//		} else {
//			
//			req.setCharacterEncoding(charset);//处理post请求编码
//		}
//		/*
//		 * 在xxxServlet中获取到的对象是GetRequest类型的对象
//		 * doGet {
//		 * 
//		 * 		request.getParameter("username");
//		 * 
//		 * }
//		 */
		 MyRequest my = new MyRequest(req) ;
		//放行
		chain.doFilter(my, res);
	}
	
	private class MyRequest extends HttpServletRequestWrapper{
		
		private HttpServletRequest request ;
		
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request =request ;
		}

		@Override
		public String getParameter(String name) {
			//处理get方式的乱码
			String method = request.getMethod() ;
			
			if(method.equals("GET")){
				//获取原始数据
				String data = request.getParameter(name) ;
				
				try {
					String value = new String(data.getBytes("iso8859-1"),"utf-8") ;
					return value ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				try {
					request.setCharacterEncoding(charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return super.getParameter(name) ;
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		String charset = fConfig.getInitParameter("charset");//读取web.xml配置文件中的内容
		if(charset != null && !charset.isEmpty()) {
			this.charset = charset;
		}
	}
}
