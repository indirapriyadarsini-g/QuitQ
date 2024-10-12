package com.quitq.ECom.dto;

public class CategoryStatsDto {
private String title;
private int number;
public CategoryStatsDto(String title, int number) {
	super();
	this.title = title;
	this.number = number;
}
public CategoryStatsDto() {
	super();
	// TODO Auto-generated constructor stub
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
}
