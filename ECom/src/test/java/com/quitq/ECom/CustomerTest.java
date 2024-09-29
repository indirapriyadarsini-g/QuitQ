package com.quitq.ECom;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.repository.CustomerRepository;
import com.quitq.ECom.service.CustomerService;

@SpringBootTest
public class CustomerTest {

	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService;
	
	@Test
	public void registerCustomerTest() {
		Customer customer = new Customer();
		customer.setName("May");
		customer.setContact("90909");
		
		UserInfo ui = new UserInfo();
		ui.setId(1);
		ui.setPassword("qwerty");
		ui.setRole("ROLE_USER");
		ui.setUsername("may");
		
		when(customerService.register(customer)).thenReturn(customer);
		
		assertNotNull(customerService.register(customer));
	}
	

	
}
