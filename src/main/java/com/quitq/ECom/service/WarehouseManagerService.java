package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.AddressVendor;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.AddressVendorRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.WarehouseManagerRepository;
import com.quitq.ECom.repository.WarehouseRepository;

@Service
public class WarehouseManagerService {

	@Autowired
	private WarehouseManagerRepository warehouseManagerRepository;
	@Autowired
	private WarehouseRepository warehouseRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AddressVendorRepository addressVendorRepository;

	public WarehouseManager createWarehouseManager(WarehouseManager warehouseManager) {
		return warehouseManagerRepository.save(warehouseManager);
	}

	public WarehouseManager updateWarehouseManager(WarehouseManager warehouseManager) {
		return warehouseManagerRepository.save(warehouseManager);
	}

	public void deleteWarehouseManager(int warehouseManagerId) {
		warehouseManagerRepository.deleteById(warehouseManagerId);
	}

	public WarehouseManager getWarehouseManagerByWarehouse(Warehouse warehouse) {
		return warehouseManagerRepository.findByWarehouse(warehouse);
	}

	public List<Vendor> getAssociatedVendors(WarehouseManager warehouseManager) {
		return warehouseManager.getVendors();
	}

	public String getWarehouseAddress(Integer warehouseId) {
		Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
		if (warehouse != null) {
			return warehouse.getCity();
		}
		return null; // Or throw an exception if warehouse not found
	}

	public List<Product> getProductsNotInWarehouse(Integer warehouseId) {
		return warehouseManagerRepository.findProductsNotInWarehouse(warehouseId);
	}

	public Double fetchStockQtyByProductName(String productName) {
		Product product = warehouseManagerRepository.findByName(productName);
		if (product != null) {
			return product.getQuantity();
		}
		return null; // Or throw an exception if product not found
	}

	public List<Product> getStockLessThan(Integer threshold) {
		return warehouseManagerRepository.findByQuantityLessThan(threshold);
	}

	public List<Product> getIsZeroStock() {
		return warehouseManagerRepository.findByQuantity(0);
	}
	
	public Product addProductToWarehouse(int warehouseId, int productId) {
	    Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
	    Product product = productRepository.findById(productId).orElse(null);

	    if (warehouse != null && product != null) {
	        Vendor vendor = product.getV();
	        if (vendor != null) {
	            // Access address vendors through the AddressVendorRepository
	            List<AddressVendor> addressVendors = addressVendorRepository.findByVendor(vendor);
	            // Filter addresses based on city and status
	            Optional<AddressVendor> addressVendor = addressVendors.stream()
	                    .filter(av -> av.getAddress().getCity().equals(warehouse.getCity()) && av.getStatus().equals("active"))
	                    .findFirst();

	            if (addressVendor.isPresent()) {
	                // Cities match and address is active, proceed with assignment
	                product.setWarehouse(warehouse);
	                productRepository.save(product);
	            } else {
	                // Handle cases where cities don't match or no active address found
	            }
	        } else {
	            // Handle case where vendor is not found
	        }
	    } else {
	        // Handle cases where warehouse or product is not found
	    }
	    return null;
	}

}