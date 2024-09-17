package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> addAddress(Principal p,@RequestBody VendorAddressDto address,MessageDto dto)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		Address temp=new Address();
		temp.setCity(address.getCity());
		temp.setLandmark(address.getLandmark());
		temp.setPincode(address.getPincode());
		temp.setState(address.getState());
		temp.setStreetdetails(address.getStreetdetails());
		String status=address.getStatus();
		if(status==null)
		{
			status="inactive";
		}
		Optional<Address> optionalAddress=addressService.findAll(address);
		Address a;
		if(optionalAddress.isEmpty())
		{
			
			
			a=addressService.saveAddress(temp);
		}
		else
		{
			a=optionalAddress.get();
		}
	try {
		return ResponseEntity.ok(addressVendorService.add(a,v,status));
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
	@GetMapping("/get/{aid}")
	public ResponseEntity<?> getParticularAddressOfVendor(@PathVariable int aid,Principal p)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		List<VendorAddressDto> address=addressService.getAddressOfVendorWithId(v, aid);
		return ResponseEntity.ok(address);

	}
	@DeleteMapping("/delete/{aid}")
	public ResponseEntity<?>  deleteVendorAddress(@PathVariable int aid,Principal p,MessageDto messageDto)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		try {
			addressVendorService.deleteAddress(aid, v.getId());
			return ResponseEntity.ok("Your address is deleted succesfully ");
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@PutMapping("/update/{aid}")
	public ResponseEntity<?> updateVendorAddress(@RequestBody Address address,@PathVariable int aid,Principal p,MessageDto messageDto)
	{

		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		try {
			addressService.updateAddress(address, aid,v.getId());
			return ResponseEntity.ok("Your address is updated");
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
messageDto.setMsg(e.getMessage());
			
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);		}
	}
	@GetMapping("/activeAddress")
	public ResponseEntity<?> getActiveAddressOfVendor(Principal p,MessageDto messageDto)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		Optional<Address> a=addressService.getActiveAddressOfVendor(v.getId());
		if(a.isEmpty())
		{
			return ResponseEntity.ok("You doesn't have any active address please select a address as active address");
		}
		return ResponseEntity.ok(a.get());
	}
	@PutMapping("/changeStatus/{aid}/{status}")
	public ResponseEntity<?> changeStatusOfAddress(@PathVariable String status,@PathVariable int aid,Principal p,MessageDto messageDto)
	{
		String username=p.getName();
		User u=userRepository.getUserByUsername(username);
		Vendor v=vendorService.getVendorByUserId(u.getId());
		try {
			addressVendorService.changeStatusOfAddress(v.getId(), aid, status);
			return ResponseEntity.ok("Your status has been changed to "+status);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
messageDto.setMsg(e.getMessage());
			
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);			}
	}
	

}
