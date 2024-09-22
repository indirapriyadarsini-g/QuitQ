package com.quitq.ECom.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class WarehouseManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne
    private Warehouse warehouse;
    

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

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String toString() {
		return "WarehouseManager [id=" + id + ", name=" + name + ", warehouse=" + warehouse + "]";
	}

	public WarehouseManager(int id, String name, Warehouse warehouse) {
		super();
		this.id = id;
		this.name = name;
		this.warehouse = warehouse;
	}

	public WarehouseManager() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    

}