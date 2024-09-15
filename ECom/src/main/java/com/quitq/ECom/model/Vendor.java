package com.quitq.ECom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity

public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String buisnessName;
	@OneToOne
	private User user;
	public Vendor(int id, String name, String buisnessName, User user) {
		super();
		this.id = id;
		this.name = name;
		this.buisnessName = buisnessName;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", buisnessName=" + buisnessName + ", user=" + user + "]";
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
	public String getBuisnessName() {
		return buisnessName;
	}
	public void setBuisnessName(String buisnessName) {
		this.buisnessName = buisnessName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}