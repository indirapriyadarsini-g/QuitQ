package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String contact;
	
	@OneToOne
	private UserInfo user;

	public Customer(int id, String name, String contact, UserInfo user) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", contact=" + contact + ", user=" + user + "]";
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
		return user;
	}

	public void setUserInfo(UserInfo user) {
		this.user = user;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
	

