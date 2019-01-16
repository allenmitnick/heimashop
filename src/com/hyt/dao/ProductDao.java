package com.hyt.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hyt.domain.Product;
import com.hyt.utils.DataSourceUtils;

public class ProductDao {
//	Dao 通过jdbc对product表进行CURD操作
	
	/**
	 * 查询所有产品
	 * @return
	 */
	public List<Product> findAll() {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product";
//		String sql = "SELECT * FROM product ORDER BY pid+0 ASC";
		Object[] params = {};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
		
	}

	/**
	 * 通过pid查询产品
	 * @return
	 */
	public Product findById(String pid) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//因为想带回产品的类别 通过笛卡尔积
//		String sql = "SELECT * FROM product p,category c WHERE p.cid = c.cid and p.pid = ?";
		String sql = "SELECT * FROM product WHERE pid =?";
		Object[] params = {pid};
		try {
			return qr.query(sql, new BeanHandler<Product>(Product.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}

	/**
	 * 根据产品pid修改产品信息
	 * @return
	 */
	public void updateProduct(Product product) {
		
		String pid = product.getPid();
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE product SET pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,"
				+ "pdesc=?,pflag=?,cid=? WHERE pid=?";
		Object[] params = {product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),
				product.getPflag(),product.getCid(),product.getPid()};
		try {
			
			qr.update(sql, params);
//			System.out.println("update ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}

	public void deleteById(String pid) {
	
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "DELETE FROM product WHERE pid = ?";
		Object[] params = {pid};
		try {
			qr.update(sql, params);
//			System.out.println("delete ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}

	public void addProduct(Product product) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCid()};
		try {
			qr.update(sql, params);
//			System.out.println("insert ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}
//查询热门商品
	public List<Product> findByHot() {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());	
		String sql = "SELECT * FROM product WHERE is_hot=? AND pflag=? ORDER BY pdate DESC limit ?";
		Object[] params = {1, 0, 9};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

//	查询最新商品
	public List<Product> findByNew() {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());	
		String sql = "SELECT * FROM product WHERE pflag=? ORDER BY pdate DESC limit ?";
		Object[] params = {0, 9};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}

	public int findToalRecordById(String cid) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());	
		String sql = "SELECT count(*) FROM product WHERE cid=?";
		Object[] params = {cid};
		try {
			Long count = (Long)qr.query(sql, new ScalarHandler(), params);;
			return count.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	public List<Product> findAllByCid(String cid, int startIndex, int pageSize) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());	
		String sql = "SELECT * FROM product WHERE cid =? and pflag=? ORDER BY pdate DESC LIMIT ?,?";
		Object[] params = {cid, 0, startIndex, pageSize};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			throw new RuntimeException();
			return null;
		}
		
	}

}
