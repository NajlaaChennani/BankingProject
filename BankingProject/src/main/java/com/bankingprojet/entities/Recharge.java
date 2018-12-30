package com.bankingprojet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recharge")
public class Recharge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_recharge;
	
	private long id_user;
	
	private String phone;
	
	private double montant;
	
	private String date;

	public Recharge(String phone, long id_user, double montant, String date) {
		super();
		this.id_user = id_user;
		this.phone = phone;
		this.montant = montant;
		this.date = date;
	}

	public Recharge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId_recharge() {
		return id_recharge;
	}

	public void setId_recharge(long id_recharge) {
		this.id_recharge = id_recharge;
	}

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
