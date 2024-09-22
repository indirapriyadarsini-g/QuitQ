package com.quitq.ECom.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	@Autowired
	VendorService vendorService;
	 @Autowired
		private UserRepository userRepository;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
	@PostMapping("/add")
	public ResponseEntity<?> addVendor(@RequestBody Vendor v,MessageDto messageDto)
	{
		User userInfo=v.getUser();
		userInfo.setRole("ROLE_VENDOR");
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
		return ResponseEntity.ok(vendorService.addVendor(v));
		
	}
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllVendor(MessageDto messageDto)
	{
		
		return ResponseEntity.ok(vendorService.getAll());
		
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getVendor(@PathVariable int id,MessageDto messageDto)
	{
		
		try {
			Vendor v=vendorService.findById(id);
			return ResponseEntity.ok(v);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteVendor(Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
	try {
		vendorService.delete(userName);
		return ResponseEntity.ok("Vendor deleted Successfully");
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(messageDto);
	}	
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateVendor(Principal p,@RequestBody Vendor v,MessageDto messageDto)
	{
		String username=p.getName();
		try {
			Vendor vendor=vendorService.updateVendor(username,v);
			return ResponseEntity.ok(vendor);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	/*
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getVendorByProductName(@PathVariable int id,MessageDto messageDto)
	{
		Vendor v;
		try {
			v = vendorService.getVendorByProductId(id);
			return ResponseEntity.ok(v);

		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
		}
	@GetMapping("/category/{categoryName}")
	public ResponseEntity<?> getVendorByCategoryName(@PathVariable String name,MessageDto messageDto)
	{
		List<Vendor> v;
		try {
			v = vendorService.getVendorsByCategoryName(name);
			return ResponseEntity.ok(v);

		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
		}
		*/
	}

