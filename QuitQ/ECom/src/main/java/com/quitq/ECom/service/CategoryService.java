package com.quitq.ECom.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.Category;
import com.quitq.ECom.repository.CategoryRepository;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category addcategory(Category category) {
		
		
		String categoryName = category.getCategoryName();
		
		category.setCategoryName(categoryName);
		
		return categoryRepository.save(category);
		
	}
	public void validate(Category category) throws Exception {
		
	}
}