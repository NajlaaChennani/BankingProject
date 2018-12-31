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
	private long idrecharge;
	
	private long iduser;
	
	private String phone;
	
	private double montant;
	
	private String date;

	public Recharge(String phone, long iduser, double montant, String date) {
		super();
		this.iduser = iduser;
		this.phone = phone;
		this.montant = montant;
		this.date = date;
	}

	public Recharge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getIdrecharge() {
		return idrecharge;
	}

	public void setIdrecharge(long idrecharge) {
		this.idrecharge = idrecharge;
	}

	public long getId_user() {
		return iduser;
	}

	public void setId_user(long iduser) {
		this.iduser = iduser;
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
