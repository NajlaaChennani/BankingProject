package com.bankingprojet.services;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Agence;

public interface AgenceRepository extends CrudRepository<Agence, Long>{
	Agence findByName(String name);
	void deleteByName(String name);
}
