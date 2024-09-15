package com.quitq.ECom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.service.WarehouseManagerService;

@RestController
@RequestMapping("/api/warehouse-managers")
public class WarehouseManagerController {

    @Autowired
    private WarehouseManagerService warehouseManagerService;
    @Autowired
	private VendorRepository vendorRepository;

    // Get all warehouse managers
    @GetMapping("/list")
    public List<WarehouseManager> listAllWarehouseManagers() {
        return warehouseManagerService.getAllWarehouseManagers();
    }

    // Get warehouse manager by ID
    @GetMapping("/view/{id}")
    public Optional<WarehouseManager> viewWarehouseManagerById(@PathVariable Integer id) {
        return warehouseManagerService.getWarehouseManagerById(id);
    }

    // Add a new warehouse manager
    @PostMapping("/add")
    public WarehouseManager addWarehouseManager(@RequestBody WarehouseManager warehouseManager) {
        return warehouseManagerService.addWarehouseManager(warehouseManager);
    }

    // Update warehouse manager by ID
    @PutMapping("/update/{id}")
    public WarehouseManager updateWarehouseManager(@PathVariable Integer id, @RequestBody WarehouseManager warehouseManagerDetails) {
        return warehouseManagerService.updateWarehouseManager(id, warehouseManagerDetails);
    }

    // Delete a warehouse manager
    @DeleteMapping("/delete/{id}")
    public void deleteWarehouseManager(@PathVariable Integer id) {
        warehouseManagerService.deleteWarehouseManager(id);
    }
    
    // Endpoint to assign a product to a warehouse
    @PutMapping("/add-product-to-warehouse/{productId}/{warehouseId}")
    public Product addProductToWarehouse(@PathVariable Integer productId, @PathVariable Integer warehouseId) {
        return warehouseManagerService.addProductToWarehouse(productId, warehouseId);
    }
}
