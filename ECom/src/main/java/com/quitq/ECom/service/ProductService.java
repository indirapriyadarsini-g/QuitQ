package com.quitq.ECom.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.CategoryStatsDto;
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
	Logger log=LoggerFactory.getLogger(this.getClass());

	public Product addProduct(Product p,String username) throws InvalidIdException
	{
		log.info("Getting the vendor of product from username");
		Vendor vendor=vendorRepository.getVendorByUsername(username);
		
	
		log.info("Set the vendor to product");

		p.setV(vendor);
		log.info("Now vendor is about to get save");

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
		log.info("Getting list of product with vendor name");
		List<Product> list=productRepository.findByVendorUsrname(username);
		log.info("Filtering the list obtained with id of product");

		List<Product> streamList=list.stream().filter(p->p.getId()==pid).toList();
		if(streamList.isEmpty())
		{
			log.warn("No product exist with this id");
			throw new InvalidIdException("No product exist with this id");
		}
		for(Product p:streamList)
		{
			log.info("Obtaining the product and setting it with request body");
			p.setTitle(newProduct.getTitle());
			p.setQuantity(newProduct.getQuantity());
			p.setPrice(newProduct.getPrice());
			p.setC(newProduct.getC());
			p.setDiscount(newProduct.getDiscount());
			log.info("Product about to get updated");
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
		log.info("Finding all product sold by vendor");
		List<Product> list=productRepository.findByVendorUsrname(username);
		log.info("Now filter the product based on category");

		List<Product> streamList=list.stream().filter(p->p.getC().equals(Category.valueOf(cName))).toList();
		if(streamList.isEmpty())
		{
			log.warn("No product found of this category");
			throw new InvalidIdException("No product exist with this category name");
		}
		log.info("Product found and list is returned");
		return streamList;
	}
	public List<CategoryStatsDto> findCategorySoldByVendor(String username) throws InvalidIdException
	{
		List<Object[]> list=productRepository.findCategorySoldByVendor(username);
		if(list.isEmpty())
		{
			throw new InvalidIdException("No category");
		}
		List<CategoryStatsDto> category=new ArrayList<>();
		for(Object obj[]:list)
		{
			CategoryStatsDto dto=new CategoryStatsDto();
			dto.setTitle(obj[0].toString());
			dto.setNumber(Integer.parseInt(obj[1].toString()));
			category.add(dto);
		}
		return category;
	}


	public Page<Product> findByParticularVendorName(String username, Pageable pageAble) throws InvalidIdException {
		// TODO Auto-generated method stub
		log.info("finding product page with pagination criteria and username");
		Page<Product> p=productRepository.findByVendorUsrnameAndPage(username,pageAble);
if(p.isEmpty())
{
	log.warn("No product found");
	throw new InvalidIdException("You haven't added any product");
}
log.info("Page is returned");
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
	public List<Product> findInStockProuct(String username) throws InvalidIdException
	{
		List<Product> p=productRepository.findOutOfStockProduct(username,false);

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
			/*It returns an input stream in form of bytes stream to read content of file from */
			inputStream = file.getInputStream();
			
			/*InputStreanReader convets byte stream into character stream*/
			/*Buffered the charters into buffer  for efficient reading of characters*/
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

	public Product hardDelete(String username, int id) {
Product p=productRepository.findById(id).get();
p.setStatus("deleted");
return productRepository.save(p);
	}

	
	



}