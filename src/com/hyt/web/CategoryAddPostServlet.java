package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hyt.domain.Category;
import com.hyt.service.CategoryService;
import com.hyt.service.ProductService;

/**
 * Servlet implementation class CategoryAddPostServlet
 */
public class CategoryAddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Map<String, String[]> properties = request.getParameterMap();
		Category category = new Category();
		try {
			BeanUtils.populate(category, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//表单没有提交的数据需要手动封装 cid
		category.setCid(UUID.randomUUID().toString());
		
//		找service添加到数据库   传递bean对象
		CategoryService categoryService = new CategoryService();
		categoryService.addCategory(category);
		
		//重定向查询所有
		response.sendRedirect(request.getContextPath() + "/categoryFindAllServlet");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Map<String, String[]> properties = request.getParameterMap();
		Category category = new Category();
		try {
			BeanUtils.populate(category, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//表单没有提交的数据需要手动封装 cid
		category.setCid(UUID.randomUUID().toString());
		
//		找service添加到数据库   传递bean对象
		CategoryService categoryService = new CategoryService();
		categoryService.addCategory(category);
		
		//重定向查询所有
		response.sendRedirect(request.getContextPath() + "/categoryFindAllServlet");
	
	}

}
