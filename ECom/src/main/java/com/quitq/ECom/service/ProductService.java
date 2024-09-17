package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.config.Exception.InvalidIdException;
import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.repository.VendorRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	VendorRepository vendorRepository;
	public Product addProduct(Product p,int vid) throws InvalidIdException
	{
		Optional<Vendor> optional=vendorRepository.findById(vid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Vendor id not valid");
		}
		Vendor vendor=optional.get();
		p.setV(vendor);
				
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
	
	public void deleteById(Vendor v,int pid) throws InvalidIdException
	{
		Optional<Product> optional=productRepository.findById(pid);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Id not valid");
		}
		
		Product p=optional.get();
		if(p.getV().getId()==v.getId())
		{
			p.setStatus("inactive");
			productRepository.save(p);
		}
		else
		{
			throw new InvalidIdException("You cannot delete this product");
		}
		
	}
	
	public Product updateProduct(Vendor v,Product newProduct,int pid) throws InvalidIdException
	{
		Optional<Product> optionalProduct=productRepository.findById(pid);
		if(optionalProduct.isEmpty())
		{
			throw new InvalidIdException("Product id not valid");
		}
	if(v.getId()==optionalProduct.get().getV().getId())
	{
		Product p=optionalProduct.get();
		p.setTitle(newProduct.getTitle());
		p.setDiscount(newProduct.getDiscount());
		p.setPrice(newProduct.getPrice());
		p.setQuantity(newProduct.getQuantity());
		p.setC(newProduct.getC());
		return productRepository.save(p);

	}
		
	throw new InvalidIdException("You cannot update this product");

	}

	public List<Product> findByVendorName(String name) throws InvalidIdException
	{
		List<Product> p=productRepository.getByVendorName(name);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this  vendor name");
		}
		return p;
	}
	public List<Product> findByCategoryName(String cName) throws InvalidIdException
	{
		List<Product> p=productRepository.getByCategoryName(Category.valueOf(cName));
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this category name");
		}
		return p;
	}


	public List<Product> findByParticularVendorName(Vendor v) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Product> p=productRepository.getAllProductOfVendor(v.getId());
		if(p.isEmpty())
		{
			throw new InvalidIdException("This vendor doesn't have any product");
		}
		return p;
	}
	
	public List<Product> findByStatus(String status) throws InvalidIdException
	{
		List<Product> p=productRepository.findByStatus(status);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this status");
		}
		return p;
	}
	public List<Product> findByStatusAndVendor(Vendor v,String status) throws InvalidIdException
	{
		List<Product> p=productRepository.findByStatusAndVendor(status,v.getId());
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this status for you vendor name"+v.getName());
		}
		return p;
	}
	public Product makeProductActive(Vendor v,int pid) throws InvalidIdException
	{
		Optional<Product> p=productRepository.findById(pid);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this status for you vendor name"+v.getName());
		}
		Product product=p.get();
		if(product.getV().getId()==v.getId())
		{
			product.setStatus("active");		
			return productRepository.save(product);
		}
throw new InvalidIdException("Ypou cannot change status for this product");
	}

	public List<Product> findByWarehouseId(int id) throws InvalidIdException
	{
		List<Product> p=productRepository.getByWarehouseId(id);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist in your warehouse");
		}
		return p;
	}
	public List<Product> findOutOfStockProuct(Vendor v) throws InvalidIdException
	{
		List<Product> p=productRepository.findOutOfStockProduct(v.getId(),true);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No products out of stock");
		}
		return p;
	}
	
	



}