package com.hyt.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
	
	private Map<String, OrderItem> orderItems = new HashMap<String, OrderItem>();
	private double total;
	
	public Map<String, OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Map<String, OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
//	购物车价格总计
	public double getTotal() {
		total = 0;
		for(Map.Entry<String, OrderItem> entry:orderItems.entrySet()) {
			OrderItem orderItem = entry.getValue();
			total +=orderItem.getSubTotal();
		}
		return total;
	}
	/**
	 * 设置购物车的总金额 用不上
	 * @param total
	 */
//	public void setTotal(double total) {
//		this.total = total;
//	}
	
	/**
	 * 添加商品到购物车
	 * @param product
	 * @param count
	 */
	public void addCart(Product product, int count) {
		if(product == null) {
			return;
		}
//		要区分是否是第一次买该商品 
		OrderItem orderItem = orderItems.get(product.getPid());
//		第一次购买
		if(orderItem == null) {
			
			orderItem = new OrderItem();
			orderItem.setItemId(UUID.randomUUID().toString());
			orderItem.setProduct(product);
			orderItem.setCount(count);
			this.orderItems.put(product.getPid(), orderItem);
		}else {
			orderItem.setCount(count);
		}
//		total += orderItem.getSubTotal();

	}
	
	/**
	 * 从购物车中删除某项商品
	 * @param pid
	 */
	public void removeCart(String pid) {
		
		OrderItem orderItem = orderItems.get(pid);
		if(orderItem != null) {
			orderItems.remove(pid);
		}
	}
	/**
	 * 清空购物车
	 */
	public void clearCart() {
		
		orderItems.clear();
		
	}
	
	
	
	

}
