package com.bankingprojet.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;

import com.bankingprojet.entities.Agence;
import com.bankingprojet.entities.Compte;
import com.bankingprojet.entities.Role;
import com.bankingprojet.entities.Rolename;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.message.JwtResponse;
import com.bankingprojet.message.LoginForm;
import com.bankingprojet.security.JwtProvider;
import com.bankingprojet.services.AgenceRepository;
import com.bankingprojet.services.RoleRepository;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;

@Controller
public class BanquierController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
 
	@Autowired
	UserRepository userRepository;
	@Autowired
	AgenceRepository agenceRepository;
	@Autowired
	VirementRepository virementRepository;
	@Autowired
	com.bankingprojet.services.CompteRepository CompteRepository;
	User username=new User();
	String agent;
	@RequestMapping("/")
	public String login(@Valid User user, BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()){
            System.out.println("There was a error "+bindingResult);

            return "login";
        }
		
		return "login";
	}
	@RequestMapping("/banquier/form/{id}")
	public String edit(@PathVariable("id") long id,Model model)
	{
		Optional<User> user=userRepository.findById(id);
		User _user=user.get();
		model.addAttribute("user", _user);
		model.addAttribute("userToedit", new User()); 
		model.addAttribute("agent",agent);

		return "form";
	}
	@RequestMapping("/banquier/inscription")
	public String Inscription(@Valid User user, BindingResult bindingResult, Model model)
	{
		 model.addAttribute("usser", new User()); 
		  if (bindingResult.hasErrors()) {
	            return "";
	        }
			model.addAttribute("agent",agent);

		return "Inscription";
	}
	@RequestMapping("/banquier/inscription/Ajout")
	public String Inscription_Ajout(@Valid User user, BindingResult bindingResult, Model model)
	{
		 userRepository.save(user);
		 Role pmRole = roleRepository.findByName(Rolename.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			Set<Role> roles = new HashSet<>();
			roles.add(pmRole);		
			user.setRoles(roles);
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			model.addAttribute("List_users",userRepository.findBySpecificRoles(1));
			model.addAttribute("List_agences",agenceRepository.findAll());
			model.addAttribute("agent",agent);


		return "homeBanquier";
	}
	@RequestMapping("/banquier/Ajout_Compte")
		public String Ajout_Compte( Model model)
		{
		model.addAttribute("agent",agent);

			model.addAttribute("Compte",new Compte());
			return "Ajout_Compte";
		}	
	@RequestMapping("/banquier/Ajout_Compte/exec")
	public String Ajout_Compte_exec(@Valid Compte Compte, BindingResult bindingResult, Model model)
	{
		if(!(CompteRepository.existsByTypeAndIduser(Compte.getType(), Compte.getIduser()))) {
		Compte.setEtat(true);
		CompteRepository.save(Compte);
		}
		model.addAttribute("List_users",userRepository.findBySpecificRoles(1));
		model.addAttribute("List_agences",agenceRepository.findAll());
		model.addAttribute("agent",agent);
		return "homeBanquier";
	}	
	@RequestMapping("/banquier/ActivationCompte")
	public String activer_compte(Model model)
	{
		model.addAttribute("Compte",new Compte());
		model.addAttribute("agent",agent);

		
		return "Activer_compte";
	}
	@RequestMapping("/banquier/Activation_compte/exec")
	public String activer_compte_exec(@Valid Compte compte,BindingResult fe,Model model)
	{
		 Compte inbound = new Compte();
		Optional<Compte> existing =CompteRepository.findById(compte.getIdcompte());
		inbound=existing.get();
		if(inbound.isEtat()==false) {		inbound.setEtat(true);}
		else {inbound.setEtat(false);}
		CompteRepository.save(inbound);
		model.addAttribute("List_users",userRepository.findBySpecificRoles(1));
		model.addAttribute("agent",agent);

			return "homeBanquier";
	}
	
	@RequestMapping("/banquier/agences")
	public String agences(Model model)
	{
		model.addAttribute("List_agences",agenceRepository.findAll());

		model.addAttribute("agent",agent);

		return "List_agences";
	}
	@PostMapping("/banquier/exec_versement")
	public String exec_versement(@Valid Virement virement,BindingResult fe,Model model)
	{
		java.util.Date date = new java.util.Date();
		String format = "dd/MM/yy H:mm:ss"; 
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
		System.out.println("virement");
		String datevirement = formater.format(date);
		Optional<User> _verseur = userRepository.findById(virement.getIdverseur());		
		Optional<User> _beneficiaire = userRepository.findById(virement.getIdbeneficiaire());
		User verseur = _verseur.get();
		User beneficiaire = _beneficiaire.get();		
		Compte compteverseur = CompteRepository.findByIduserAndTypeAndEtat(verseur.getId(), "Compte courant",true);
		Compte comptebenef = CompteRepository.findByIduserAndTypeAndEtat(beneficiaire.getId(), "Compte courant",true);
		try {
			System.out.print(compteverseur.getSolde());
		}
		
		catch(Exception r)
		{
			System.out.print(r.getMessage());	
		}
		try {
			System.out.print(virement.getMontant());
		}
		
		catch(Exception r)
		{
			System.out.print(r.getMessage());
		}		
		try {		System.out.print("3333333333333333333333333333333333333333333333333333333333333333333333333");
}
		catch(Exception r)
		{
			System.out.print(r.getMessage());
			
		}

		compteverseur.setSolde(compteverseur.getSolde()-virement.getMontant());
		comptebenef.setSolde(comptebenef.getSolde()+virement.getMontant());
		
		CompteRepository.save(compteverseur);
		CompteRepository.save(comptebenef);
		Virement _virement = virementRepository.save(new Virement(virement.getMotif(), virement.getIdbeneficiaire(), verseur.getId(),
				virement.getTypecompte(), virement.getMontant(),datevirement));
		model.addAttribute("List_users",userRepository.findBySpecificRoles(1));
		model.addAttribute("agent",agent);

		return "homeBanquier";
		
	}
	@RequestMapping("/banquier/Virement")
	public String Virement(Model model)
	{
		model.addAttribute("Virement", new Virement());

		model.addAttribute("agent",agent);
		return "Virement";
	}
	@RequestMapping("/banquier/form/edit")
	public String edituser(@Valid User user,Model model)
	{
		Optional<User> existing = userRepository.findById(user.getId());
          User user1=new User();
          user1=user;
          user1.setPassword(existing.get().getPassword());
          user1.setRoles(existing.get().getRoles());
	userRepository.save(user1);
	model.addAttribute("List_users",userRepository.findBySpecificRoles(1));
	model.addAttribute("List_agences",agenceRepository.findAll());
	model.addAttribute("agent",agent);
		return "homeBanquier";
	}
	@PostMapping("/banquier/signin")
	public String authenticateAdmin(@Valid User user,@Valid Agence agence, BindingResult bindingResult, Model model) {
 
		Optional<Role> pmRole = roleRepository.findByName(Rolename.ROLE_PM);
		Role role = pmRole.get();
		if(bindingResult.hasErrors()) {
			System.out.println("error");
			return "login";
		}
		Optional<User> _user = userRepository.findByUsername(user.getUsername());
		
		try {
		User userr = _user.get();
		}catch(Exception e)
		{}
		if( true==true)
		{
			System.out.println("truee");
			
			model.addAttribute("List_users",userRepository.findBySpecificRoles(1));

			username=_user.get();
			agent=_user.get().getUsername();
			model.addAttribute("agent",agent);
			return "homebanquier";
		}
		else
		{
		return "login";	
		}
	}

	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(4);
	}
}
