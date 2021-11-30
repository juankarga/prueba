package com.prueba.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
@SpringBootTest
public class RecommendationServiceImplTest {

	@InjectMocks
	private RecommendationServiceImpl recommendationServiceImpl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(recommendationServiceImpl, "trm", 4000f);
	}

	@Test
	public void calculateTest() {

		Float amountIn = 7000f;

		Map<String, Float> items = new HashMap();
		items.put("MLCO1", 2000f);
		items.put("MLCO2", 4000f);
		items.put("MLCO3", 8000f);
		items.put("MLCO4", 1000f);

		List<String> resultList = recommendationServiceImpl.calculate(items, amountIn);
		Assert.assertNotNull(resultList);
		Assert.assertTrue(resultList.size() == 3);
	}

}
