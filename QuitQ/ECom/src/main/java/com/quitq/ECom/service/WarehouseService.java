package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.repository.VendorRepository;
import com.quitq.ECom.repository.WarehouseRepository;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;
    
    @Autowired
    private VendorRepository vendorRepository;

    // Method to get all warehouses
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // Method to get a specific warehouse by ID
    public Optional<Warehouse> getWarehouseById(Integer id) {
        return warehouseRepository.findById(id);
    }

    // Method to add a new warehouse
    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    // Method to update a warehouse
    public Warehouse updateWarehouse(Integer id, Warehouse warehouseDetails) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setName(warehouseDetails.getName());
        warehouse.setCapacity(warehouseDetails.getCapacity());


        return warehouseRepository.save(warehouse);
    }

    // Method to delete a warehouse
    public void deleteWarehouse(Integer id) {
        warehouseRepository.deleteById(id);
    }
    
 // Method to fetch products of vendors in a warehouse
    public List<Product> getProductsByWarehouse(Integer warehouseId) {
        return vendorRepository.findProductsByWarehouseId(warehouseId);
    }
}
