package com.prueba.utils;

public class ConvertUtils {

	private ConvertUtils() {

	}

	public static Float convertPesosToDollar(Float valuePesos, Float trm) {
		return valuePesos / trm;
	}
}
