package com.bankingprojet.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;

import com.bankingprojet.entities.Role;
import com.bankingprojet.entities.Rolename;
import com.bankingprojet.entities.User;
import com.bankingprojet.message.JwtResponse;
import com.bankingprojet.message.LoginForm;
import com.bankingprojet.security.JwtProvider;
import com.bankingprojet.services.RoleRepository;
import com.bankingprojet.services.UserRepository;

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
	
	@GetMapping("/")
	public String login(@Valid User user, BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()){
            System.out.println("There was a error "+bindingResult);

            return "login";
        }
		return "login";
	}
	
	@PostMapping("/banquier/signin")
	public String authenticateAdmin(@Valid User user, BindingResult bindingResult, Model model) {
 
		Optional<Role> pmRole = roleRepository.findByName(Rolename.ROLE_PM);
		Role role = pmRole.get();
		if(bindingResult.hasErrors()) {
			System.out.println("error");
			return "login";
		}
		Optional<User> _user = userRepository.findByUsername(user.getUsername());
		
		User userr = _user.get();

		if(userr != null && this.passwordEncoder().matches(user.getPassword(), userr.getPassword()) && userr.getRoles().contains(role))
		{
			System.out.println("truee");
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
