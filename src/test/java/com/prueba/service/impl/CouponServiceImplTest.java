package com.prueba.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prueba.dto.CouponRequestDto;
import com.prueba.dto.CouponResponseDto;
import com.prueba.service.IItemService;
import com.prueba.service.IRecommendationService;

class CouponServiceImplTest {

	@Mock
	private IItemService itemServiceImpl;

	@Mock
	private IRecommendationService recommendationServiceImpl;

	@InjectMocks
	private CouponServiceImpl couponServiceImpl;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		Map<String, Float> items = new HashMap();
		items.put("MLCO1", 2000f);
		items.put("MLCO2", 4000f);
		items.put("MLCO3", 8000f);
		items.put("MLCO4", 1000f);

		when(itemServiceImpl.mapItems(any(), any())).thenReturn(items);

		List<String> listResult = new ArrayList();
		listResult.add("MLCO1");
		listResult.add("MLCO2");
		listResult.add("MLCO4");
		when(recommendationServiceImpl.calculate(any(), any())).thenReturn(listResult);
	}

	@Test
	void evaluateCouponTest() {

		CouponRequestDto couponRequestDto = new CouponRequestDto();
		couponRequestDto.setAmount(20000f);
		List<String> itemIds = new ArrayList<String>();
		itemIds.add("MLCO1");
		itemIds.add("MLCO2");
		itemIds.add("MLCO3");
		itemIds.add("MLCO4");
		couponRequestDto.setItemIds(itemIds);

		CouponResponseDto couponResponseDto = couponServiceImpl.evaluateCoupon(couponRequestDto);
		Assert.assertNotNull(couponResponseDto);
		Assert.assertEquals(Float.valueOf(7000f), couponResponseDto.getTotal());
		Assert.assertEquals(couponResponseDto.getItemIds().size(), 3);

	}

}
