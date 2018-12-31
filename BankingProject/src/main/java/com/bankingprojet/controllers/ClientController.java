package com.bankingprojet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingprojet.entities.Recharge;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.RechargeRepository;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/banking")
@RestController
public class ClientController {
	@Autowired
	VirementRepository virementRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RechargeRepository rechargeRepository;
	
	java.util.Date date = new java.util.Date();
	String format = "dd/MM/yy H:mm:ss"; 
	java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
	
	@PostMapping(value = "/addvirement")
	public Virement postVirement(@RequestBody Virement virement) {
		System.out.println("virement");
		String datevirement = formater.format(date);
		Optional<User> _verseur = userRepository.findById(virement.getIdverseur());		
		Optional<User> _beneficiaire = userRepository.findById(virement.getIdbeneficiaire());
		
		User verseur = _verseur.get();
		User beneficiaire = _beneficiaire.get();
		
		verseur.setSoldebanquaire((double)verseur.getSoldebanquaire()-virement.getMontant());
		userRepository.save(verseur);		
		beneficiaire.setSoldebanquaire((double)beneficiaire.getSoldebanquaire()+virement.getMontant());
		userRepository.save(beneficiaire);
		
		Virement _virement = virementRepository.save(new Virement(virement.getMotif(), virement.getIdbeneficiaire(), virement.getIdverseur()
				, virement.getMontant(),datevirement));
		return _virement;
	}
	
	
	@PostMapping(value = "/addrecharge/{username}")
	public Recharge postRecharge(@RequestBody Recharge recharge, @PathVariable String username)
	{
		System.out.println("Recharge");
		String daterecharge = formater.format(date);
		Optional<User> _user = userRepository.findByUsername(username);
		Optional<User> _beneficiaire = userRepository.findByPhone(recharge.getPhone());
		
		User user = _user.get();
		User beneficiaire = _beneficiaire.get();
		
		user.setSoldebanquaire((double)user.getSoldebanquaire()-recharge.getMontant());
		userRepository.save(user);
		beneficiaire.setSoldetelephonique((double)beneficiaire.getSoldetelephonique()+recharge.getMontant());
		userRepository.save(beneficiaire);
		
		
		Recharge _recharge = rechargeRepository.save(new Recharge(recharge.getPhone(), user.getId(), recharge.getMontant(), daterecharge));
		return _recharge;
	}
	
	@GetMapping("/allvirements/{username}")
	public List<Virement> getAllVirements(@PathVariable String username)
	{
		System.out.println("Tous les Virements...");
		
		Optional<User> user = userRepository.findByUsername(username);
		
		User _user = user.get();
		
		List<Virement> virements = virementRepository.findByIdverseur(_user.getId());
		return virements;
	}
	
	@GetMapping("/allrecharges/{username}")
	public List<Recharge> getAllRecharges(@PathVariable String username)
	{
		System.out.println("Toutes les recharges...");
		
		Optional<User> user = userRepository.findByUsername(username);
		
		User _user = user.get();
		
		List<Recharge> recharges = rechargeRepository.findByIduser(_user.getId());
		return recharges;
	}
	
	@GetMapping("/getclient/{username}")
	public User getClient(@PathVariable String username)
	{
		System.out.println("détails d'un client");
        Optional<User> user = userRepository.findByUsername(username);		
		User _user = user.get();
		return _user;
	}
}
