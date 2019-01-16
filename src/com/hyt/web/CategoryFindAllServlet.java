package com.hyt.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.Category;
import com.hyt.service.CategoryService;

/**
 * Servlet implementation class CategoryFindAllServlet
 */
public class CategoryFindAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		System.out.println("CategoryFindAllServlet");
		CategoryService categoryService = new CategoryService();
		List<Category> allCategory = categoryService.findAll();
		request.setAttribute("allCategory", allCategory);
//		System.out.println(allCategory);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
