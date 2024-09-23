package com.quitq.ECom.controller;

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


    @GetMapping("/getwmgr/{warehouseId}")
    public WarehouseManager getWarehouseManagerByWarehouseId(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.getWarehouseById(id);
        return warehouseManagerService.getWarehouseManagerByWarehouse(warehouse);
    }
    @PostMapping("/createmgr")
    public WarehouseManager createWarehouseManager(@RequestBody WarehouseManager warehouseManager) {
        return warehouseManagerService.createWarehouseManager(warehouseManager);
    }

    @PutMapping("/updatemgr/{id}")
    public WarehouseManager updateWarehouseManager(@PathVariable int id, @RequestBody WarehouseManager warehouseManager) {
        warehouseManager.setId(id);
        return warehouseManagerService.updateWarehouseManager(warehouseManager);
    }

    @DeleteMapping("/deletemgr/{id}")
    public void deleteWarehouseManager(@PathVariable int id) {
        
    	warehouseManagerService.deleteWarehouseManager(id);
    }
    
    
}