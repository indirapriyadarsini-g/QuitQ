package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Product;

public class ProductWImageDto {
private Product product;
private List<Image> imageList;
public Product getProduct() {
	return product;
}
public void setProduct(Product product) {
	this.product = product;
}
public List<Image> getImageList() {
	return imageList;
}
public void setImageList(List<Image> imageList) {
	this.imageList = imageList;
}
public ProductWImageDto(Product product, List<Image> imageList) {
	super();
	this.product = product;
	this.imageList = imageList;
}
public ProductWImageDto() {
	super();
	// TODO Auto-generated constructor stub
}
}
