package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.service.MyUserDetailsService;
import com.quitq.ECom.service.VendorService;

@RestController
@CrossOrigin(origins={"http://localhost:4200"})

@RequestMapping("/vendor")
public class VendorController {
	@Autowired
	VendorService vendorService;
	/*
	@Autowired
	AmazonSimpleEmailService service;*/
	 @Autowired
		private UserRepository userRepository;
	 @Autowired
	 private MyUserDetailsService userDetailsService;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
	@PostMapping("/add")
	public ResponseEntity<?> addVendor(@RequestBody Vendor v,MessageDto messageDto)
	{
		User userInfo=v.getUser();
		userInfo.setRole("ROLE_VENDOR");
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userRepository.save(userInfo);
		return ResponseEntity.ok(vendorService.addVendor(v));
		
	}
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllVendor(MessageDto messageDto)
	{
		
		return ResponseEntity.ok(vendorService.getAll());
		
	}
	@GetMapping("/get")
	public ResponseEntity<?> getVendor(Principal p,MessageDto messageDto) throws InvalidIdException
	{
		
		String username=p.getName();
		Vendor v=vendorService.getVendorByUserName(username);
		return ResponseEntity.ok(v);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteVendor(Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
	try {
		vendorService.delete(userName);

		return ResponseEntity.ok("Vendor deleted Successfully");
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();
		return ResponseEntity.badRequest().body(messageDto);
	}	
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateVendor(Principal p,@RequestBody Vendor v,MessageDto messageDto)
	{
		String username=p.getName();
		try {
			Vendor vendor=vendorService.updateVendor(username,v);

			return ResponseEntity.ok(vendor);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/getAllCategory")
	public List<Category> getAllCategory(){
		return List.of(Category.values());
	}
	@GetMapping("/getRandomNumber")
	public Integer getRandomNumber() {
		Random rand = new Random();
		  
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        return Integer.valueOf(rand_int1);
	}
	/*
	@GetMapping("/sendEmail/{number}")
	public String sendEmail(@PathVariable int number){
		Content content=new Content("Verification body");
		Body body=new Body(new Content("This is verification mail body number="+number));
		Message message=new Message(content,body);
		SendEmailRequest mail=new SendEmailRequest("shaikhhuda2810@gmail.com",
				new Destination().withToAddresses("0808cs201091.ies@ipsacademy.org"),message);
		service.sendEmail(mail);
		return "Email Sent";
	}
	*/
	@GetMapping("/resetPassword/{newPassword}")
	public ResponseEntity<?> getOldPassword(Principal p,@PathVariable String newPassword){
		String userName=p.getName();
		Vendor v=vendorService.getVendorByUserName(userName);

		User u=v.getUser();
		u.setPassword(passwordEncoder.encode(newPassword));
    	userRepository.save(u);	
    	return ResponseEntity.ok(u);
    	}
	/*
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getVendorByProductName(@PathVariable int id,MessageDto messageDto)
	{
		Vendor v;
		try {
			v = vendorService.getVendorByProductId(id);
			return ResponseEntity.ok(v);

		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
		}
	@GetMapping("/category/{categoryName}")
	public ResponseEntity<?> getVendorByCategoryName(@PathVariable String name,MessageDto messageDto)
	{
		List<Vendor> v;
		try {
			v = vendorService.getVendorsByCategoryName(name);
			return ResponseEntity.ok(v);

		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			messageDto.setMsg(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(messageDto);
		}
		}
		*/
	}

