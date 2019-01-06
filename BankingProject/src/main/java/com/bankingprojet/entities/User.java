package com.bankingprojet.entities;

import java.util.HashSet;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            })
    })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
		
	
	private String name;
	
	private String username;
	
    @Size(min=6, max = 100)
	private String password;
	
	private int age;
	
	private String phone;
	
	private String address;	
		
	private double soldetelephonique;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

	public User(String name, String username, String password, int age, String phone, String address,
			double soldetelephonique) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.soldetelephonique = soldetelephonique;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getSoldetelephonique() {
		return soldetelephonique;
	}

	public void setSoldetelephonique(double soldetelephonique) {
		this.soldetelephonique = soldetelephonique;
	}
	
	public Set<Role> getRoles() {
        return roles;
    }
 
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
