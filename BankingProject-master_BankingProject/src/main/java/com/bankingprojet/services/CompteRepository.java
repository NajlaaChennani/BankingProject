package com.bankingprojet.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Compte;

public interface CompteRepository extends CrudRepository<Compte, Long>{
	List<Compte> findByIduser(long iduser);
	Compte findByIduserAndType(long iduser, String type);
	boolean existsByTypeAndIduser(String type, long iduser);
	Compte findByIduserAndTypeAndEtat(long id, String string, boolean b);
}
