package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity

public class Image{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id; 

private String imageName;
private String status="uncover";


public Image() {
	super();
	// TODO Auto-generated constructor stub
}
public Image(int id, String imageName, String status,Product p) {
	super();
	this.id = id;
	this.imageName = imageName;
	this.status = status;

	this.p=p;
}
@ManyToOne
private Product p;
public Product getP() {
	return p;
}
public void setP(Product p) {
	this.p = p;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getImageName() {
	return imageName;
}
public void setImageName(String imageName) {
	this.imageName = imageName;
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
