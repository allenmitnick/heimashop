package com.hyt.service;

import java.util.List;

import com.hyt.dao.OrdersDAO;
import com.hyt.domain.OrderItem;
import com.hyt.domain.Orders;
import com.hyt.domain.Product;
import com.hyt.domain.User;

public class OrderService {

	private OrdersDAO ordersDAO = new OrdersDAO();
	public void save(Orders order) {
		
		ordersDAO.addOrder(order);
		for(OrderItem orderItem:order.getList()) {
			ordersDAO.addOrderItem(orderItem);
		}
		
	}
	public Orders findByOid(String oid) {
		return ordersDAO.findByOid(oid);
		
	}
	public void updateOrderState(String r6_Order) {
		ordersDAO.updateOrderState(r6_Order);
		
	}
	public List<Orders> findAll(User user) {
		// TODO Auto-generated method stub
		List<Orders> orderList = ordersDAO.findAll(user);
		for(Orders order:orderList) {
			order.setUser(user);
			String oid = order.getOid();
			List<OrderItem> orderItemList = ordersDAO.findAllItemsByOid(oid);
			
			for(OrderItem orderItem:orderItemList) {
				
				ProductService productService = new ProductService();
				Product product = productService.findById(orderItem.getPid());
				orderItem.setProduct(product);
			}
			order.setList(orderItemList);	
		}
		return orderList;
	}

}
