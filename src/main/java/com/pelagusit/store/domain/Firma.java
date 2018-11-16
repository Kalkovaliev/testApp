package com.pelagusit.store.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "firma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Firma extends DefaultModel implements Serializable{
	
	
	@NotNull
    @Size(min = 0, max = 100)
	 @Column(name = "name")
    private String name;
	

	/*@ManyToOne
	@JoinColumn(name = "city_id", table = "city")
    private City city;
*/
	
	@ManyToOne
	 @JoinColumn(name="city_id")
	   private City city;
	
	
	 @Size(min = 0, max = 50)
	@Column(name = "phone")
    private String phone;

	 @Size(min = 0, max = 150)
	@Column(name = "adress")
    private String adress;
	 



	

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
