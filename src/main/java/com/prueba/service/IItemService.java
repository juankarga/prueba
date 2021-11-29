package com.prueba.service;

import java.util.List;
import java.util.Map;

import com.prueba.model.Item;

public interface IItemService {

	public List<String> calculate(Map<String, Float> items, Float amount);

	public Item findItemMl(String itemId);

}
