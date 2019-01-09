package com.bankingprojet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="agence", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
            })
})
public class Agence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idagence;
		
	private String name;
	
	private String adresse;

	public Agence() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Agence(String name, String adresse) {
		super();
		this.name = name;
		this.adresse = adresse;
	}

	public Agence(long idagence, String name, String adresse) {
		super();
		this.idagence = idagence;
		this.name = name;
		this.adresse = adresse;
	}
	
	public long getIdagence() {
		return idagence;
	}

	public void setIdagence(long idagence) {
		this.idagence = idagence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	

}
