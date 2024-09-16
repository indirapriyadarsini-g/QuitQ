package com.quitq.ECom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.repository.UserRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;

	public Boolean login(User user) {
		Optional<User> loginuser = userRepository.findById(user.getId());
		
		return null;
	}

	public Boolean register(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getProductsFromCart(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
