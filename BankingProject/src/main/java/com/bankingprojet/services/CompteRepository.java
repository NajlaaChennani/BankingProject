package com.bankingprojet.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Compte;

public interface CompteRepository extends CrudRepository<Compte, Long>{
	List<Compte> findByIduser(long iduser);
}
