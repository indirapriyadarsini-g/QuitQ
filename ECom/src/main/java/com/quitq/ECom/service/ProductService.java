package com.quitq.ECom.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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
	public Product addProduct(Product p,String username) throws InvalidIdException
	{
		Vendor vendor=vendorRepository.getVendorByUsername(username);
		
	

		p.setV(vendor);
				
		return productRepository.save(p);
	}
	
	public List<Product> getAll(Pageable pageable)
	{
		return productRepository.findAll(pageable).getContent();

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
	
		
	public List<Product> updateProduct(String username,Product newProduct,int pid) throws InvalidIdException
	{
		List<Product> list=productRepository.findByVendorUsrname(username);
		List<Product> streamList=list.stream().filter(p->p.getId()==pid).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No product exist with this id");
		}
		for(Product p:streamList)
		{
			p.setTitle(newProduct.getTitle());
			p.setQuantity(newProduct.getQuantity());
			p.setPrice(newProduct.getPrice());
			p.setC(newProduct.getC());
			p.setDiscount(newProduct.getDiscount());
		productRepository.save(p);
			
		}
		return streamList;
	
		


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
	public List<Product> findByCategoryName(String username,String cName) throws InvalidIdException
	{
		List<Product> list=productRepository.findByVendorUsrname(username);
		List<Product> streamList=list.stream().filter(p->p.getC().equals(Category.valueOf(cName))).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No product exist with this category name");
		}
		
		return streamList;
	}
	public List<String> findCategorySoldByVendor(String username) throws InvalidIdException
	{
		List<Object[]> list=productRepository.findCategorySoldByVendor(username);
		if(list.isEmpty())
		{
			throw new InvalidIdException("No category");
		}
		List<String> category=new ArrayList<>();
		for(Object obj[]:list)
		{
			category.add(obj[0].toString());
		}
		return category;
	}


	public List<Product> findByParticularVendorName(String username) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Product> p=productRepository.findByVendorUsrname(username);
if(p.isEmpty())
{
	throw new InvalidIdException("You haven't added any product");
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
	public List<Product> findByStatusAndVendor(String username,String status) throws InvalidIdException
	{
		List<Product> p=productRepository.findByStatusAndUsername(status, username);
		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No product exist by this status");
		}
		return p;
	}
	public Product makeProductActive(String username,int pid) throws InvalidIdException
	{
		List<Product> list=productRepository.findByVendorUsrname(username);
		List<Product> streamList=list.stream().filter(p->p.getId()==pid).toList();
		
		for(Product active:streamList)
		{
			active.setStatus("active");
			productRepository.save(active);
			return active;
		}
		throw new InvalidIdException("No product exist with this id");

	

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
	public List<Product> findOutOfStockProuct(String username) throws InvalidIdException
	{
		List<Product> p=productRepository.findOutOfStockProduct(username,true);

		if(p==null||p.isEmpty())
		{
			throw new InvalidIdException("No products out of stock");
		}
		return p;
	}

	public List<Product> getProductListByVendorUsername(String username,int pid) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Product> list=productRepository.findByVendorUsrname(username);
		List<Product> streamList=list.stream().filter(p->p.getId()==pid).toList();
		if(streamList.isEmpty())
		{
			throw new InvalidIdException("No product exist with this id");
		}
		for(Product inActive:streamList)
		{
			inActive.setStatus("inactive");
			productRepository.save(inActive);
		}
		return streamList;
	}

	public List<Product> getProductByName(String username, String name) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Product> product=productRepository.findProductByNameAndUsername(username,name);
		if(product.isEmpty())
		{
			throw new InvalidIdException("No product exist with this name");
		}
		return product;
		
	}

	public void uploadData(MultipartFile file,String username) throws IOException {
		// TODO Auto-generated method stub
		Vendor v=vendorRepository.getVendorByUsername(username);
		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
			br.readLine();
			String data="";
			List<Product> product=new ArrayList<>();
			while((data=br.readLine())!=null)
			{
			


				
				
				Product p=new 
						Product
						(data.split(",")[0],Double.parseDouble(data.split(",")[1]),Double.parseDouble(data.split(",")[2]),data.split(",")[3],Boolean.parseBoolean(data.split(",")[4]),Double.parseDouble(data.split(",")[5]),
						Category.valueOf(data.split(",")[6]),v) ;
				product.add(p);
				
			}
			productRepository.saveAll(product);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Your file cannot be uploaded");
		}
		
		
		
		
	}

	
	



}