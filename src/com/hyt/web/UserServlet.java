package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hyt.domain.User;
import com.hyt.service.UserService;

/**
 * Servlet implementation class UserServlet 多个转发 或者重定向 要加return
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 获得请求的 ServletPath 并抽取其中的方法名字段
		String methodName = request.getServletPath();
		methodName = methodName.substring(1, methodName.length() - 5);
		// 通过方法名找到方法并调用
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 获得请求的 ServletPath 并抽取其中的方法名字段
		String methodName = request.getServletPath();
		methodName = methodName.substring(1, methodName.length() - 5);
		// 通过方法名找到方法并调用
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接口：处理 registerUI.user 请求 点击注册按钮，发送register.jsp表单界面给用户
	 * 
	 * @throws Exception
	 */
	public void registerUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.getRequestDispatcher("/register.jsp").forward(request, response);

	}

	/**
	 * 接口：处理 register.user 请求 注册用户，初始用户名为账号，并设置初始头像
	 * 
	 * @throws Exception
	 */
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		获取表单提交的值 填充到user对象
		User user = new User();
		Map<String, String[]> properties = request.getParameterMap();
		BeanUtils.populate(user, properties);

		// 表单没有提交的数据需要手动封装
		user.setUid(UUID.randomUUID().toString());
		user.setCode(UUID.randomUUID().toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String udate = format.format(new Date());
		user.setBirthday(udate);
		user.setState(0);

		UserService userService = new UserService();

//		如果用户名已经存在 重新填 否则注册成功 跳到登录界面（带有参数）
		if (userService.isExist(user.getUsername())) {
			request.setAttribute("message", "user existed");
			request.getRequestDispatcher("/registerUI.user").forward(request, response);
			return;
		} else {
			request.setAttribute("message", "请激活邮箱后登录");
			userService.addUser(user);
			response.sendRedirect(request.getContextPath() + "/loginUI.user");
			return;
		}

	}

	/**
	 * 邮件激活 用户点击邮件连接访问此处 如果匹配则修改state并将code置空
	 */
	public void activate(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		获得激活码
		String code = request.getParameter("code");
//		用户激活
		UserService userService = new UserService();
		userService.activeUser(code);
		response.sendRedirect(request.getContextPath() + "/loginUI.user");

	}

	/**
	 * 接口：处理 loginUI.user请求 点击登录按钮，发送login.jsp表单界面给用户
	 * 
	 * @throws Exception
	 */
	public void loginUI(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (ServletException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 接口：处理 login.user请求
	 * 
	 * @throws Exception
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		先判断验证码 在判断用户名密码是否正确
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		String check = request.getParameter("check");
		System.out.println(checkcode_session);

		if (checkcode_session.equals(check)) {

//			1封装数据
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
//			2传递给service 通知service进行登录
			UserService userService = new UserService();
			User loginUser = userService.login(user);
//			根据service结果 处理 成功或失败两种情况
			if (loginUser != null) {

//				如果没有激活则不让他登录
				if (0 == loginUser.getState()) {
					request.setAttribute("msg", "请先通过邮件激活再登录");
//					请求转发到登录页
					request.getRequestDispatcher("/loginUI.user").forward(request, response);
					return;
				}

//				自动登录的处理 如果勾选则写入cookie，没有勾选则删除将删除之前自动登录的cookie
				String autoLogin = (String) request.getParameter("autologin");
				if ("1".equals(autoLogin)) {
//					添加cookie
					Cookie autoLoginCookie = new Cookie("autoLoginCookie",
							user.getUsername() + "@" + user.getPassword());
					autoLoginCookie.setPath("/");
					autoLoginCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(autoLoginCookie);
				} else {
//					删除cookie 替代为空值
					Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
					autoLoginCookie.setPath("/");
//					清除cookie必须将其设置为0
					autoLoginCookie.setMaxAge(0);
					response.addCookie(autoLoginCookie);

				}
//				自动登录的处理

//				记住用户名处理 如果勾选 下次用户名输入框就有了默认值
				String remerberme = request.getParameter("remerberme");

				if ("1".equals(remerberme)) {
					Cookie remembermeCookie = new Cookie("remembermeCookie", user.getUsername());
					remembermeCookie.setPath("/");
					remembermeCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(remembermeCookie);
				}
//				记住用户名处理				
//				session作用域记录登录状态
				request.getSession().setAttribute("loginUser", loginUser);
//				重定向到首页
				response.sendRedirect(request.getContextPath() + "/");
				return;

			} else {

//			错误处理
//			request作用域记录错误信息
				request.setAttribute("msg", "用户名或密码错误");
//			请求转发到登录页
				request.getRequestDispatcher("/loginUI.user").forward(request, response);
				return;
			}

		} else {

			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("/loginUI.user").forward(request, response);
			return;
		}

	}

	/**
	 * 接口：处理 logout.user请求
	 * 
	 * @throws Exception
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		System.out.println("logout");
		User loginUser = (User) request.getSession().getAttribute("loginUser");
//		System.out.println(loginUser);
		if (loginUser != null) {
//			将session用户状态移除
			request.getSession().removeAttribute("loginUser");
//			重定向到首页
			response.sendRedirect(request.getContextPath() + "/loginUI.user");
			return;
		}
	}

}
