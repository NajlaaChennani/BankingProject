package com.bankingprojet.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class VirementController {
	@Autowired
	VirementRepository virementRepository;
	
	@Autowired
	UserRepository userRepository;
	
	java.util.Date date = new java.util.Date();
	String format = "dd/MM/yy H:mm:ss"; 
	java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
	
	@PutMapping(value = "/api/banking/addvirement")
	public Virement postVirement(@RequestBody Virement virement) {
		String datevirement = formater.format(date);
		Optional<User> _verseur = userRepository.findById(virement.getId_verseur());		
		Optional<User> _beneficiaire = userRepository.findById(virement.getId_beneficiare());
		
		User verseur = _verseur.get();
		User beneficiaire = _beneficiaire.get();
		
		verseur.setSoldebanquaire((double)verseur.getSoldebanquaire()-virement.getMontant());
		userRepository.save(verseur);
		
		beneficiaire.setSoldebanquaire((double)beneficiaire.getSoldebanquaire()+virement.getMontant());
		userRepository.save(beneficiaire);
		
		Virement _virement = virementRepository.save(new Virement(virement.getMotif(), virement.getId_beneficiare(), virement.getId_verseur()
				, virement.getMontant(),datevirement));
		return _virement;
	}
	
}
