package com.pelagusit.store.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "orderproducts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderProducts extends DefaultModel implements Serializable {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_order")
	private Order order;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@NotNull
	@Column(name = "quantity")
	private long quantity;

	@NotNull
	@Column(name = "totalprice")
	private long totalprice;

	public long getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(long totalprice) {
		this.totalprice = totalprice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
