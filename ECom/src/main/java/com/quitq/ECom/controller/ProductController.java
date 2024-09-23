package com.quitq.ECom.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.model.Product;

import com.quitq.ECom.model.UserInfo;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.UserInfoRepository;
import com.quitq.ECom.service.ProductService;
import com.quitq.ECom.service.VendorService;

@RestController
@RequestMapping("/product")
public class ProductController {
@Autowired
ProductService productService;
@Autowired
VendorService vendorService;
@Autowired
UserInfoRepository userRepository;
@PostMapping("/add")
public ResponseEntity<?> addProduct(@RequestBody Product p,Principal pr)

{
	String username=pr.getName();

	try {
		return ResponseEntity.ok(productService.addProduct(p,username));

	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		
		e.printStackTrace();
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}

@GetMapping("/all")
public ResponseEntity<?> getAllProduct(
		@RequestParam(defaultValue="0",required=false)Integer page,
		@RequestParam(defaultValue="1000",required=false)Integer size
		)

{
	Pageable pageAble=PageRequest.of(page,size);

	return ResponseEntity.ok(productService.getAll(pageAble));
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



@DeleteMapping("/delete/v2/{id}")
public ResponseEntity<?> makeProductInactiveV2(Principal p,@PathVariable int id,MessageDto messageDto)
{
	/**/
	String username=p.getName();

	try {
		List<Product> pList=productService.getProductListByVendorUsername(username,id);
		return ResponseEntity.ok("Your product"+pList+" made in active"
				);

	} catch (InvalidIdException e) {
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}

@PutMapping("/update/{id}")
public ResponseEntity<?> updateProduct(Principal pr,@RequestBody Product p,@PathVariable int id,MessageDto messageDto)
{
	String username=pr.getName();


	try {
		List<Product> p1=productService.updateProduct(username,p, id);

		return ResponseEntity.ok(p1);
	} catch (InvalidIdException e) {
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}

@GetMapping("/category/{name}")
public ResponseEntity<?> getProductByCategoryName(Principal pr,@PathVariable String name,MessageDto messageDto)
{
	try {
		List<Product> p=productService.findByCategoryName(pr.getName(),name);

		return ResponseEntity.ok(p);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}

@GetMapping("/vendor/categorySold")
public ResponseEntity<?> getCategorySoldByVendor(Principal p,MessageDto messageDto)
{
	String name=p.getName();
	List<String> c;
	try {
		c = productService.findCategorySoldByVendor(name);
		return ResponseEntity.ok(c);

	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
	
}
@GetMapping("/vendor")
public ResponseEntity<?> getProductByVendorName(Principal pr,MessageDto messageDto)
{
	String username=pr.getName();
	
	try {

		List<Product> p=productService.findByParticularVendorName(username);

		return ResponseEntity.ok(p);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}


@GetMapping("/vendor/status/{status}")
public ResponseEntity<?> getProductByStatusAndVendor(Principal pr,@PathVariable String status,MessageDto messageDto)
{
	String username=pr.getName();

	
	try {
		List<Product> p=productService.findByStatusAndVendor(username,status);

		return ResponseEntity.ok(p);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}

@PutMapping("/vendor/changeStatus/{id}")
public ResponseEntity<?> chanegVendorProductStatus(Principal pr,@PathVariable int id,MessageDto messageDto)
{
	String username=pr.getName();
	
	try {
		Product p=productService.makeProductActive(username,id);

		return ResponseEntity.ok("Product "+p.getTitle()+" made active again ");
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}

	@GetMapping("/vendor/product/outOfStock")
	
		public ResponseEntity<?> outOfStockProduct(Principal pr,MessageDto messageDto)
		{
			String username=pr.getName();
			
			try {
				List<Product> p=productService.findOutOfStockProuct(username);

				return ResponseEntity.ok(p);
			} catch (InvalidIdException e) {
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
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);

	}
}
@GetMapping("/vendor/productName/{name}")
public ResponseEntity<?> getProductByName(Principal pr,@PathVariable String name,MessageDto dto)
{
	String username=pr.getName();
	try {
		List<Product> product=productService.getProductByName(username,name);
		return ResponseEntity.ok(product);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		dto.setMsg(e.getMessage());
		return ResponseEntity.badRequest().body(dto);
	}
}

/*@DeleteMapping("/delete/{id}")
public ResponseEntity<?> makeProductInactive(Principal p,@PathVariable int id,MessageDto messageDto)
{

	
	try {
		productService.deleteByUsername(usernam;
		return ResponseEntity.ok("Product made inactive");
	} catch (InvalidIdException e) {
		messageDto.setMsg(e.getMessage());
		e.printStackTrace();

		return ResponseEntity.badRequest().body(messageDto);
	}
}*/
@PostMapping("/vendor/addAll/upload")
public ResponseEntity<?> uploadProductCsvFile(@RequestBody MultipartFile file,Principal p,MessageDto dto)
{
	String username=p.getName();
	try {
		productService.uploadData(file,username);
		return ResponseEntity.ok("File uploaded successfully");

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		dto.setMsg(e.getMessage());
		return ResponseEntity.badRequest().body(dto);
	}
}




}
