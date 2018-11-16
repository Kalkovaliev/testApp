package com.pelagusit.store.web.rest.dto;

public class OrderProductsUpdateDTO {
	
	private long orderProduct_id;
	private long order_id;
	private long product_id;
	private long quantity;
	private long totalprice;


	public long getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(long totalprice) {
		this.totalprice = totalprice;
	}

	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getOrderProduct_id() {
		return orderProduct_id;
	}
	public void setOrderProduct_id(long orderProduct_id) {
		this.orderProduct_id = orderProduct_id;
	}
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
}