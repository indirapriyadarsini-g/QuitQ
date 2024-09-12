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


import com.quitq.ECom.model.Category;
import com.quitq.ECom.service.CategoryService;



@RestController //@Controller + @ResponseBody: JSON 
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	public ResponseEntity<?> addCategory(@RequestBody Category category) { //reading the i/p
		
		try {
			categoryService.validate(category);
		} catch (Exception e) {
				System.out.println("Check");
			 return null; 
		} 
		
		
		return ResponseEntity.ok(categoryService.addcategory(category)); 
	}

}