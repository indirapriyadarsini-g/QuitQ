package com.quitq.ECom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.User;
import com.quitq.ECom.repository.OrderProductRepository;
import com.quitq.ECom.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


	
	public Boolean login(User user) {
		Optional<User> loginuser = userRepository.getUserById(user.getId());
		if(loginuser.isPresent()) {
			ResponseEntity.ok();
			return true;
		}
		else {
			ResponseEntity.badRequest();
			return false;
		}

	}
}
