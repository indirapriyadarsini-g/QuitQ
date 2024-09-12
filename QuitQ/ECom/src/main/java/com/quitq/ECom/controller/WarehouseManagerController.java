package com.quitq.ECom.controller;

import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.service.WarehouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse-managers")
public class WarehouseManagerController {

    @Autowired
    private WarehouseManagerService warehouseManagerService;

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
}
