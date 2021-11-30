package com.prueba.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.prueba.dto.CouponRequestDto;
import com.prueba.dto.CouponResponseDto;
import com.prueba.service.ICouponService;
import com.prueba.service.IItemService;
import com.prueba.service.IRecommendationService;
import com.prueba.utils.JacksonUtils;

@Service
public class CouponServiceImpl implements ICouponService {

	private Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

	private IItemService itemServiceImpl;

	private IRecommendationService recommendationServiceImpl;

	public CouponServiceImpl(IItemService itemServiceImpl, IRecommendationService recommendationServiceImpl) {
		this.itemServiceImpl = itemServiceImpl;
		this.recommendationServiceImpl = recommendationServiceImpl;
	}

	@Override
	public CouponResponseDto evaluateCoupon(CouponRequestDto couponRequestDto) {
		log.info("Inicia evaluar cupon couponRequestDto: {}", JacksonUtils.getPlainJson(couponRequestDto));
		Map<String, Float> itemsMap = itemServiceImpl.mapItems(couponRequestDto.getItemIds(),
				couponRequestDto.getAmount());

		List<String> itemResultList = recommendationServiceImpl.calculate(itemsMap, couponRequestDto.getAmount());

		CouponResponseDto couponResponseDto = mapResponse(itemResultList, itemsMap);
		log.info("Finaliza evaluar cupon couponResponseDto: {}", JacksonUtils.getPlainJson(couponResponseDto));
		return couponResponseDto;
	}

	private CouponResponseDto mapResponse(List<String> itemResultList, Map<String, Float> itemsMap) {
		CouponResponseDto couponResponseDto = new CouponResponseDto();
		couponResponseDto.setItemIds(itemResultList);
		couponResponseDto.setTotal(calculateAmountSpent(itemResultList, itemsMap));
		return couponResponseDto;
	}

	private Float calculateAmountSpent(List<String> offer, Map<String, Float> items) {
		float amount = 0f;
		for (String item : offer) {
			amount += items.get(item);
		}
		return amount;
	}

}
