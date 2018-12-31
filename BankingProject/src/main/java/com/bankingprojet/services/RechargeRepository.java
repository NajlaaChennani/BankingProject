package com.bankingprojet.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bankingprojet.entities.Recharge;

public interface RechargeRepository extends CrudRepository<Recharge, Long>{
	List<Recharge> findByIduser(long iduser);
}
