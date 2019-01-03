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

import com.bankingprojet.entities.Compte;
import com.bankingprojet.entities.Recharge;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.CompteRepository;
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
	
	@Autowired
	CompteRepository compteRepository;
	
	java.util.Date date = new java.util.Date();
	String format = "dd/MM/yy H:mm:ss"; 
	java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
	
	@PostMapping(value = "/addvirement/{username}")
	public Virement postVirement(@RequestBody Virement virement, @PathVariable String username) {
		System.out.println("virement");
		String datevirement = formater.format(date);
		Optional<User> _verseur = userRepository.findByUsername(username);		
		Optional<User> _beneficiaire = userRepository.findById(virement.getIdbeneficiaire());
		
		User verseur = _verseur.get();
		User beneficiaire = _beneficiaire.get();
		
		Compte compteverseur = new Compte();
		Compte comptebenef = new Compte();
		
		List<Compte> comptesverseur = compteRepository.findByIduser(verseur.getId());
		List<Compte> comptesbenef = compteRepository.findByIduser(beneficiaire.getId());
		
		for(int i=0;i<comptesverseur.size();i++)
		{
			if(comptesverseur.get(i).getType().equals(virement.getTypecompte()))
			{
				compteverseur = comptesverseur.get(i);
			}
		}
		for(int i=0;i<comptesbenef.size();i++)
		{
			if(comptesbenef.get(i).getType().equals("Compte courant"))
			{
				comptebenef = comptesbenef.get(i);
			}
		}
		
		compteverseur.setSolde(compteverseur.getSolde()-virement.getMontant());
		comptebenef.setSolde(comptebenef.getSolde()+virement.getMontant());
		
		compteRepository.save(compteverseur);
		compteRepository.save(comptebenef);
		Virement _virement = virementRepository.save(new Virement(virement.getMotif(), virement.getIdbeneficiaire(), verseur.getId(),
				virement.getTypecompte(), virement.getMontant(),datevirement));
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
		List<Compte> comptesverseur = compteRepository.findByIduser(user.getId());
		Compte compteverseur = new Compte();
		
		for(int i=0;i<comptesverseur.size();i++)
		{
			if(comptesverseur.get(i).getType().equals("Compte courant"))
			{
				compteverseur = comptesverseur.get(i);
			}
		}
		
		compteverseur.setSolde((double)compteverseur.getSolde()-recharge.getMontant());
		compteRepository.save(compteverseur);
		
		beneficiaire.setSoldetelephonique((double)beneficiaire.getSoldetelephonique()+recharge.getMontant());
		userRepository.save(beneficiaire);
		
		
		Recharge _recharge = rechargeRepository.save(new Recharge(recharge.getPhone(), user.getId(), recharge.getMontant(), daterecharge));
		return _recharge;
	}
	
	@GetMapping("/comptesuser/{username}")
	public List<Compte> getComptes(@PathVariable String username)
	{
		System.out.println("comptes d'un client");

        Optional<User> user = userRepository.findByUsername(username);		
		User _user = user.get();
		
		List<Compte> listcomptes = compteRepository.findByIduser(_user.getId());
		return listcomptes;
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
	
	@GetMapping("getCompteById/{idcompte}")
	public Compte getCompte(@PathVariable long idcompte)
	{
		System.out.println("retour d'un compte");
        Optional<Compte> compte = compteRepository.findById(idcompte);		
		Compte _compte = compte.get();
		return _compte;
	}
	
	@GetMapping("/getcomptebyidandtype/{username}/{type}")
	public Compte getCompteByIdandType(@PathVariable String username, @PathVariable String type)
	{
		System.out.println("retour d'un compte depuis l id et le type");
		Optional<User> user = userRepository.findByUsername(username);		
		User _user = user.get();
        Compte compte = compteRepository.findByIduserAndType(_user.getId(), type);		
		return compte;
	}
	
	@GetMapping("/getuserbyphone/{phone}")
	public User getUserByPhone(@PathVariable String phone)
	{
		 Optional<User> user = userRepository.findByPhone(phone);		
			User _user = user.get();
			return _user;
	}
}
