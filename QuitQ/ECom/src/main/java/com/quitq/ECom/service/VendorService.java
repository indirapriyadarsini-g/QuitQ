package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.enums.RoleType;
import com.quitq.ECom.exception.InputValidationException;
import com.quitq.ECom.exception.InvalidIdException;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.VendorRepository;

@Service
public class VendorService {
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
    private ProductService productService;
	public Vendor addVendor(Vendor v)
	{
		User u=v.getUser();
		u.setRole(RoleType.VENDOR);
		u=userService.adduser(u);
		v.setUser(u);
		return vendorRepository.save(v);
		
		
	}
	public List<Vendor> getAll()
	{
		return (List<Vendor>) vendorRepository.findAll();
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
	public void  delete(int vid) throws InvalidIdException
	{
		Optional<Vendor> optional=vendorRepository.findById(vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		int uid=optional.get().getUser().getId();
		vendorRepository.deleteById(vid);
		userRepository.deleteById(uid);
		
	}
	public Vendor updateVendor(Vendor v,int vid) throws InvalidIdException
	{
		Optional<Vendor> optional=vendorRepository.findById(vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		Vendor newVendor=optional.get();
		newVendor.setBuisnessName(v.getBuisnessName());
		newVendor.setName(v.getName());
		newVendor.setUser(v.getUser());
		userRepository.save(v.getUser());
		return vendorRepository.save(newVendor);
	}
	public void validate(Vendor v) throws InputValidationException
	{
		if(v==null)
		{
			throw new InputValidationException("Vendor cannot be null");
		}
		if(v.getBuisnessName()==null||v.getBuisnessName().equals(""))
		{
			throw new InputValidationException("Buisnessname cannot have null value or empty value");

		}
		if(v.getName()==null||v.getName().equals(""))
		{
			throw new InputValidationException("Buisnessname cannot have null value or empty value");

		}
	}
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
	}


}