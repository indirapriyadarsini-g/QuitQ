package com.quitq.ECom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.AddressVendor;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.AddressRepository;
import com.quitq.ECom.repository.AddressVendorRepository;

@Service
public class AddressVendorService {
	@Autowired
	AddressVendorRepository addressVendorRepository;
	@Autowired
	AddressRepository addressRepository;
@Autowired
AddressService addressService;
	public AddressVendor add(Address a,Vendor v,String status) throws InvalidIdException {

		// TODO Auto-generated method stub
		Optional<AddressVendor> optional=addressVendorRepository.findByAddressIdAndVendorId(a.getId(),v.getId());
		if(optional.isEmpty())
		{
			AddressVendor av=new AddressVendor();
			av.setAddress(a);
			if(status.equals("active"))
			{
				Optional<Address> optionalAddress=addressService.getActiveAddressOfVendor(v.getId());
				if(optionalAddress.isEmpty())
				{
					av.setStatus(status);
					av.setVendor(v);
					return addressVendorRepository.save(av);

				}
				else
				{
					AddressVendor addressVendor=addressVendorRepository.findByAddressIdAndVendorId(optionalAddress.get().getId(),v.getId()).get();
					addressVendor.setStatus("inactive");
					av.setVendor(v);
					av.setStatus("active");
					return addressVendorRepository.save(av);
					
				}

			}
			else
			{
				av.setStatus(status);
				av.setVendor(v);
				return addressVendorRepository.save(av);

			}
			
			
		}
		else
		{
			throw new InvalidIdException("You have already added this address");

		}
		
		
	}
	public void changeStatusOfAddress(int vid,int aid,String status) throws InvalidIdException
	{
		Optional<AddressVendor> optional=addressVendorRepository.findByAddressIdAndVendorId(aid,vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("This adress of yours doesnt exist");
		}
		if(status.equals("active"))
		{
			
			AddressVendor av=optional.get();
			Optional<Address> optionalAddress=addressService.getActiveAddressOfVendor(vid);
if(optionalAddress.isEmpty())
{
	av.setStatus("active");
	addressVendorRepository.save(av);
}
else
{
AddressVendor addressVendor=addressVendorRepository.findByAddressIdAndVendorId(optionalAddress.get().getId(),vid).get();
addressVendor.setStatus("inactive");
av.setStatus("active");
addressVendorRepository.save(av);
}
		
			
		}
		else
		{
			if(status.equals("inactive"))
			{
				AddressVendor av=optional.get();
				av.setStatus("inactive");
				addressVendorRepository.save(av);
			}
			else
			{
				throw new InvalidIdException("Status can be either active or inactive");
			}
		}
	}
	public void deleteAddress(int aid,int vid) throws InvalidIdException
	{
		Optional<AddressVendor> optional=addressVendorRepository.findByAddressIdAndVendorId(aid,vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("No such address exist");
		}
		else
		{
			AddressVendor av=optional.get();
			addressVendorRepository.deleteById(av.getId());
			addressRepository.deleteById(av.getAddress().getId());

		}

	}
	public void deleteAllAddress(int vid)
	{
	
	}
	
	

	
	

}

