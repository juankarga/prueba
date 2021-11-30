package com.prueba.service;

import java.util.List;
import java.util.Map;

public interface IRecommendationService {

	public List<String> calculate(Map<String, Float> items, Float amount);

}
