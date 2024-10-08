package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;


@Entity
//@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String contact;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name="user")
	private UserInfo userInfo;
	
	

	public Customer(int id, String name, String contact, UserInfo userInfo) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.userInfo = userInfo;
			}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", contact=" + contact + ", userInfo=" + userInfo
				+  "]";
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

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
	

