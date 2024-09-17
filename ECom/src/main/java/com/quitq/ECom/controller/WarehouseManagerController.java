package com.quitq.ECom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.service.WarehouseManagerService;
import com.quitq.ECom.service.WarehouseService;


@RestController
@RequestMapping("/warehouse-manager")
public class WarehouseManagerController {

    @Autowired
    private WarehouseManagerService warehouseManagerService;
    @Autowired
    private WarehouseService warehouseService;


    @GetMapping("/{warehouseId}")
    public WarehouseManager getWarehouseManagerByWarehouseId(@PathVariable Integer id) {
        // Retrieve warehouse based on warehouseId
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return warehouseManagerService.getWarehouseManagerByWarehouse(warehouse);
    }
    @PostMapping
    public WarehouseManager createWarehouseManager(@RequestBody WarehouseManager warehouseManager) {
        return warehouseManagerService.createWarehouseManager(warehouseManager);
    }

    @PutMapping("/{id}")
    public WarehouseManager updateWarehouseManager(@PathVariable int id, @RequestBody WarehouseManager warehouseManager) {
        warehouseManager.setId(id);
        return warehouseManagerService.updateWarehouseManager(warehouseManager);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouseManager(@PathVariable int id) {
        
    	warehouseManagerService.deleteWarehouseManager(id);
    }
    
    
}