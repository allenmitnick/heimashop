package com.hyt.service;

import java.util.List;

import com.hyt.dao.ProductDao;
import com.hyt.domain.PageBean;
import com.hyt.domain.Product;

public class ProductService {
//	指控dao层对产品表CURD
	
	/**
	 * 查询所有产品
	 * @return
	 */
	ProductDao productDao = new ProductDao();
	
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		
		return productDao.findAll();
	}
	
	/**
	 * 通过pid查询产品
	 * @return
	 */
	public Product findById(String pid) {
		
		return productDao.findById(pid);
	}

	/**
	 * 修改产品
	 * @return
	 */
	public void updateProduct(Product product) {
		
		productDao.updateProduct(product);		
	}

	public void deleteById(String pid) {
		
		productDao.deleteById(pid);		
	}

	public void addProduct(Product product) {
		
		productDao.addProduct(product);
	}

	public List<Product> findByHot() {
		// TODO Auto-generated method stub
		return productDao.findByHot();
	}

	public List<Product> findByNew() {
		// TODO Auto-generated method stub
		return productDao.findByNew();
	}
	

	public void findByCid(String cid, PageBean<Product> pageBean) {
		
//		获得总记录数
		int totalRecord = productDao.findToalRecordById(cid);
		pageBean.setTotalCount(totalRecord);
		
		 /*
		* 问题： jsp页面，如果当前页为首页，再点击上一页报错！
		*              如果当前页为末页，再点下一页显示有问题！
		* 解决：
		*        1. 如果当前页 <= 0;       当前页设置当前页为1;
		*        2. 如果当前页 > 最大页数；  当前页设置为最大页数
		*/
		if(pageBean.getCurrentPage() <= 0) {
			pageBean.setCurrentPage(1);
		}else if(pageBean.getCurrentPage() > pageBean.getTotalPage()) {
			pageBean.setCurrentPage(pageBean.getTotalPage());
		}
		
		//获取当前页： 计算查询的起始行、返回的行数
		int currentPage = pageBean.getCurrentPage();
		
		int startIndex = (currentPage - 1) * pageBean.getPageCount();
		int pageSize = pageBean.getPageCount();
		
		List<Product> proDate = productDao.findAllByCid(cid, startIndex, pageSize);
		
		pageBean.setData(proDate);
		
	}

}
