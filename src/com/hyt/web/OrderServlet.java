package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.Cart;
import com.hyt.domain.OrderItem;
import com.hyt.domain.Orders;
import com.hyt.domain.User;
import com.hyt.service.OrderService;
import com.hyt.utils.PaymentUtil;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = new OrderService();

//	UserService userService = new UserService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1, servletPath.indexOf(".order"));

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
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
		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1, servletPath.indexOf(".order"));

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * url： /saveOrder.order 保存订单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		1获得数据
//		1.1获得购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
//		1.2获得用户
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if (loginUser == null) {

			request.setAttribute("msg", "请先登录再购买");
			request.getRequestDispatcher("/loginUI.user").forward(request, response);
			return;

		}
//		2封装数据 主要将购物车转化为订单
		Orders order = new Orders();
//		2.1服务器自动生成
		order.setOid(UUID.randomUUID().toString());
		order.setState(1);
		order.setOrderTime(new Date());
//		设置总金额
		order.setTotal(cart.getTotal());
//		设置所属用户
		order.setUser(loginUser);
//		设置订单的用户名
		order.setName(loginUser.getUsername());
//		设置电话号
		order.setTelephone(loginUser.getTelephone());
//		设置订单地址
		order.setAddress(loginUser.getAddress());
//		遍历购物车 转换到订单
		for (Map.Entry<String, OrderItem> entry : cart.getOrderItems().entrySet()) {

			OrderItem cartItem = entry.getValue();
//			System.out.println(cartItem);
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId(cartItem.getItemId());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setCount(cartItem.getCount());
			orderItem.setOid(order.getOid());
//			System.out.println(orderItem);
			order.getList().add(orderItem);

		}
//		System.out.println(order);
//		调用业务层
		orderService.save(order);
//		清空购物车
		cart.clearCart();
//		页面跳转
		request.setAttribute("order", order);
		request.getRequestDispatcher("order_info.jsp").forward(request, response);

	}

	/**
	 * 支付订单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		1.获得请求参数
		String oid = request.getParameter("oid");
//		System.out.println(oid);
//		2调用业务层，查询订单，需要的话修改订单状态
		Orders order = orderService.findByOid(oid);
//		System.out.println(order.getAddress());
//		重定向到第三方平台 易宝
		// 在线支付
		/*
		 * if(pd_FrpId.equals("ABC-NET-B2C")){ //介入农行的接口 }else
		 * if(pd_FrpId.equals("ICBC-NET-B2C")){ //接入工行的接口 }
		 */
		// .......

		// 只接入一个接口，这个接口已经集成所有的银行接口了 ，这个接口是第三方支付平台提供的
		// 接入的是易宝支付
		// 获得 支付必须基本数据 订单号oid 金额 银行
//		String money = order.getTotal();
		
		String money = "0.01";
		
		String pd_FrpId = request.getParameter("pd_FrpId");
//		System.out.println(pd_FrpId);
		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
				"&p0_Cmd="+p0_Cmd+
				"&p1_MerId="+p1_MerId+
				"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+
				"&p4_Cur="+p4_Cur+
				"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+
				"&p7_Pdesc="+p7_Pdesc+
				"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+
				"&pa_MP="+pa_MP+
				"&pr_NeedResponse="+pr_NeedResponse+
				"&hmac="+hmac;

		//重定向到第三方支付平台
		response.sendRedirect(url);
		
	}

	/**
	 * 查看所有订单信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void orderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if (loginUser == null) {

			request.setAttribute("msg", "请先登录再查看订单");
			request.getRequestDispatcher("/loginUI.user").forward(request, response);
			return;

		}
		
		List<Orders> orderList = orderService.findAll(loginUser);
		request.setAttribute("orderList", orderList);
//		System.out.println(orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
}
