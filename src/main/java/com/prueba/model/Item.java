package com.prueba.model;

import java.io.Serializable;

public class Item implements Serializable {

	private String id;
	private Double amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
