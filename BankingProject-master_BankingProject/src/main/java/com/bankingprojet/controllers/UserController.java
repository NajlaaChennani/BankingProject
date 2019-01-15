package com.bankingprojet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingprojet.entities.User;
import com.bankingprojet.services.UserRepository;


@CrossOrigin(origins = "*")
@RestController
public class UserController {
	@Autowired
	UserRepository repository;
	
	@GetMapping("/api/banking/user")
	@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return ">>> User Contents!";
	}
	 
}
