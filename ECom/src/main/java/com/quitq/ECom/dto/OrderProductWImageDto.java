package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.OrderProduct;

public class OrderProductWImageDto {

	private OrderProduct orderProduct;

	private List<Image> imageList;

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	public List<Image> getImageList() {
		return imageList;
	}

	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}

	public OrderProductWImageDto(OrderProduct orderProduct, List<Image> imageList) {
		super();
		this.orderProduct = orderProduct;
		this.imageList = imageList;
	}

	public OrderProductWImageDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
