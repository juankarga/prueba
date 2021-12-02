package com.prueba.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prueba.service.IRecommendationService;
import com.prueba.utils.ConvertUtils;

@Service
public class RecommendationServiceImpl implements IRecommendationService {

	// tasa representativa del mercado con la que vamos a convertir la moneda
	@Value("${trm}")
	private Float trm;

	/**
	 * Metodo para calcular la lista de recomendados a partir de una lista de
	 * favoritos y un monto maximo del cupon
	 * 
	 * Para resolver esto implementaremos una solucion adaptada del problema de la
	 * mochila dado que tenemos una capacidad maxima de la mochila (monto del cupon)
	 * y una serie de elementos a intruducir en esta ( items) con esta
	 * implementacion encontraremos la mejor forma de realizar la recomendacion de
	 * items para gastar la mayor parte del cupon
	 * 
	 * https://www.baeldung.com/java-knapsack
	 * 
	 */
	@Override
	public List<String> calculate(Map<String, Float> items, Float amountIn) {

		// convertimos de pesos a dolares buscando manejar cifras mas pequeñas y con
		// ellos reducir la complejidad del algoritmo
		Float amount = ConvertUtils.convertPesosToDollar(amountIn, trm);

		int wt[] = new int[items.size()];
		int val[] = new int[items.size()];
		Map<Integer, String> equivalent = new HashMap<Integer, String>();
		int i = 0;
		for (String name : items.keySet()) {
			equivalent.put(i, name);
			// convertimos de pesos a dolares buscando manejar cifras mas pequeñas y con
			// ellos reducir la complejidad del algoritmo
			wt[i] = (int) Math.floor(ConvertUtils.convertPesosToDollar(items.get(name), trm) * 100);
			val[i] = 1;
			i++;
		}

		int W = (int) Math.floor(amount * 100);
		int n = val.length;

		return solveknapSack(W, wt, val, n, equivalent);
	}

	/**
	 * Metodo que implementa la solucion al problema de la mochila
	 * 
	 * @param W          monto del cupon
	 * @param wt         lista de precios de cada uno de los items
	 * @param val        beneficio o valor que le damos a cada item en nuestro caso
	 *                   es indiferente por eso se marca siempre en 1
	 * @param n          numero de items
	 * @param equivalent mapa que nos servira para identificar en que posicion quedo
	 *                   cada item y al final construir nuestra lista de
	 *                   recomendacion
	 * @return
	 */
	private List<String> solveknapSack(int W, int[] wt, int[] val, int n, Map<Integer, String> equivalent) {
		// declaramos la lista que posteriormente llenaremos para tener los recomendados
		List<String> result = new ArrayList<String>();

		// iniciamos llenando la matriz para resolver el problema de la mochila
		int i, w;
		int K[][] = new int[n + 1][W + 1];
		for (i = 0; i <= n; i++) {
			for (w = 0; w <= W; w++) {
				if (i == 0 || w == 0)
					K[i][w] = 0;
				else if (wt[i - 1] <= w)
					K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}
		// resultado de la mochila
		int res = K[n][W];

		// ahora vamos a analizar cuales son los items que vamos a recomendar a partir
		// del precio maximo
		w = W;
		for (i = n; i > 0 && res > 0; i--) {
			if (res == K[i - 1][w])
				continue;
			else {
				result.add(equivalent.get(i - 1));
				res = res - val[i - 1];
				w = w - wt[i - 1];
			}
		}
		return result;
	}
}
