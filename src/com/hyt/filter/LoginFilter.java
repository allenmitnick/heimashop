package com.hyt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.User;
import com.hyt.service.UserService;
import com.hyt.utils.CookieUtils;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		强转
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpServletResponse response2 = (HttpServletResponse) response;
		/*
		 * // 如果是登录页直接放行 发送登录表单 其他的等接下来处理 String servletPath =
		 * request2.getServletPath(); if(servletPath.endsWith(".user")) { String method
		 * = servletPath.substring(1, servletPath.length() - 5);
		 * if("loginUI".equals(method)) { chain.doFilter(request2, response2);
		 * System.out.println("loginfilter success"); return; } }
		 */
//		到这儿只有http://localhost:8080/WEB20/loginUI.user能进入 主界面都进不来
//		1用户登录信息
		User loginUser = (User) request2.getSession().getAttribute("loginUser");
//		如果已经登录，放行
		if (loginUser != null) {
			chain.doFilter(request2, response2);
			return;
		}
//		获得自动登录的cookie信息
		Cookie userCookie = CookieUtils.getCookie(request2.getCookies(), "autoLoginCookie");
//		判断自动登录的cookie是否存在 有cookie才能帮不然就空指针异常了
		System.out.println(userCookie);
		if (userCookie == null) {
			chain.doFilter(request2, response2);
			return;
		} else {

//		通过cookie中记录信息，查询用户
//		获得用户信息
			String[] u = userCookie.getValue().split("@");
			String username = u[0];
			String password = u[1];
			User user = new User(username, password);

//		执行登录
			UserService userService = new UserService();
			loginUser = userService.login(user);
//		如果没有查询  想帮没帮上 自己去办吧
			if (loginUser == null) {
				chain.doFilter(request2, response2);
				return;
			} else {
				request2.getSession().setAttribute("loginUser", loginUser);
				chain.doFilter(request2, response2);
				return;
			}
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
