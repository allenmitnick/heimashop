package com.hyt.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hyt.domain.OrderItem;
import com.hyt.domain.Orders;
import com.hyt.domain.User;
import com.hyt.utils.DataSourceUtils;

public class OrdersDAO {

	public void addOrder(Orders order) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrderTime(), order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),
				order.getUser().getUid()};
		
		try {
			qr.update(sql, params);
//			System.out.println("order insert ok");
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}
	
	public void addOrderItem(OrderItem orderItem) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		Object[] params = {orderItem.getItemId(), orderItem.getCount(), orderItem.getSubTotal(),
				orderItem.getProduct().getPid(),orderItem.getOid()};
		try {
			qr.update(sql, params);
//			System.out.println("orderItem insert ok");
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}

	public Orders findByOid(String oid) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders WHERE oid =?";
		Object[] params = {oid};
		try {
			return qr.query(sql, new BeanHandler<Orders>(Orders.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
	}

	public void updateOrderState(String r6_Order) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE orders SET state=? WHERE oid=?";
		Object[] params = {2,r6_Order};
		try {
			
			qr.update(sql, params);
//			System.out.println("orders update ok");
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}

	public List<Orders> findAll(User user) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders WHERE uid =?";
		Object[] params = {user.getUid()};
		try {
			return qr.query(sql, new BeanListHandler<Orders>(Orders.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}

	public List<OrderItem> findAllItemsByOid(String oid) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orderitem WHERE oid =?";
		Object[] params = {oid};
		try {
			return qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}

	}

}
