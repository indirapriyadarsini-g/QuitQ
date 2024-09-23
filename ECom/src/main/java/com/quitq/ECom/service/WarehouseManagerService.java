

package com.quitq.ECom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quitq.ECom.model.AddressVendor;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;
import com.quitq.ECom.model.Warehouse;
import com.quitq.ECom.model.WarehouseManager;
import com.quitq.ECom.repository.AddressVendorRepository;
import com.quitq.ECom.repository.ProductRepository;
import com.quitq.ECom.repository.WarehouseManagerRepository;
import com.quitq.ECom.repository.WarehouseRepository;

@Service
public class WarehouseManagerService {

    @Autowired
    private WarehouseManagerRepository warehouseManagerRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressVendorRepository addressVendorRepository;

    public WarehouseManager createWarehouseManager(WarehouseManager warehouseManager) {
        return warehouseManagerRepository.save(warehouseManager);
    }

    public WarehouseManager updateWarehouseManager(WarehouseManager warehouseManager) {
        return warehouseManagerRepository.save(warehouseManager);
    }

    public void deleteWarehouseManager(int warehouseManagerId) {
        warehouseManagerRepository.deleteById(warehouseManagerId);
    }

    public WarehouseManager getWarehouseManagerByWarehouse(Optional<Warehouse> warehouse) {
        return warehouseManagerRepository.findByWarehouse(warehouse);
    }

    public ResponseEntity<String> getWarehouseAddress(Integer warehouseId) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        if (!warehouseOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Warehouse not found with ID: " + warehouseId);
        }
        return ResponseEntity.ok(warehouseOptional.get().getCity());
    }

    public List<Product> getProductsNotInWarehouse(Integer warehouseId) {
        return warehouseManagerRepository.findProductsNotInWarehouse(warehouseId);
    }

    public ResponseEntity<?> fetchStockQtyByProductName(String productName) {
        Optional<Product> productOptional = warehouseManagerRepository.findByName(productName);
        if (!productOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found with name: " + productName);
        }
        return ResponseEntity.ok(productOptional.get().getQuantity());
    }

    public List<Product> getStockLessThan(Integer threshold) {
        return warehouseManagerRepository.findByQuantityLessThan(threshold);
    }

    public List<Product> getIsZeroStock() {
        return warehouseManagerRepository.findByQuantityLessThan(0);
    }

    public ResponseEntity<?> addProductToWarehouse(int warehouseId, int productId) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (!warehouseOptional.isPresent() || !productOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Warehouse or product not found");
        }

        Warehouse warehouse = warehouseOptional.get();
        Product product = productOptional.get();

        Vendor vendor = product.getV();
        if (vendor != null) {
            List<AddressVendor> addressVendors = addressVendorRepository.findByVendor(vendor);
            Optional<AddressVendor> addressVendor = addressVendors.stream()
                    .filter(av -> av.getAddress().getCity().equals(warehouse.getCity()) && av.getStatus().equals("active"))
                    .findFirst();

            if (addressVendor.isPresent()) {
                product.setWarehouse(warehouse);
                productRepository.save(product);
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.badRequest().body("Product vendor address does not match warehouse city or vendor address is not active");
            }
        }

        return ResponseEntity.badRequest().body("Product does not have a vendor assigned");
    }
}