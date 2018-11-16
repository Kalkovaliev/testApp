package com.pelagusit.store.web.rest.dto;

import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.Product;

public class OrderDTO {
	private Order order;
	private Product product;	
	private long quantity;
	private long totalprice;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public long getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(long totalprice) {
		this.totalprice = totalprice;
	}
}