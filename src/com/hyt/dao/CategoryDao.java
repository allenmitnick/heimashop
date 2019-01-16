package com.hyt.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hyt.domain.Category;
import com.hyt.domain.Product;
import com.hyt.utils.DataSourceUtils;

public class CategoryDao {
	
	/**
	 * 查询所有类别
	 * @return
	 */
	public List<Category> findAll() {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM category";
//		String sql = "SELECT * FROM category ORDER BY cid+0 ASC";
		Object[] params = {};
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	public Category findById(String cid) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//因为想带回产品的类别 通过笛卡尔积
//		String sql = "SELECT * FROM product p,category c WHERE p.cid = c.cid and p.pid = ?";
		String sql = "SELECT * FROM category WHERE cid =?";
		Object[] params = {cid};
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	public void updateCategory(Category category) {
		
		String cid = category.getCid();
//		System.out.println(category.getCid() + ":" + category.getCname());
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE category SET cname = ? WHERE cid=?";
		Object[] params = {category.getCname(), category.getCid()};
		try {
			qr.update(sql, params);
//			System.out.println("update ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	public void deleteCategoryById(String cid) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
//		将商品外键设置成null
		String sql = "UPDATE product SET cid=null WHERE  cid = ?";
		Object[] params = {cid};
		try {
			qr.update(sql, params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		sql = "DELETE FROM category WHERE cid = ?";
		try {
			qr.update(sql,params);
//			System.out.println("delete ok");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCategory(Category category) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO category VALUES(?,?)";
		Object[] params = {category.getCid(), category.getCname()};
		try {
			qr.update(sql, params);
//			System.out.println("insert ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}
		
	

}
