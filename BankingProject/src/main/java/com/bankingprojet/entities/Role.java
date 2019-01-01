package com.bankingprojet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	 @Enumerated(EnumType.STRING)
	 @NaturalId
	 @Column(length = 60)
	 private Rolename name;
	 
	 public Role(long id, Rolename name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Rolename getName() {
		return name;
	}

	public void setName(Rolename name) {
		this.name = name;
	}

	
	public Role() {}
	 
	 public Role(Rolename name) {
	     this.name = name;
	 }
	 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
