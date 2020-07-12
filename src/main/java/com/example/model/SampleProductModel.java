package com.example.model;

import lombok.Data;

@Data
public class SampleProductModel {

	private String productName;

	private int price;

	private String uselessProperty = "test";

	public SampleProductModel(String productName, int price) {
		this.productName = productName;
		this.price = price;
	}
}
