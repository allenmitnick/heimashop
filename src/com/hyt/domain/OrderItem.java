package com.hyt.domain;

public class OrderItem {
	
	private String itemId;
	private Product product;
	private int count;
	private double subTotal;
	private String pid;
	private String oid;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal() {
		this.subTotal = count * product.getShop_price();
		return this.subTotal;
	}
//	因为是计算获得，所以不需要setter方法
//	public void setSubTotal(double subTotal) {
//		this.subTotal = count * product.getShop_price();
//		this.subTotal = subTotal;
//	}
	
	public String getOid() {
		return oid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId + ", product=" + product + ", count=" + count + ", subTotal=" + subTotal
				+ ", oid=" + oid + "]";
	}
	
	
	
	
	

}
