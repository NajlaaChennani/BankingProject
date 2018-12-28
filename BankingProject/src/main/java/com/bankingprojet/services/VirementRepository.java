package com.bankingprojet.services;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Virement;

public interface VirementRepository extends CrudRepository<Virement, Long>{

}
