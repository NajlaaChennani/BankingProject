package com.bankingprojet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="compte")
public class Compte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idcompte;
	
	private long iduser;
	
	private String type;
	
	private String date;
	
	private double solde;
	
	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compte(long iduser, String type, String date, double solde) {
		super();
		this.iduser = iduser;
		this.type = type;
		this.date = date;
		this.solde = solde;
	}

	public long getIdcompte() {
		return idcompte;
	}

	public void setIdcompte(long idcompte) {
		this.idcompte = idcompte;
	}

	public long getIduser() {
		return iduser;
	}

	public void setIduser(long iduser) {
		this.iduser = iduser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}
	
	
}
