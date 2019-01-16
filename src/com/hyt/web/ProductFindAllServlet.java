package com.hyt.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.Product;
import com.hyt.service.ProductService;


/**
 * Servlet implementation class ProductFindAllServlet
 */
public class ProductFindAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//web层怎么编写案例  
//		每个web层对应一个接口(url)，一个service对应多个web CURD需要，
//		一个dao对应多个web CURD需要 跟一个service对应
//		1.通知service，查询所有商品并返回
		ProductService productService = new ProductService();
		List<Product> allProduct = productService.findAll();
		
//		2.选择jsp
//		2.1 将查询结果存放在request作用域
		request.setAttribute("allProduct", allProduct);
//		2.3 请求转发
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
