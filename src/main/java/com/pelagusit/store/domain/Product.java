package com.pelagusit.store.domain;

import java.io.Serializable;




import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends DefaultModel implements Serializable{
	
	
	
	@NotNull
    @Size(min = 0, max = 100)
	 @Column(name = "name")
    private String name;
	
	

	/*@ManyToOne
	@JoinColumn(name = "city_id", table = "city")
    private City city;
*/
	
	@ManyToOne
	 @JoinColumn(name="company_id")
	   private Firma company;
	
	
	 @Size(min = 0, max = 100)
	@Column(name = "description")
    private String description;

	
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Column(name = "price")
    private Double price;
	 
	@Column(name = "available")   //mozno e da zafrkava bit so boolean???
    private boolean available;
	 


	 

	public Firma getCompany() {
		return company;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCompany(Firma company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
