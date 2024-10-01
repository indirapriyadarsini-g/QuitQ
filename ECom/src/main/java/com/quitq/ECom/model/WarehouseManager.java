package com.quitq.ECom.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class WarehouseManager {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id; 
	
	private String name; 
	
	private String contact;
	
	@OneToOne
	private UserInfo userInfo;
	
	@OneToOne
	private Warehouse warehouse;

	public WarehouseManager(int id, String name, String contact, UserInfo user) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.userInfo = userInfo;
	}

	public WarehouseManager(int id) {
        this.id = id;
    }
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo user) {
		this.userInfo = user;
	}

	public WarehouseManager() {
		super();
		// TODO Auto-generated constructor stub
	}

}

