package com.bankingprojet.message;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignUpForm {

private String name;
	
	private String username;
	
	private String password;
	
	private int age;
	
	private String phone;
	
	private String address;
	
	private double soldebanquaire;
	
	private double soldetelephonique;
 
    private Set<String> role;
    
 
    
    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSoldebanquaire() {
		return soldebanquaire;
	}

	public void setSoldebanquaire(double soldebanquaire) {
		this.soldebanquaire = soldebanquaire;
	}

	public double getSoldetelephonique() {
		return soldetelephonique;
	}

	public void setSoldetelephonique(double soldetelephonique) {
		this.soldetelephonique = soldetelephonique;
	}

	public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
    	return this.role;
    }
    
    public void setRole(Set<String> role) {
    	this.role = role;
    }
}