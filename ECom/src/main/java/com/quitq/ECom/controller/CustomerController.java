package com.quitq.ECom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;
import com.quitq.ECom.service.CustomerService;

@RestController
@RequestMapping("customer/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/login")
	public ResponseEntity<?> customerLogin(@RequestBody User user, MessageDto dto){
		try {
			Boolean isLoggedIn = customerService.login(user);
			return ResponseEntity.ok(new String("User logged in"));
		} catch (Exception e) {
			dto.setMsg("Invalid credentials");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@GetMapping("/register")
	public ResponseEntity<?> customerRegister(@RequestBody Customer customer, MessageDto dto){
		try {
			Boolean isLoggedIn = customerService.register(customer);
			return ResponseEntity.ok(new String("User logged in"));
		} catch (Exception e) {
			dto.setMsg("Invalid credentials");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	
	@GetMapping("/my-cart")
	public ResponseEntity<?> getProductsFromCart(@RequestBody Customer customer,MessageDto dto){
		try {
			Boolean isLoggedIn = customerService.getProductsFromCart(customer);
			return ResponseEntity.ok(new String("User logged in"));
		} catch (Exception e) {
			dto.setMsg("Customer not registered");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	
	
}
