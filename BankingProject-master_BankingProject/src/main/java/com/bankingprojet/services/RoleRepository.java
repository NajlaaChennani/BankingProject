package com.bankingprojet.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingprojet.entities.Role;
import com.bankingprojet.entities.Rolename;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(Rolename roleName);
}

