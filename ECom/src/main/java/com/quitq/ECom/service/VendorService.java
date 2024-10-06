package com.quitq.ECom.service;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserInfoRepository;
import com.quitq.ECom.repository.VendorRepository;

@Service
public class VendorService {
	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	UserInfoRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	public Vendor addVendor(Vendor v)
	{
				return vendorRepository.save(v);
		
		
	}
	public List<Vendor> getAll()
	{
		return vendorRepository.findAll();
	}
	public Vendor findById(int vid) throws InvalidIdException
	{
		Optional<Vendor> optional=vendorRepository.findById(vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		Vendor v=optional.get();
		return v;
	}
	public void  delete(String userName) throws InvalidIdException
	{
		Vendor vendor=vendorRepository.getVendorByUsername(userName);
		
		int uid=vendor.getUser().getId();
		vendorRepository.deleteById(vendor.getId());
		userRepository.deleteById(uid);
		
	}
	public Vendor updateVendor(String username,Vendor v) throws InvalidIdException
	{
		Vendor vendor=vendorRepository.getVendorByUsername(username);
		
		
		vendor.setBuisnessName(v.getBuisnessName());
		vendor.setName(v.getName());
		Optional<UserInfo> optionalUser=userRepository.findById(vendor.getUser().getId());
		if(optionalUser.isEmpty())
		{
			throw new InvalidIdException("No user exist");
		}
		UserInfo user=optionalUser.get();
		user.setUsername(v.getUser().getUsername());
		userRepository.save(user);

		vendor.setUser(user);

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
