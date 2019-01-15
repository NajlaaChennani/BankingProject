package com.bankingprojet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="virement")
public class Virement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idvirement;
	
	private String motif;
	
	private long idbeneficiaire;
	
	private long idverseur;
	
	private String typecompte;
	
	private double montant;
	
	private String date;
	
	
	
	public Virement() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Virement(String motif, long idbeneficiaire, long idverseur, String typecompte, double montant, String date) {
		super();
		this.motif = motif;
		this.idbeneficiaire = idbeneficiaire;
		this.idverseur = idverseur;
		this.setTypecompte(typecompte);
		this.montant = montant;
		this.date = date;
	}

	public Virement(long idvirement, String motif, long idbeneficiaire, long idverseur, String typecompte, double montant, String date) {
		super();
		this.idvirement = idvirement;
		this.motif = motif;
		this.idbeneficiaire = idbeneficiaire;
		this.idverseur = idverseur;
		this.setTypecompte(typecompte);
		this.montant = montant;
		this.date = date;
	}

	public long getIdvirement() {
		return idvirement;
	}



	public void setIdvirement(long idvirement) {
		this.idvirement = idvirement;
	}



	public String getMotif() {
		return motif;
	}



	public void setMotif(String motif) {
		this.motif = motif;
	}



	public long getIdbeneficiaire() {
		return idbeneficiaire;
	}



	public void setIdbeneficiaire(long idbeneficiaire) {
		this.idbeneficiaire = idbeneficiaire;
	}



	public long getIdverseur() {
		return idverseur;
	}



	public void setIdverseur(long idverseur) {
		this.idverseur = idverseur;
	}



	public String getTypecompte() {
		return typecompte;
	}



	public void setTypecompte(String typecompte) {
		this.typecompte = typecompte;
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
