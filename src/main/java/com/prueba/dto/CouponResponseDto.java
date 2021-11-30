package com.prueba.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponResponseDto {

	@JsonProperty("item_ids")
	private List<String> itemIds;
	@JsonProperty("total")
	private Float total;

	public List<String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

}
