package com.quitq.ECom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.quitq.ECom.enums.StatusType;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;
import com.quitq.ECom.repository.CartProductRepository;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.service.CustomerService;

@SpringBootTest
public class CustomerTest {

	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService;
	
	@Mock
	CartProductRepository cprepo;
	
	
	
	@Test
	public void registerCustomerTest() {
		Customer customer = new Customer();
		customer.setName("May");
		customer.setContact("90909");
		
		User ui = new User();
		ui.setId(1);
		ui.setPassword("qwerty");
		ui.setRole("ROLE_USER");
		ui.setUsername("may");
		
		when(customerService.register(customer)).thenReturn(customer);
		
		assertNotNull(customerService.register(customer));
	}
	
	@Test
	public void getProfileTest() {
		Customer customer = new Customer();
		customer.setName("May");
		customer.setContact("90909");
		
		User ui = new User();
		ui.setId(1);
		ui.setPassword("qwerty");
		ui.setRole("ROLE_USER");
		ui.setUsername("may");
		
		when(customerService.register(customer)).thenReturn(customer);
		when(customerService.getProfileDetails(ui.getUsername())).thenReturn(customer);
		
		assertEquals(customerService.register(customer),customerService.getProfileDetails(ui.getUsername()));
	}
	
	

	
}
