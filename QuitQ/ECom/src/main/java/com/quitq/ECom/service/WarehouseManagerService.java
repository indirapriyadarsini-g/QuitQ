package com.quitq.ECom.service;

import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.WarehouseManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseManagerService {

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;

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
}
