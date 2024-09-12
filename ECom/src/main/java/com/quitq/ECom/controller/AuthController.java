package com.quitq.ECom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.model.User;
import com.quitq.ECom.repository.UserRepository;



public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/auth/signup")
    public void signup(@RequestBody User userInfo) {
    	userInfo.setRole(RoleType.USER);
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
    }
}
