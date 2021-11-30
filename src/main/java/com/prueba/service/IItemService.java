package com.prueba.service;

import java.util.List;
import java.util.Map;

public interface IItemService {

	public Map<String, Float> mapItems(List<String> itemIds, Float couponAmount);

}
