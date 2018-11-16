package com.pelagusit.store.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class DefaultModel {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@CreatedBy
	@NotNull
	@Column(name = "CREATOR")
	private String creator;

	@CreatedDate
	@NotNull
	@Column(name = "DATE_CREATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@LastModifiedBy
	@Column(name = "LAST_MODIFIER")
	private String lastModifier;

	@LastModifiedDate
	@Column(name = "DATE_LAST_UPDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastUpdate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}
}
