package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hyt.domain.Category;
import com.hyt.service.CategoryService;
import com.hyt.utils.JedisUtils;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private Gson gson;
    public CategoryServlet() {
        super();
        gson = new Gson();
    }

	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *    /add.category
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1,servletPath.length()-9);
		try {
			Method method= this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1,servletPath.length()-9);
		try {
			Method method= this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * allCategory.category
	 * 所有分类的servlet
	 * 改进通过缓存技术
	 * 如果服务器查过了并且放在了缓存中将直接从缓存中读取，否则去查询数据库获取
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void allCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
/*//		1.查询所有分类
		CategoryService categoryService = new CategoryService();
		List<Category> allCategory = categoryService.findAll();
//		将查询结果转换成json
		String jsonStr = gson.toJson(allCategory).toString();
//		将结果响应给浏览器
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(jsonStr);
//		response.getWriter().print(gson.toJson("hello"));
		return;
		
//		request.setAttribute("allCategory", allCategory);	
//		request.getRequestDispatcher("").forward(request, response);
*/	
		CategoryService categoryService = new CategoryService();
		String category_list = categoryService.findAllByAjax();
//		System.out.println(category_list);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(category_list);
		return;
	}

		

}
