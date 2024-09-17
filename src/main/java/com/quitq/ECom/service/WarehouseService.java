package com.quitq.ECom.service;

import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;
    
    public Warehouse getWarehouseById(Integer warehouseId) {
    	return warehouseRepository.findById(warehouseId).orElse(null);
    }
    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Integer warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }
    public Warehouse getWarehouseByCity(String city) {
        return warehouseRepository.findByCity(city);
    }

}