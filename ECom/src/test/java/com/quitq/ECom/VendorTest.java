package com.quitq.ECom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.service.VendorService;

@SpringBootTest
public class VendorTest {
	@Mock
	VendorRepository vendorRepository;
	@InjectMocks
	VendorService vendorService;
	@Test
	public void addVendorTest()
	{
		Vendor v=new Vendor();
		v.setId(1);
		v.setBuisnessName("XYZ");
		v.setName("Harish");
		User u=new User();
		u.setId(1);
		u.setRole("ROLE_VENDOR");
		u.setPassword("12345");
		u.setUsername("xyz@gmail.com");
		v.setUser(u);
		when(vendorService.addVendor(v)).thenReturn(v);
		assertNotNull(vendorService.addVendor(v));
		}
	@Test
	public void getAllVendor()
	{
		List<Vendor> list=new ArrayList<>();
		User u=new User(1,"xyz@gmail.com","1234","ROLE_VENDOR");
		Vendor v=new Vendor(1,"xyz electroncs","Harish",u);
		list.add(v);
		 u=new User(2,"abc@gmail.com","1234","ROLE_VENDOR");
		 v=new Vendor(2,"abc electroncs","Ramesh",u);
		 list.add(v);
		 u=new User(3,"efg@gmail.com","1234","ROLE_VENDOR");
		 v=new Vendor(3,"efg electroncs","Ramesh",u);
		 list.add(v);
		 when(vendorService.getAll()).thenReturn(list);
		 assertEquals(3,list.size());
		 
	}
	@Test
	public void getVendorById()
	{
		User u=new User(1,"xyz@gmail.com","1234","ROLE_VENDOR");
		Vendor v=new Vendor(1,"xyz electroncs","Harish",u);
	
		 when(vendorService.getVendorByUserId(1)).thenReturn(v);
		 assertEquals(v,vendorService.getVendorByUserId(1));
		 
	}

	

}
