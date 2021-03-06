package com.prueba.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.prueba.dto.CouponRequestDto;
import com.prueba.dto.CouponResponseDto;
import com.prueba.service.ICouponService;

class CouponControllerTest {

	@Mock
	private ICouponService couponServiceImpl;

	@InjectMocks
	private CouponController couponController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		CouponResponseDto couponResponseDto = new CouponResponseDto();
		List<String> itemIds = new ArrayList<String>();
		itemIds.add("MLCO1");
		itemIds.add("MLCO2");
		couponResponseDto.setItemIds(itemIds);
		couponResponseDto.setTotal(19800f);
		when(couponServiceImpl.evaluateCoupon(any())).thenReturn(couponResponseDto);
	}

	@Test
	void evaluateCouponTest() throws Exception {

		CouponRequestDto couponRequestDto = new CouponRequestDto();
		couponRequestDto.setAmount(20000f);
		List<String> itemIds = new ArrayList<String>();
		itemIds.add("MLCO1");
		itemIds.add("MLCO2");
		itemIds.add("MLCO3");
		itemIds.add("MLCO4");
		couponRequestDto.setItemIds(itemIds);

		ResponseEntity<CouponResponseDto> response = couponController.evaluateCoupon(couponRequestDto);
		Assert.assertNotNull(response);
		CouponResponseDto couponResponseDto = response.getBody();
		Assert.assertEquals(Float.valueOf(19800f), couponResponseDto.getTotal());
		Assert.assertEquals(2, couponResponseDto.getItemIds().size());
	}

}
