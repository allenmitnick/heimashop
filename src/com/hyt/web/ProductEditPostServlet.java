package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hyt.domain.Product;
import com.hyt.service.ProductService;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class ProductEditPostServlet
 */
public class ProductEditPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.编码
		request.setCharacterEncoding("UTF-8");
		//2.获得数据并封装
		Product product = new Product();
		Map<String, String[]> properties = request.getParameterMap();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//手动设置表单中没有的数据
		
		
		//3 发到service层编辑操作
		//4.重定向查询所有
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.编码
		request.setCharacterEncoding("UTF-8");
		//2.获得数据并封装
		Product product = new Product();
		Map<String, String[]> properties = request.getParameterMap();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//手动设置表单中没有的数据
//		System.out.println(request.getParameter("pid"));
//		System.out.println(product);
//		System.out.println("hyt");
		product.setPimage("products/1/c_0033.jpg");
//		private String pdate;//上架日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate= format.format(new Date());
		product.setPdate(pdate);
//		private int pflag;//商品是否下载 0代表未下架
		product.setPflag(0);
		//3 发到service层编辑操作
		ProductService productService = new ProductService();
		productService.updateProduct(product);
		//4.重定向查询所有
		response.sendRedirect(request.getContextPath() + "/productFindAllServlet");
	}

}
