package com.huike.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest	request;
		HttpServletResponse	response;
		
		try {
		    request = (HttpServletRequest) req;
		    response = (HttpServletResponse) res;
		} catch (ClassCastException e) {
		    throw new ServletException("non-HTTP request or response");
		}
		
		request.setCharacterEncoding("utf-8") ;
		response.setContentType("text/html;charset=UTF-8");
		
		chain.doFilter(request, response) ;
	}

	@Override
	public void destroy() {

	}

}
