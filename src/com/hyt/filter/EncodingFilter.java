package com.hyt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class EncodingFilter implements	Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
//		1 强转
		HttpServletRequest request2 = (HttpServletRequest)request;
		HttpServletResponse response2 = (HttpServletResponse)response;
//		2 设置编码
		request2.setCharacterEncoding("UTF-8");
		response2.setCharacterEncoding("utf-8");
//		response2.setContentType("text/html;charset=utf-8");
//		3 创建自定义request
		
//		4 放行，使用自定义request
		chain.doFilter(request2, response2);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	


}
