package com.prueba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.dto.CouponRequestDto;
import com.prueba.dto.CouponResponseDto;
import com.prueba.service.ICouponService;
import com.prueba.utils.JacksonUtils;

@RestController
public class CouponController {

	private Logger log = LoggerFactory.getLogger(CouponController.class);

	private ICouponService couponServiceImpl;

	public CouponController(ICouponService couponServiceImpl) {
		this.couponServiceImpl = couponServiceImpl;
	}

	@PostMapping("/coupon")
	public ResponseEntity<CouponResponseDto> evaluateCoupon(@RequestBody CouponRequestDto couponRequestDto) {
		log.info("Inicia evaluar cupon request: {}", JacksonUtils.getPlainJson(couponRequestDto));
		CouponResponseDto couponResponseDto = couponServiceImpl.evaluateCoupon(couponRequestDto);
		log.info("Finaliza evaluar cupon response: {}", JacksonUtils.getPlainJson(couponResponseDto));
		if (couponResponseDto.getItemIds().isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(couponResponseDto);
		}
	}

}
