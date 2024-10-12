package com.quitq.ECom.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.model.User;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.UserRepository;
import com.quitq.ECom.service.WarehouseManagerService;
import com.quitq.ECom.service.WarehouseService;


@RestController
@RequestMapping("/warehouse-manager")
public class WarehouseManagerController {
	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseManagerService warehouseManagerService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
	private UserRepository userRepository;


    @GetMapping("/getwmgr/{warehouseId}")
    public WarehouseManager getWarehouseManagerByWarehouseId(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.getWarehouseById(id);
        return warehouseManagerService.getWarehouseManagerByWarehouse(warehouse);
    }
    @PostMapping("/createmgr")
    public ResponseEntity<?> createWarehouseManager(@RequestBody WarehouseManager warehouseManager, Principal principal, MessageDto dto) {
        try {
            User userInfo = userRepository.getUserByUsername(principal.getName());
            
            WarehouseManager mgr = new WarehouseManager();
            mgr.setContact(warehouseManager.getContact());
            mgr.setName(warehouseManager.getName());
            mgr.setUserInfo(userInfo);
            mgr.getUserInfo().setRole("ROLE_WAREHOUSEMANAGER");
            
            WarehouseManager newManager = warehouseManagerService.createWarehouseManager(mgr);
            
            logger.info("Warehouse Manager Profile Registered");
            return ResponseEntity.ok(newManager);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            dto.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
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