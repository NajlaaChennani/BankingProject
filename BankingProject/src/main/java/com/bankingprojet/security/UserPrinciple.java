package com.bankingprojet.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.bankingprojet.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String name;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private int age;
	
	private String phone;
	
	private String address;
		
	private double soldetelephonique;
    
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserPrinciple(long id, String name, String username, String password, int age, String phone, String address,
			double soldetelephonique, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.soldetelephonique = soldetelephonique;
		this.authorities = authorities;
	}
 
	public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().name()))
        		.collect(Collectors.toList());
 
        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getAge(),
                user.getAddress(),
                user.getPhone(),
                user.getSoldetelephonique(),
                authorities
        );
    }
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}



	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getAge() {
		return age;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}


	public double getSoldetelephonique() {
		return soldetelephonique;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
