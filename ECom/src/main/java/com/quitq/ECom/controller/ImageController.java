package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {
	@Autowired
	ImageService imageService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorRepository vendorRepository;
	@PostMapping("/add/{id}")
	public ResponseEntity<?> addImage(@RequestBody MultipartFile image,@PathVariable int id,Principal pr,MessageDto dto)
	{
		String userName=pr.getName();
		
		Image imageSave;
		try {
			imageSave = imageService.addImage(image, id,userName);
			return ResponseEntity.ok(imageSave);

		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
			
		}
	}
	@GetMapping("/getAll/{id}")
	public ResponseEntity<?> getAllImageOfProduct(Principal pr,@PathVariable int id,MessageDto dto)
	{
		String userName=pr.getName();
		
		try {
			List<Image> image=imageService.getAllImageOfProduct(id,userName);
			return ResponseEntity.ok(image);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	@GetMapping("/getSpecificImage/{id}")
	public ResponseEntity<?> getSpecificImageOfProduct(Principal pr,@PathVariable int id,MessageDto dto)
	{
		String userName=pr.getName();
		
		try {
			Image image=imageService.getSpecificImageOfProduct(id,userName);
			return ResponseEntity.ok(image);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		
	}
	@DeleteMapping("/deleteAllImage/{id}")
	public ResponseEntity<?> deleteAllImagesOfProduct(@PathVariable int id,Principal pr,MessageDto dto)
	{
		String userName=pr.getName();
		
try {
	imageService.deleteAllImageOfProduct(id,userName);
	return ResponseEntity.ok("All image deleted succesfullly");
} catch (InvalidIdException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	dto.setMsg(e.getMessage());
	return ResponseEntity.badRequest().body(dto);
}
	}
	@DeleteMapping("/deleteSpecificImage/{id}")
	public ResponseEntity<?> deleteSpecificImagesOfProduct(@PathVariable int id,Principal pr,MessageDto dto)
	{
		String userName=pr.getName();
		
try {
	imageService.deleteSpecificImageOfProduct(id,userName);
	return ResponseEntity.ok("Image deleted succesfullly");
} catch (InvalidIdException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	dto.setMsg(e.getMessage());
	return ResponseEntity.badRequest().body(dto);
}
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateImage(@RequestBody MultipartFile image,
			@PathVariable int id,MessageDto dto,Principal pr)
	{
		String userName=pr.getName();
		try {
	imageService.updateImage(id,userName);
	return ResponseEntity.ok("Image updated succesfullly");
} catch (InvalidIdException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	dto.setMsg(e.getMessage());
	return ResponseEntity.badRequest().body(dto);
}
	}
	@GetMapping("/coverImage/{id}")
	public ResponseEntity<?> coverImage(@PathVariable int id,Principal pr,MessageDto dto)
	{
		String userName=pr.getName();
		User u=userRepository.getUserByUsername(userName);
		Vendor v=vendorRepository.findByUserId(u.getId());
	
			Image image;
			try {
				image = imageService.giveCoverImage(userName, id);
				return ResponseEntity.ok(image);

			} catch (InvalidIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dto.setMsg(e.getMessage());
				return ResponseEntity.badRequest().body(dto);
			}
			
		
		
		
	}
	
	

}
