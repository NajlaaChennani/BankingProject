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
	private long id_virement;
	
	private String motif;
	
	private long id_beneficiaire;
	
	private long id_verseur;
	
	private double montant;
	
	private String date;
	
	
	
	public Virement() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Virement(String motif, long id_beneficiaire, long id_verseur, double montant, String date) {
		super();
		this.motif = motif;
		this.id_beneficiaire = id_beneficiaire;
		this.id_verseur = id_verseur;
		this.montant = montant;
		this.date = date;
	}



	public long getId_virement() {
		return id_virement;
	}



	public void setId_virement(long id_virement) {
		this.id_virement = id_virement;
	}



	public String getMotif() {
		return motif;
	}



	public void setMotif(String motif) {
		this.motif = motif;
	}



	public long getId_beneficiaire() {
		return id_beneficiaire;
	}



	public void setId_beneficiaire(long id_beneficiaire) {
		this.id_beneficiaire = id_beneficiaire;
	}



	public long getId_verseur() {
		return id_verseur;
	}



	public void setId_verseur(long id_verseur) {
		this.id_verseur = id_verseur;
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
