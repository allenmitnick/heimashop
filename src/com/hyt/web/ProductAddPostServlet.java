package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hyt.domain.Product;
import com.hyt.service.ProductService;

/**
 * Servlet implementation class ProductAddPostServlet
 */
public class ProductAddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置编码
		request.setCharacterEncoding("UTF-8");
		String pname = request.getParameter("pname");
//		System.out.println(pname);
		//获得提交的表单
		Map<String, String[]> properties = request.getParameterMap();
		//封装到bean
		Product product = new Product();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//表单没有提交的数据需要手动封装 pid
		product.setPid(UUID.randomUUID().toString());
		product.setPimage("products/1/c_0033.jpg");
		//private String pdate;//上架日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		//private int pflag;//商品是否下载 0代表未下架
		product.setPflag(0);
//		找service添加到数据库 传递bean对象
		ProductService productService = new ProductService();
		productService.addProduct(product);
		
		//4.重定向查询所有
		response.sendRedirect(request.getContextPath() + "/productFindAllServlet");
	}

}
