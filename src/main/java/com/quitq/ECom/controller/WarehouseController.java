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

    @GetMapping("/{city}")
    public Warehouse getWarehouseByCity(@PathVariable String city) {
        return warehouseService.getWarehouseByCity(city);
    }
    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.createWarehouse(warehouse);
    }

    @PutMapping("/{id}")
    public Warehouse updateWarehouse(@PathVariable int id, @RequestBody Warehouse warehouse) { 
    	warehouse.setId(id);
        return warehouseService.updateWarehouse(warehouse);

    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable int id) {
        warehouseService.deleteWarehouse(id);
    }
    
    @PostMapping("/warehouses/{warehouseId}/products/{productId}")
    public ResponseEntity<Product> addProductToWarehouse(@PathVariable("warehouseId") int warehouseId,
                                                          @PathVariable("productId") int productId) {
      Product product = warehouseManagerService.addProductToWarehouse(warehouseId, productId);
      if (product != null) {
        return ResponseEntity.ok(product);
      } else {
        return ResponseEntity.notFound().build();
      }
    }
    
    @GetMapping("/warehouses/{warehouseId}/products/not-in")
    public ResponseEntity<List<Product>> getProductsNotInWarehouse(@PathVariable("warehouseId") Integer warehouseId) {
      List<Product> products = warehouseManagerService.getProductsNotInWarehouse(warehouseId);
      return ResponseEntity.ok(products);
    }
    
    @GetMapping("/warehouses/{productName}/stock-quantity")
    public ResponseEntity<Double> fetchStockQtyByProductName(@PathVariable("productName") String productName) {
      Double quantity = warehouseManagerService.fetchStockQtyByProductName(productName);
      if (quantity != null) {
        return ResponseEntity.ok(quantity);
      } else {
        return ResponseEntity.notFound().build();
      }
    }
    
    @GetMapping("/warehouses/zero-stock")
    public ResponseEntity<List<Product>> getIsZeroStock() {
      List<Product> products = warehouseManagerService.getIsZeroStock();
      return ResponseEntity.ok(products);
    }
}