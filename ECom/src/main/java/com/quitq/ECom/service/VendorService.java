package com.quitq.ECom.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.VendorRepository;

@Service
public class VendorService {
	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	Logger log=LoggerFactory.getLogger(this.getClass());
	public Vendor addVendor(Vendor v)
	{
		log.info("Venor about to get saved");
				return vendorRepository.save(v);

		
		
	}
	public List<Vendor> getAll()
	{
		return vendorRepository.findAll();
	}
	public Vendor findById(int vid) throws InvalidIdException
	{
		log.info("Getting vendor from vendor Id");

		Optional<Vendor> optional=vendorRepository.findById(vid);
		if(optional.isEmpty())
		{
			log.warn("Vendor not found");
			throw new InvalidIdException("Id not valid");
		}
		log.info("Vendor id is present");

		Vendor v=optional.get();
		return v;
	}
	public void  delete(String userName) throws InvalidIdException
	{
		log.info("Getting vendor from vendor username");

		Vendor vendor=vendorRepository.getVendorByUsername(userName);
		
		int uid=vendor.getUser().getId();
		log.info("Vendor found and vendor gets deleted");

		vendorRepository.deleteById(vendor.getId());
		log.info("Vendor found and user gets deleted");

		userRepository.deleteById(uid);
		
	}
	public Vendor updateVendor(String username,Vendor v) throws InvalidIdException
	{
		log.info("Getting vendor from vendor username");

		Vendor vendor=vendorRepository.getVendorByUsername(username);
		log.info("Setting the vendor with new request body");

		
		vendor.setBuisnessName(v.getBuisnessName());
		vendor.setName(v.getName());
		Optional<User> optionalUser=userRepository.findById(vendor.getUser().getId());
		if(optionalUser.isEmpty())
		{
			throw new InvalidIdException("No user exist");
		}
		User user=optionalUser.get();
		user.setUsername(v.getUser().getUsername());
		userRepository.save(user);

		vendor.setUser(user);
		log.info("Vendor updated successfully");

		return vendorRepository.save(vendor);
	}
	public Vendor getVendorByUserId(int uid)
	{
		Vendor v=vendorRepository.findByUserId(uid);
		return v;
	}
	public Vendor getVendorByUserName(String username)
	{
		Vendor v=vendorRepository.getVendorByUsername(username);
		return v;
	}

	
	
}
	/*
	public Vendor getVendorByProductId(int id) throws InvalidIdException 
	{
		Vendor v=vendorRepository.findByProductId(id);
		if(v==null)
		{
			throw new InvalidIdException("Not a valid product");
		}
return v;		
	}
	public List<Vendor> getVendorsByCategoryName(String categoryName) throws InvalidIdException 
	{
		List<Vendor> v=vendorRepository.findByCategoryName(categoryName);
		if(v==null)
		{
			throw new InvalidIdException("No vendor with tese category name");
		}
return v;		
	
	

}*/
