package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.dto.VendorAddressDto;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.AddressRepository;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.service.AddressService;
import com.quitq.ECom.service.AddressVendorService;
import com.quitq.ECom.service.VendorService;

@RestController
@RequestMapping("/vendor/address")
public class VendorAddressController {
	@Autowired
	AddressVendorService addressVendorService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorService vendorService;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	AddressService addressService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addAddress(Principal p,@RequestBody Address address,MessageDto dto)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		Optional<Address> optionalAddress=addressService.findAll(address);
		Address a;
		if(optionalAddress.isEmpty())
		{
			a=addressService.saveAddress(address);
		}
		else
		{
			a=optionalAddress.get();
		}
	try {
		return ResponseEntity.ok(addressVendorService.add(a,v));
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		dto.setMsg(e.getMessage());
		return ResponseEntity.badRequest().body(dto);
	}
	
	
		
		
	}
	@GetMapping("/all")
	public ResponseEntity<?> getAllAddressOfVendor(Principal p)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		List<VendorAddressDto> address=addressService.getAllAddressOfVendor(v);
		return ResponseEntity.ok(address);

	}
	

}