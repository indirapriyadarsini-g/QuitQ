package com.quitq.ECom.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.VendorAddressDto;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.AddressVendor;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.AddressRepository;
import com.quitq.ECom.repository.AddressVendorRepository;

@Service
public class AddressService {
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	AddressVendorRepository addressVendorRepository;
	

	public Address saveAddress(Address a)
	{
		return addressRepository.save(a);
	}
	public Optional<Address> findAll(VendorAddressDto address)

	{
		Optional<Address> optionalAddress=addressRepository.findByAll(address.getCity(),address.getLandmark(),address.getPincode(),address.getState(),address.getStreetdetails());
		return optionalAddress;
	}
	public List<VendorAddressDto> getAllAddressOfVendor(Vendor v) {
		// TODO Auto-generated method stub
		List<Object[]> a=addressRepository.findAddressOfParticularVendor(v.getId());
		List<VendorAddressDto> list=new ArrayList<>();
		for(Object[] o:a)
		{
			VendorAddressDto dto=new VendorAddressDto();
			dto.setStatus(o[0].toString());
			dto.setCity(o[1].toString());
			dto.setState(o[2].toString());
			dto.setLandmark(o[3].toString());
			dto.setStreetdetails(o[4].toString());
			dto.setPincode(Integer.parseInt(o[5].toString()));
			list.add(dto);
		}
		return list;
	}
	public List<VendorAddressDto> getAddressOfVendorWithId(Vendor v,int aid)
	{
	
		List<Object[]> a=addressRepository.findParticularAddressOfParticularVendor(v.getId(),aid);
		List<VendorAddressDto> list=new ArrayList<>();

		for(Object[] o:a)
		{
			VendorAddressDto dto=new VendorAddressDto();
			dto.setStatus(o[0].toString());
			dto.setCity(o[1].toString());
			dto.setState(o[2].toString());
			dto.setLandmark(o[3].toString());
			dto.setStreetdetails(o[4].toString());
			dto.setPincode(Integer.parseInt(o[5].toString()));
			list.add(dto);
		}
		return list;
	
		
	}
	public Optional<Address> getActiveAddressOfVendor(int vid)
	{
		Optional<Address> address=addressRepository.findActiveStatusOfVendor("active",vid);
		return address;
	}
	public Address updateAddress(Address a,int aid,int vid) throws InvalidIdException
	{
		Optional<Address> optionalAddress=addressRepository.findById(aid);
		if(optionalAddress.isEmpty())
		{
			throw new InvalidIdException("Address doesnt exist");
		}
		
		Optional<AddressVendor> optional=addressVendorRepository.findByAddressIdAndVendorId(aid, vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("This address is not associated with you ");
		}
		Optional<Address> newAddress=addressRepository.findByAll(a.getCity(),a.getLandmark(),a.getPincode(),a.getState(),a.getStreetdetails());
		if(newAddress.isEmpty())
		{
			a=addressRepository.save(a);
		}
		else
		{
			a=addressRepository.save(newAddress.get());
		}
		AddressVendor av=optional.get();
		av.setAddress(a);
		addressVendorRepository.save(av);
		return a;
		
		
	}
	
	

}
