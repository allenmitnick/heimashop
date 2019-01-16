package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hyt.domain.Cart;
import com.hyt.domain.Product;
import com.hyt.service.ProductService;

/**
 * Servlet implementation class Cart
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductService productService = new ProductService();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1, servletPath.indexOf(".cart"));

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1, servletPath.indexOf(".cart"));
//		System.out.println(methodName);

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
//	添加商品到购物车
	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		System.out.println("addToCart");
		//		1获得请求参数
		String pid = request.getParameter("pid");
//		1.1商品id
		Integer count = Integer.parseInt(request.getParameter("count"));
//		1.2商品数量
		Product product = productService.findById(pid);
//		获得购物车
		Cart cart = getCart(request.getSession());
//		将商品添加到购物车
		cart.addCart(product, count);
//		重定向到购物车
//		System.out.println(request.getContextPath() + "/cart.jsp");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		return;
		
	}
	
	/**
	 * 从购物车中删除某项商品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delFromCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String pid = request.getParameter("pid");
//		获得购物车
		Cart cart = getCart(request.getSession());
//		将商品从购物车删除
		cart.removeCart(pid);
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		return;
	}
	
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
//		获得购物车
		Cart cart = getCart(request.getSession());
		cart.clearCart();
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		return;
			
	}
	
	
	

	/**
	 * 获得购物车 放在session中
	 * @param session
	 * @return
	 */
	public Cart getCart(HttpSession session) {
		
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null ) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	

}
