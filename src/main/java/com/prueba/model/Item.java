package com.prueba.model;

import java.io.Serializable;

public class Item implements Serializable {

	private String id;
	private Float price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
