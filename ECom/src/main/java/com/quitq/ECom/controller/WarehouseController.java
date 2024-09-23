package com.quitq.ECom.controller;

import java.util.List;

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

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.service.WarehouseManagerService;
import com.quitq.ECom.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private WarehouseManagerService warehouseManagerService;

    @GetMapping("/get/{city}")
    public Warehouse getWarehouseByCity(@PathVariable String city) {
        return warehouseService.getWarehouseByCity(city);
    }
    @PostMapping("/addwh")
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.createWarehouse(warehouse);
    }

    @PutMapping("/updatewh/{id}")
    public Warehouse updateWarehouse(@PathVariable int id, @RequestBody Warehouse warehouse) { 
    	warehouse.setId(id);
        return warehouseService.updateWarehouse(warehouse);

    }

    @DeleteMapping("/deletewh/{id}")
    public void deleteWarehouse(@PathVariable int id) {
        warehouseService.deleteWarehouse(id);
    }
    
    @PostMapping("/addp2w/{warehouseId}/products/{productId}")
    public ResponseEntity<?> addProductToWarehouse(@PathVariable int warehouseId, @PathVariable int productId) {
      ResponseEntity<?> product = warehouseManagerService.addProductToWarehouse(warehouseId, productId);
      if (product != null) {
        return ResponseEntity.ok(product);
      } else {
        return ResponseEntity.notFound().build();
      }
    }
    
    @GetMapping("/getpnotinw/{warehouseId}/products/not-in")
    public ResponseEntity<List<Product>> getProductsNotInWarehouse(@PathVariable Integer warehouseId) {
      List<Product> products = warehouseManagerService.getProductsNotInWarehouse(warehouseId);
      return ResponseEntity.ok(products);
    }
    
    @GetMapping("/getstockbyname/{productName}/stock-quantity")
    public ResponseEntity<?> fetchStockQtyByProductName(@PathVariable String productName) {
      ResponseEntity<?> quantity = warehouseManagerService.fetchStockQtyByProductName(productName);
      if (quantity != null) {
        return ResponseEntity.ok(quantity);
      } else {
        return ResponseEntity.notFound().build();
      }
    }
    
    @GetMapping("/zero-stock")
    public ResponseEntity<List<Product>> getIsZeroStock() {
      List<Product> products = warehouseManagerService.getIsZeroStock();
      return ResponseEntity.ok(products);
    }
}