package com.hyt.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyt.domain.PageBean;
import com.hyt.domain.Product;
import com.hyt.service.ProductService;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductServlet() {
		super();

	}

	/**
	 * /all.product
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
//		System.out.println(servletPath);		
		String methodName = servletPath.substring(1, servletPath.indexOf(".product"));
//		System.out.println(methodName);

//		String methodName = servletPath.substring(1,servletPath.length()-8);
//		System.out.println(methodName);
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
		String methodName = servletPath.substring(1, servletPath.indexOf(".product"));
//		System.out.println(methodName);

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 主页商品 最新商品和最热商品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void productIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		System.out.println("productIndex running");
//		查询热门商品
		List<Product> hotList = productService.findByHot();
//		查询最新商品
		List<Product> newList = productService.findByNew();

//		将查询结果存放
		request.setAttribute("hotList", hotList);
		request.setAttribute("newList", newList);
//		System.out.println(hotList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * 商品详情 <通过商品id>
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void productDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		1.接收参数
		String pid = request.getParameter("pid");
//		2.通过service进行查询 使用参数
		Product product = productService.findById(pid);
//		System.out.println("pid" + product.getPid());
//		3查询结果放到request域
		request.setAttribute("product", product);
//		历史记录的处理
		// 获得客户端携带cookie---获得名字是pids的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					// 1-3-2 本次访问商品pid是8----->8-1-3-2
					// 1-3-2 本次访问商品pid是3----->3-1-2
					// 1-3-2 本次访问商品pid是2----->2-1-3
					// 将pids拆成一个数组
					String[] split = pids.split("&");// {3,1,2}
					List<String> asList = Arrays.asList(split);// [3,1,2]
					LinkedList<String> list = new LinkedList<String>(asList);// [3,1,2]
					// 判断集合中是否存在当前pid
					if (list.contains(pid)) {
						// 包含当前查看商品的pid
						list.remove(pid);
						list.addFirst(pid);
					} else {
						// 不包含当前查看商品的pid 直接将该pid放到头上
						list.addFirst(pid);
					}
					// 将[3,1,2]转成3-1-2字符串
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("&");// 3-1-2-
					}
					// 去掉3-1-2-后的-
					pids = sb.substring(0, sb.length() - 1);
				}
			}
		}

		Cookie cookie_pids = new Cookie("pids", pids);
		response.addCookie(cookie_pids);
//		历史记录的处理

//		转发
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	/**
	 * 分类商品
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void productCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		System.out.println("productCategory running");
//		根据分类查询的产品分页两个参数：分类id 当前页数目（默认为首页）
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");

		if (currentPage == null || "".equals(currentPage.trim())) {
			currentPage = "1";
		}
		int currPage = Integer.parseInt(currentPage);
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setCurrentPage(currPage);
		pageBean.setPageCount(12);
		// 调用service
		productService.findByCid(cid, pageBean); // 【pageBean已经被dao填充了数据】
		request.setAttribute("cid", cid);
		request.setAttribute("pageBean", pageBean);

//		定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
//		获得客户端携带名字叫pids的cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] splits = pids.split("&");
					for (String pid : splits) {
						Product product = productService.findById(pid);
						historyProductList.add(product);
					}
				}
			}
		}

		// 将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);

	}

}
