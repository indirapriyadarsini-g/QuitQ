package com.quitq.ECom.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String name;
    
    private int capacity;
    
    private String city;

    @OneToMany
    private List<Product> product;
    
    public Warehouse(int id, String name, int capacity, String city, List<Product> product,WarehouseManager warehouseManager) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.city = city;
		this.product = product;
		this.warehouseManager = warehouseManager;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Warehouse(int id, String name, int capacity, String city, WarehouseManager warehouseManager) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.city = city;
		this.warehouseManager = warehouseManager;
	}
	public Warehouse() {
    }

	@OneToOne
    private WarehouseManager warehouseManager;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public WarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(WarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}
    
    
    
}

