package com.bankingprojet.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingprojet.entities.Agence;
import com.bankingprojet.entities.Role;
import com.bankingprojet.entities.Rolename;
import com.bankingprojet.entities.User;
import com.bankingprojet.message.ResponseMessage;
import com.bankingprojet.services.AgenceRepository;
import com.bankingprojet.services.RoleRepository;
import com.bankingprojet.services.UserRepository;

@RestController
@RequestMapping(value = "/api/banking")
public class AdminController {
	
	@Autowired 
	AgenceRepository agenceRepository;
	
	@Autowired
	UserRepository userRepository;
 
	@Autowired
	RoleRepository roleRepository;
 
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/addAgence")
	public ResponseEntity<Agence> addAgence(@RequestBody JSONObject requestAgence) {
		Agence agence = new Agence(requestAgence.get("name").toString(), requestAgence.get("adresse").toString());
		agenceRepository.save(agence);
		return new ResponseEntity<Agence>(agence, HttpStatus.CREATED);
	}
	
	@GetMapping("/allagences")
	public ResponseEntity<List<Agence>> allAgences()
	{
		List<Agence> list = new ArrayList<>();
		agenceRepository.findAll().forEach(list::add);
		return new ResponseEntity<List<Agence>>(list, HttpStatus.OK);
	}
	
	@GetMapping("loginAdmin/{username}/{password}")
	public ResponseEntity<String> loginAdmin(@PathVariable String username, @PathVariable String password) {
 
		Optional<Role> adminRole = roleRepository.findByName(Rolename.ROLE_ADMIN);
		Role role = adminRole.get();
		Optional<User> _user = userRepository.findByUsername(username);
		
		User userr = _user.get();

		if(userr != null && this.passwordEncoder().matches(password, userr.getPassword()) && userr.getRoles().contains(role))
		{
			System.out.println("Log admin success");
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		else
		{
			System.out.println("1" + userr.equals(null));
			System.out.println("2" + this.passwordEncoder().matches(password, userr.getPassword()));
			System.out.println("3" + userr.getRoles().contains(role));
			System.out.println("Log admin failed");
			return new ResponseEntity<String>("fail", HttpStatus.OK);
		}
	}
	@PostMapping("/addAgent")
	public ResponseEntity<User> addAgent(@RequestBody JSONObject requestAgent){

		// Creating user's account
		User user = new User(requestAgent.get("name").toString(), requestAgent.get("username").toString(), 
				encoder.encode(requestAgent.get("password").toString()), Integer.parseInt(requestAgent.get("age").toString()), 
				requestAgent.get("phone").toString(), requestAgent.get("address").toString(),
				Double.parseDouble(requestAgent.get("soldetelephonique").toString()));
 
		Set<Role> roles = new HashSet<>();
		Role pmRole = roleRepository.findByName(Rolename.ROLE_PM)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(pmRole);		
		user.setRoles(roles);
		userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/allagents")
	public ResponseEntity<List<User>> allAgents()
	{
        List<User> list = new ArrayList<>();
		userRepository.findBySpecificRoles(2).forEach(list::add);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	@Transactional
	@DeleteMapping("/deleteAgent/{username}")
	public ResponseEntity<?> deleteAgent(@PathVariable("username") String username)
	{
		Optional<User> user = userRepository.findByUsername(username);
		User _user = user.get();
		if(_user.getRoles().size()>1)
		{
			System.out.println("test1");
			userRepository.deleteRoleFromUsersWithRole(2, _user.getId());
			return new ResponseEntity<>(new ResponseMessage("More than 1 role !"), HttpStatus.GONE);
		}
		else
		{
			System.out.println("test2");
			userRepository.deleteByUsername(username);
			return new ResponseEntity<>(new ResponseMessage("Agent deleted successfully!"), HttpStatus.GONE);
		}
	}
	
	@Transactional
	@DeleteMapping("/deleteAgence/{name}")
	public ResponseEntity<Void> deleteAgence(@PathVariable("name") String name){
		agenceRepository.deleteByName(name);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(4);
	}
}
