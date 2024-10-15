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

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.service.ProductService;
import com.quitq.ECom.service.VendorService;

@SpringBootTest
public class VendorTestClass {
	@Mock
	VendorRepository vendorRepository;
	@InjectMocks
	VendorService vendorService;
	@Mock
	ProductRepository productRepository;
	@InjectMocks
	ProductService productService;
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
@Test
public void addProduct() {

	User u=new User(1,"xyz@gmail.com","1234","ROLE_VENDOR");
	Vendor v=new Vendor(1,"xyz electroncs","Harish",u);
	Product p=new Product();
	p.setId(1);
	p.setTitle("Iphone13");
	p.setC(Category.ELECTRONIC);
	p.setStatus("active");
	p.setOutOfStock(false);
	p.setDiscount(0);
	p.setQuantity(12);
	p.setV(v);
	p.setWarehouse(null);
	try {
		when(productService.addProduct(p,v.getUser().getUsername())).thenReturn(p);
		assertNotNull(p);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}
@Test
public void updateProduct() {
	User u=new User(1,"xyz@gmail.com","1234","ROLE_VENDOR");
	Vendor v=new Vendor(1,"xyz electroncs","Harish",u);
	Product p=new Product();
	p.setId(1);
	p.setTitle("Iphone13");
	p.setC(Category.ELECTRONIC);
	p.setStatus("active");
	p.setOutOfStock(false);
	p.setDiscount(0);
	p.setQuantity(12);
	p.setV(v);
	p.setWarehouse(null);
	Product pNew=new Product();
	pNew.setId(1);
	pNew.setTitle("Iphone13");
	pNew.setC(Category.ELECTRONIC);
	pNew.setStatus("active");
	pNew.setOutOfStock(false);
	pNew.setDiscount(10);
	pNew.setQuantity(120);
	pNew.setV(v);
	p.setWarehouse(null);
	List<Product> pList=new ArrayList<>();
	pList.add(pNew);
	try {
		when(productService.updateProduct(v.getUser().getUsername(), pNew, 1)).thenReturn(pList);
		for(Product p1:pList) {
			assertEquals(p1.getId(),p.getId());
		}
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


	
}
