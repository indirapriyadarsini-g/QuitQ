package com.quitq.ECom.controller;

import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    // Get all warehouses
    @GetMapping("/list")
    public List<Warehouse> listAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    // Get warehouse by ID
    @GetMapping("/view/{id}")
    public Optional<Warehouse> viewWarehouseById(@PathVariable Integer id) {
        return warehouseService.getWarehouseById(id);
    }

    // Add a new warehouse
    @PostMapping("/add")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    // Update warehouse by ID
    @PutMapping("/update/{id}")
    public Warehouse updateWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouseDetails) {
        return warehouseService.updateWarehouse(id, warehouseDetails);
    }

    // Delete a warehouse
    @DeleteMapping("/delete/{id}")
    public void deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
    }
    
    @GetMapping("/{warehouseId}/products")
    public List<Product> getProductsByWarehouse(@PathVariable Integer warehouseId) {
        return warehouseService.getProductsByWarehouse(warehouseId);
    }
}
	