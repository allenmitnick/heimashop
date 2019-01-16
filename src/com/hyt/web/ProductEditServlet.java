package com.hyt.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.Category;
import com.hyt.domain.Product;
import com.hyt.service.CategoryService;
import com.hyt.service.ProductService;

/**
 * Servlet implementation class ProductEditServlet
 */
public class ProductEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.获得get的id参数去找service层 光产品不够还需要分类
		String pid = request.getParameter("pid");
		
		ProductService productService = new ProductService();
		Product product = productService.findById(pid);
		//获得所有分类
		CategoryService categoryService = new CategoryService();
		List<Category> allCategory = categoryService.findAll();
		//2.装入数据转发到jsp
		request.setAttribute("product", product);
		request.setAttribute("allCategory", allCategory);
		
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
