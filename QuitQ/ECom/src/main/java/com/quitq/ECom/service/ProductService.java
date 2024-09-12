package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.exception.InputValidateionException;
import com.quitq.ECom.exception.InvalidIdException;
import com.quitq.ECom.model.Category;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.repository.CategoryRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.repository.WarehouseManagerRepository;
import com.quitq.ECom.repository.WarehouseRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	WarehouseManagerRepository warehouseManagerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorRepository vendorRepository;
	public Product addProduct(Product p) throws InvalidIdException
	{
		Optional<Warehouse> optionalWarehouse=warehouseRepository.findById(p.getWarehouse().getId()) ;
		if(optionalWarehouse.isEmpty())
		{	
			throw new InvalidIdException("Warehouse id not present");

			
		}
Optional<Category> optional=categoryRepository.findById(p.getC().getId());		
if(optional.isEmpty())
{
	throw new InvalidIdException("Category id not present");
}
p.setC(optional.get());
Optional<Vendor> optionalV=vendorRepository.findById(p.getV().getId());		

if(optionalV.isEmpty())
{
	throw new InvalidIdException("Vendor id not present");

}
p.setV(optionalV.get());
		return productRepository.save(p);
	}
	public List<Product> getAll()
	{
		return productRepository.findAll();
	}
	public Product getById(int pid) throws InvalidIdException
	{
		Optional<Product> optional=productRepository.findById(pid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		return optional.get();
	}
	public void deleteById(int pid) throws InvalidIdException
	{
		Optional<Product> optional=productRepository.findById(pid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		Product p=optional.get();
		p.setStatus("inactive");
		productRepository.save(p);
	}
	public Product updateProduct(Product newProduct,int pid) throws InvalidIdException
	{
		Optional<Product> optional=productRepository.findById(pid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		Product p=optional.get();
		Optional<Warehouse> optionalWarehouse=warehouseRepository.findById(newProduct.getWarehouse().getId()) ;
		if(optionalWarehouse.isEmpty())
		{	
			throw new InvalidIdException("Warehouse id not present");

			
			
		}
		newProduct.setWarehouse(newProduct.getWarehouse());
		warehouseRepository.save(newProduct.getWarehouse());
Optional<Category> optionalC=categoryRepository.findById(newProduct.getC().getId());		
if(optionalC.isEmpty())
{
	throw new InvalidIdException("Category id not present");
}
newProduct.setC(newProduct.getC());
categoryRepository.save(newProduct.getC());
Optional<Vendor> optionalV=vendorRepository.findById(newProduct.getV().getId());		

if(optionalV.isEmpty())
{
	throw new InvalidIdException("Vendor id not present");

}
newProduct.setV(newProduct.getV());
vendorRepository.save(newProduct.getV());
		p.setC(newProduct.getC());
		p.setDiscount(newProduct.getDiscount());
		p.setPrice(newProduct.getPrice());
		p.setTitle(newProduct.getTitle());
		
		return productRepository.save(p);

	}

	public List<Product> findByVendorName(String vName) throws InputValidateionException
	{
		List<Product> p=productRepository.getByVendorName(vName);
		if(p==null||p.isEmpty())
		{
			throw new InputValidateionException("No product exist by this  vendor name");
		}
		return p;
	}
	public List<Product> findByCategoryName(String cName) throws InputValidateionException
	{
		List<Product> p=productRepository.getByCategoryName(cName);
		if(p==null||p.isEmpty())
		{
			throw new InputValidateionException("No product exist by this category name");
		}
		return p;
	}
	public List<Product> findByStatus(String status) throws InputValidateionException
	{
		List<Product> p=productRepository.findByStatus(status);
		if(p==null||p.isEmpty())
		{
			throw new InputValidateionException("No product exist by this status");
		}
		return p;
	}
	public List<Product> findByWarehouseId(int id) throws InputValidateionException
	{
		List<Product> p=productRepository.getByWarehouseId(id);
		if(p==null||p.isEmpty())
		{
			throw new InputValidateionException("No product exist in your warehouse");
		}
		return p;
	}
	
	

	

}