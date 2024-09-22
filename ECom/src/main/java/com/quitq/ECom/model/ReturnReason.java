package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReturnReason {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String reason;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public ReturnReason(int id, String reason) {
	super();
	this.id = id;
	this.reason = reason;
}
public ReturnReason() {
	super();
	// TODO Auto-generated constructor stub
}



}
