package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.repository.WarehouseManagerRepository;
import com.quitq.ECom.repository.WarehouseRepository;

@Service
public class WarehouseManagerService {

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;
    
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;

    // Method to get all warehouse managers
    public List<WarehouseManager> getAllWarehouseManagers() {
        return warehouseManagerRepository.findAll();
    }

    // Method to get a specific warehouse manager by ID
    public Optional<WarehouseManager> getWarehouseManagerById(Integer id) {
        return warehouseManagerRepository.findById(id);
    }

    // Method to add a new warehouse manager
    public WarehouseManager addWarehouseManager(WarehouseManager warehouseManager) {
        return warehouseManagerRepository.save(warehouseManager);
    }

    // Method to update a warehouse manager
    public WarehouseManager updateWarehouseManager(Integer id, WarehouseManager warehouseManagerDetails) {
        WarehouseManager warehouseManager = warehouseManagerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("WarehouseManager not found"));

        warehouseManager.setName(warehouseManagerDetails.getName());
        warehouseManager.setContact(warehouseManagerDetails.getContact());

        return warehouseManagerRepository.save(warehouseManager);
    }

    // Method to delete a warehouse manager
    public void deleteWarehouseManager(Integer id) {
        warehouseManagerRepository.deleteById(id);
    }
    // WarehouseManager adds product to warehouse after validation
    public Product addProductToWarehouse(Integer productId, Integer warehouseId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already assigned to a warehouse
        if (product.getWarehouse() != null) {
            throw new RuntimeException("Product is already assigned to a warehouse");
        }

        // Assign the product to the warehouse
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        product.setWarehouse(warehouse);

        // Update the vendor's products list
        Vendor vendor = product.getV();
        vendor.getProducts().add(product);
        vendorRepository.save(vendor);

        return productRepository.save(product);
    }
	 public void sendStockAlerts() {
	        List<Vendor> vendors = StreamSupport.stream(vendorRepository.findAll().spliterator(), false).toList();

	        for (Vendor vendor : vendors) {
	            List<Product> products = productService.getProductsByVendor(vendor.getId());

	            for (Product product : products) {
	            	boolean isAvailable = productService.checkStockLevelAndSendNotification(product);
	                
	            }
	           
	        }
	    }
	
}
