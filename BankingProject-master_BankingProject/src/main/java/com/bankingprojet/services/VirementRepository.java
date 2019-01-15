package com.bankingprojet.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Virement;

public interface VirementRepository extends CrudRepository<Virement, Long>{
	List<Virement> findByIdverseur(long idverseur);
}
