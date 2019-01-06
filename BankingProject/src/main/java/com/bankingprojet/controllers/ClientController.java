package com.bankingprojet.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bankingprojet.entities.Agence;
import com.bankingprojet.entities.Compte;
import com.bankingprojet.entities.Recharge;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.AgenceRepository;
import com.bankingprojet.services.CompteRepository;
import com.bankingprojet.services.RechargeRepository;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;

@CrossOrigin(origins = "*")
@EnableWebMvc
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
	
	@Autowired
	AgenceRepository agenceRepository;
	
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
		
		Compte compteverseur = compteRepository.findByIduserAndType(verseur.getId(), virement.getTypecompte());
		Optional<Compte> _comptebenef = compteRepository.findById(beneficiaire.getId());
		Compte comptebenef = _comptebenef.get();
		
		System.out.println(comptebenef.getIdcompte());
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
			Compte compteverseur =compteRepository.findByIduserAndType(user.getId(), "Compte courant");;
	
			System.out.println(compteverseur.getSolde());
			
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
	public ResponseEntity<User> getClient(@PathVariable String username)
	{
		System.out.println("détails ddd'un client");
        Optional<User> user = userRepository.findByUsername(username);		
		User _user = user.get();
		if (_user == null) {
			  return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			  }
			  return new ResponseEntity<User>(_user, HttpStatus.OK);
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
		System.out.println("retour d'un compte depuis l id " + username + "et le type");
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
	
	@GetMapping("/getagence/{idagence}")
	public Agence getAgence(@PathVariable long idagence)
	{
		 Optional<Agence> agence = agenceRepository.findById(idagence);		
			Agence _agence = agence.get();
			System.out.println(_agence.getName());
			return _agence;

	}
}
