package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
@Builder
public class Image{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id; 

private String imageName;
private String type;
private String status="uncover";
@Lob
private byte[] imageData;
public Image() {
	super();
	// TODO Auto-generated constructor stub
}
public Image(int id, String imageName, String type, String status, byte[] imageData,Product p) {
	super();
	this.id = id;
	this.imageName = imageName;
	this.type = type;
	this.status = status;
	this.imageData = imageData;
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
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public byte[] getImageData() {
	return imageData;
}
public void setImageData(byte[] imageData) {
	this.imageData = imageData;
}

}
