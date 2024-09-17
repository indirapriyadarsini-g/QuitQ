package com.quitq.ECom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.dto.VendorAddressDto;
import com.quitq.ECom.model.Address;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	AddressRepository addressRepository;
	public Address saveAddress(Address a)
	{
		return addressRepository.save(a);
	}
	public Optional<Address> findAll(Address address)
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

}