package com.quitq.ECom.controller;

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

import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.exception.InputValidationException;
import com.quitq.ECom.exception.InvalidIdException;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.service.ProductService;
import com.quitq.ECom.service.VendorService;

@RestController
@RequestMapping("/product")
public class ProductController {
@Autowired
ProductService productService;
@Autowired
VendorService vendorService;
@PostMapping("/add")
public ResponseEntity<?> addProduct(@RequestBody Product p)
{
	try {
		return ResponseEntity.ok(productService.addProduct(p));
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
@GetMapping("/all")
public ResponseEntity<?> getAllProduct()
{
	return ResponseEntity.ok(productService.getAll());
}
@GetMapping("/find/{id}")
public ResponseEntity<?> getProductById(@PathVariable int id,MessageDto messageDto)
{
	try {
		Product p=productService.getById(id);
		return ResponseEntity.ok(p);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}
@DeleteMapping("/delete/{id}")
public ResponseEntity<?> makeProductInactive(@PathVariable int id,MessageDto messageDto)
{
	try {
		productService.deleteById(id);
		return ResponseEntity.ok("Product made inactive");
	} catch (InvalidIdException e) {
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}
@PutMapping("/update/{id}")
public ResponseEntity<?> updateProduct(@RequestBody Product p,@PathVariable int id,MessageDto messageDto)
{
	try {
		Product p1=productService.updateProduct(p, id);
		return ResponseEntity.ok(p1);
	} catch (InvalidIdException e) {
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}
@GetMapping("/category/{name}")
public ResponseEntity<?> getProductByCategoryName(@PathVariable String name,MessageDto messageDto)
{
	try {
		List<Product> p=productService.findByCategoryName(name);
		return ResponseEntity.ok(p);
	} catch (InputValidationException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}
@GetMapping("/vendorName/{name}")
public ResponseEntity<?> getProductByVendorName(@PathVariable String name,MessageDto messageDto)
{
	try {
		List<Product> p=productService.findByVendorName(name);
		return ResponseEntity.ok(p);
	} catch (InputValidationException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}
@GetMapping("/status/{status}")
public ResponseEntity<?> getProductByStatus(@PathVariable String status,MessageDto messageDto)
{
	try {
		List<Product> p=productService.findByStatus(status);
		return ResponseEntity.ok(p);
	} catch (InputValidationException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}
@GetMapping("/warehouse/{id}")
public ResponseEntity<?> getProductByWarehouseIId(@PathVariable int id,MessageDto messageDto)
{
	try {
		List<Product> p=productService.findByWarehouseId(id);
		return ResponseEntity.ok(p);
	} catch (InputValidationException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}


}