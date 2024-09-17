package com.quitq.ECom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.AddressVendor;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.AddressVendorRepository;

@Service
public class AddressVendorService {
	@Autowired
	AddressVendorRepository addressVendorRepository;

	public AddressVendor add(Address a,Vendor v) throws InvalidIdException {
		// TODO Auto-generated method stub
		Optional<AddressVendor> optional=addressVendorRepository.findByAddressIdAndVendorId(a.getId(),v.getId());
		if(optional.isEmpty())
		{
			AddressVendor av=new AddressVendor();
			av.setAddress(a);
			av.setStatus("inactive");
			av.setVendor(v);
			return addressVendorRepository.save(av);
		}
		throw new InvalidIdException("You have already added this address");
		
		
	}
	
	

}