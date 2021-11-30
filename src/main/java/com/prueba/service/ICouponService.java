package com.prueba.service;

import com.prueba.dto.CouponRequestDto;
import com.prueba.dto.CouponResponseDto;

public interface ICouponService {

	public CouponResponseDto evaluateCoupon(CouponRequestDto couponRequestDto);

}
