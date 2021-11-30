package com.prueba.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponRequestDto {

	@JsonProperty("item_ids")
	private List<String> itemIds;
	@JsonProperty("amount")
	private Float amount;

	public List<String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}
